package table;

import execution.DMLTool;
import parsing.Token;
import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.type.PrimaryKey;
import table.type.SqlConstantImpl;
import table.type.SqlType;

import java.util.*;

import static execution.DMLTool.analyseOneRow;

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
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.getTypeId()));
            propertyMap.put(cd.getColumnName(), Class.forName(sqlMap.get(dtd.getTypeId())));
        }
        return propertyMap;
    }



//已知所有值以固定顺序排列好,通常用为插入系统表格
    public boolean insertARow(List values){
        String[] attributes=td.getColumnNamesArray();
        if(attributes.length!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }
        CglibBean bean = new CglibBean(propertyMap);
        for(int i=0;i<attributes.length;i++){
            bean.setValue(attributes[i], values.get(i));
        }
        tree.insert(bean, (Comparable) bean.getValue("primary key"));//双primarykey
        return true;
    }



    public void insertRows(List<Token> attributes,List list,int start) throws Exception {
       for(int i=start;i<list.size();i++){
           List<List<Token>> l= (List<List<Token>>) list.get(i);
           insertARow(attributes,l);
       }
    }



    public boolean insertARow(List<Token> attributes,List<List<Token>> values) throws Exception {

        if(attributes.size()!=values.size()){
            System.out.println("The number of attributes is not equal to the number of values.");
            return false;
        }

        boolean checkPk=td.getPrimaryKey().checkHavePrimaryKey(attributes);
        if(checkPk==false){
            System.out.println("There is no enough primaryKey values.");
            return false;
        }

        boolean checkNull=td.getColumnDescriptorList().checkNotNull(attributes,values);
        System.out.println(td.getColumnDescriptorList().size());
        if(checkNull==false){
            System.out.println("Some attribute is not null.");
            return false;
        }

        ColumnDescriptorList auto=td.getColumnDescriptorList().getAutoIncrementList();
        List maxValues=getMaxValues(auto);

        int number=values.get(0).size();
        for(int j=0;j<number;j++){
            PrimaryKey pk=new PrimaryKey();
            CglibBean bean = new CglibBean(propertyMap);
            //处理自增
            for(int i=0;i<auto.size();i++){
                String name=auto.elementAt(i).getColumnName();
                SqlType value= ((SqlType) maxValues.get(i)).addOne();
                bean.setValue(name,value);
            }
            //处理输入
            for(int i=0;i<attributes.size();i++){
                String name=attributes.get(i).image;
                String v=values.get(i).get(j).image;
                SqlType value=DMLTool.convertToValue(name,v,propertyMap);
                ColumnDescriptor cd=td.getPrimaryKey().getColumnDescriptor(name);
                if(cd!=null){
                    pk.addPrimaryKey(value);
                    System.out.println("primary key: "+value);
                }
                bean.setValue(name, value);
                System.out.println(name+"--->>"+value);
            }

            bean.setValue("primary key",pk);
            tree.insert(bean, (Comparable) bean.getValue("primary key"));//双primarykey
        }
        return true;
    }



    public void printTable(){
        System.out.println("||"+td.getName()+"||");
        System.out.println("-------------------------------------------------------");
        BPlusTreeTool.printBPlusTree(tree,td);
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



    public boolean updateTable(List changes,Table t) throws InstantiationException, IllegalAccessException {
        if(t.getTree().getDataNumber()==0 ){
            System.out.println("No update");
            return false;
        }
        List list1=t.getTree().getDatas();
        for(int i=0;i<list1.size();i++){
            CglibBean c= (CglibBean) list1.get(i);
            for(int j=0;j<changes.size();j++){
                List now= (List) changes.get(j);
                String attribute=((Token)now.get(0)).image;
                String v=((Token)now.get(2)).image;
                SqlType value=DMLTool.convertToValue(attribute,v,propertyMap);
                c.setValue((String) attribute,value);
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
        this.printTable();
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
            System.out.println(cd.getColumnName()+"--->"+sqlMap.get(dtd.getTypeId()));
            propertyMap.put(cd.getColumnName(), Class.forName(sqlMap.get(dtd.getTypeId())));
        }
        for(int i=0;i<data.size();i++){
            CglibBean c=data.get(i);
            for(int j=0;j<columns.size();j++){
                c.setValue(columns.get(i).getColumnName(),null);
            }
            newTree.insert(c,(Comparable)c.getValue("primary key"));
        }
        this.tree=newTree;
        td.updatePriamryKey();
        updatePrimaryKey();
    }


    public void modifyColumns(List<List> tokens) throws ClassNotFoundException {
        ColumnDescriptorList list=td.getColumnDescriptorList();
        ColumnDescriptorList primaryKeys=td.getPrimaryKey();
        for(int i=0;i<tokens.size();i++){
            String columnName=((Token)tokens.get(i).get(0)).image;
            ColumnDescriptor oldcd=list.getColumnDescriptor(columnName);
            int position=oldcd.getPosition();
            ColumnDescriptor column=analyseOneRow(1,tokens.get(i),position);
            DataTypeDescriptor dataTypeDescriptor=column.getType();
//            ColumnDescriptor column=new ColumnDescriptor(columnName,position,dataTypeDescriptor);
            list.dropColumn(columnName);
            list.add(column);
            propertyMap.replace(columnName,Class.forName(sqlMap.get(dataTypeDescriptor.getTypeId())));
            System.out.println(list.getColumnDescriptor(position).getType().toString());
        }
        td.updatePriamryKey();
        updatePrimaryKey();
    }



    public void dropColumns(List<List> tokens){
        ColumnDescriptorList list=td.getColumnDescriptorList();
        ColumnDescriptorList primaryKeys=td.getPrimaryKey();
        for(int i=0;i<tokens.size();i++){
            String columnName=((Token)tokens.get(i).get(2)).image;
            list.dropColumn(columnName);
            propertyMap.remove(columnName);
        }
        BPlusTree newTree=new BPlusTree();
        List<CglibBean> data=tree.getDatas();
        String[] columns=td.getColumnNamesArray();
        for(int i=0;i<data.size();i++){
            CglibBean c=data.get(i);
            CglibBean nc=new CglibBean();
            for(int j=0;j<columns.length;j++){
                Object o=c.getValue(columns[j]);
                nc.setValue(columns[j],o);
            }
            newTree.insert(nc,(Comparable)nc.getValue("primary key"));
        }
        this.tree=newTree;
        td.updatePriamryKey();
        updatePrimaryKey();
    }



    public HashMap getPropertyMap(){
        return propertyMap;
    }



    public List getMaxValues(ColumnDescriptorList auto) throws IllegalAccessException, InstantiationException {
        List maxValues=new ArrayList();
        for(int i=0;i<auto.size();i++){
            boolean first=true;
            String name=auto.get(i).getColumnName();
            Class cl= (Class) propertyMap.get(name);
            SqlType max=(SqlType)cl.newInstance();
            List<CglibBean> list=tree.getDatas();
            for(int j=0;j<list.size();j++){
                CglibBean c=list.get(j);
                SqlType temp= (SqlType) c.getValue(name);
                if(first){
                    max=temp;
                }else{
                    int out=max.compareTo(temp);
                    if(out<0){
                        max=temp;
                    }
                }
            }
            maxValues.add(max);
        }
        return maxValues;
    }


}
