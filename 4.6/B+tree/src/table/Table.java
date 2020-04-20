package table;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.type.PrimaryKey;
import table.type.SqlConstantImpl;

import java.util.HashMap;
import java.util.List;

public class Table extends SqlConstantImpl {
    private TableDescriptor td;
    private BPlusTree tree;
    private HashMap propertyMap = new HashMap();
    public Table(){}
    public Table(TableDescriptor td,BPlusTree b) throws ClassNotFoundException {
        this.td=td;
        tree = b;
    }
    public Table(TableDescriptor td) throws ClassNotFoundException {
        this.td=td;
        tree = new BPlusTree<>(4);
        createTable(td);

    }

    public void setTableDescriptor(TableDescriptor td) { this.td = td; }

    public void setTree(BPlusTree tree) { this.tree = tree; }

    public TableDescriptor getTableDescriptor(){return td;}

    public BPlusTree getTree() { return this.tree; }

    public HashMap createTable(TableDescriptor table) throws ClassNotFoundException {
        ColumnDescriptorList list=table.getColumnDescriptorList();
        for(int i=0;i<list.size();i++){
            ColumnDescriptor cd=list.getColumnDescriptor(i);
            DataTypeDescriptor dtd=cd.getType();
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.typeId));
            propertyMap.put(cd.getColumnName(), Class.forName(sqlMap.get(dtd.typeId)));
        }
        return propertyMap;
    }

    public boolean insertRows(List values){
        String[] attributes=td.getColumnNamesArray();
        if(attributes.length!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }
        CglibBean bean = new CglibBean(propertyMap);
        for(int i=0;i<attributes.length;i++){
            bean.setValue(attributes[i], values.get(i));
//            System.out.println(attributes[i]+"--->>"+values.get(i));
        }
        tree.insert(bean, (Comparable) bean.getValue("primary key"));//双primarykey
        return true;
    }

    public void printTable(){
        System.out.println("||"+td.getName()+"||");
        System.out.println("-------------------------------------------------------");
        BPlusTreeTool.printBPlusTree(tree);
    }

    public boolean updateTable(List attributes,List values,Table t){
        if(t.getTree().getDataNumber()==0 ){
            System.out.println("No update");
            return false;
        }
        if(attributes.size()!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }
        List list1=t.getTree().getDatas();
        for(int i=0;i<list1.size();i++){
            CglibBean c= (CglibBean) list1.get(i);
            for(int j=0;j<attributes.size();j++){
                c.setValue((String) attributes.get(j),values.get(j));
            }
        }
        updatePrimaryKey();
        return true;
    }

    public boolean updatePrimaryKey(){
        ColumnDescriptorList pkn=td.getPrimaryKey();
        List list1=tree.getDatas();
        for(int i=0;i<list1.size();i++){
            CglibBean c= (CglibBean) list1.get(i);
            PrimaryKey pk=new PrimaryKey();
            for(int k=0;k<pkn.size();k++){
                Comparable com= (Comparable) c.getValue( pkn.elementAt(k).getColumnName());
                pk.addPrimaryKey(com);
            }
            c.setValue("primary key",pk);
        }
        return true;
    }

    public void deleteRows(Table t){
        List<CglibBean> list=t.getTree().getDatas();
        for(int i=0;i<t.size();i++){
            CglibBean c=list.get(i);
            Comparable pk= (Comparable) c.getValue("primary key");
            tree.delete(pk);
        }
    }

    public int size(){
        return tree.getDataNumber();
    }

    public boolean cleanAllData(){
        tree = new BPlusTree<>(4);
        return true;
    }

    public void addColumns(ColumnDescriptorList columns) throws ClassNotFoundException {
        BPlusTree newTree=new BPlusTree();
        ColumnDescriptorList list=td.getColumnDescriptorList();
        list.addColumns(columns);
        List<CglibBean> data=tree.getDatas();
        for(int i=0;i<columns.size();i++){
            ColumnDescriptor cd=columns.get(i);
            DataTypeDescriptor dtd=cd.getType();
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.typeId));
            propertyMap.put(cd.getColumnName(), Class.forName(sqlMap.get(dtd.typeId)));
        }
        for(int i=0;i<data.size();i++){
            CglibBean c=data.get(i);
            for(int j=0;j<columns.size();j++){
                c.setValue(columns.get(i).getColumnName(),null);
            }
            newTree.insert(c,(Comparable)c.getValue("primary key"));
        }
        this.tree=newTree;
        updatePrimaryKey();
    }


}
