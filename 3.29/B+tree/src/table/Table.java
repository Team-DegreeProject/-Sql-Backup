package table;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.type.SqlConstantImpl;

import java.util.HashMap;
import java.util.List;

public class Table extends SqlConstantImpl {
    private TableDescriptor td;
    private  BPlusTree tree;
    private HashMap propertyMap;
    public Table(){}
    public Table(TableDescriptor td,BPlusTree b) throws ClassNotFoundException {
        this.td=td;
        tree = b;
    }
    public Table(TableDescriptor td) throws ClassNotFoundException {
        this.td=td;
        tree = new BPlusTree<>(4);;
        createTable(td);

    }

    public void setTableDescriptor(TableDescriptor td) { this.td = td; }

    public void setTree(BPlusTree tree) { this.tree = tree; }

    public TableDescriptor getTableDescriptor(){return td;}

    public BPlusTree getTree() { return this.tree; }

    public HashMap createTable(TableDescriptor table) throws ClassNotFoundException {
        propertyMap = new HashMap();
        ColumnDescriptorList list=table.getColumnDescriptorList();
        for(int i=0;i<list.size();i++){
            ColumnDescriptor cd=list.getColumnDescriptor(i);
            DataTypeDescriptor dtd=cd.getType();
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.typeId));
            propertyMap.put(cd.getColumnName(), Class.forName(sqlMap.get(dtd.typeId)));
        }
        return propertyMap;
    }

    public boolean insertRows(String[] attributes,List values){
        if(attributes.length!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }
        System.out.println("att "+attributes.length);
        CglibBean bean = new CglibBean(propertyMap);
        for(int i=0;i<attributes.length;i++){
            bean.setValue(attributes[i], values.get(i));
//            System.out.println(attributes[i]+"--->>"+values.get(i));
        }
        tree.insert(bean, (Comparable) bean.getValue(td.getPrimaryKey()[0]));//双primarykey
        return true;
    }

    public void printTable(){
        System.out.println("||"+td.getName()+"||");
        System.out.println("-------------------------------------------------------");
        td.printColumnName();
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
        BPlusTree newTree=new BPlusTree();
        List list1=t.getTree().getDatas();
        List list2=tree.getDatas();
        for(int i=0;i<list1.size();i++){
            CglibBean c= (CglibBean) list1.get(i);
            for(int j=0;j<attributes.size();j++){
                c.setValue((String) attributes.get(j),values.get(j));
            }
        }
        return true;
    }

    public void deleteRows(Table t){
        List<CglibBean> list=t.getTree().getDatas();
        String primaryKey=t.getTableDescriptor().getPrimaryKey()[0];//%
        for(int i=0;i<t.size();i++){
            CglibBean c=list.get(i);
            Comparable pk= (Comparable) c.getValue(primaryKey);
            tree.delete(pk);
        }
    }

    public int size(){
        return tree.getDataNumber();
    }

}
