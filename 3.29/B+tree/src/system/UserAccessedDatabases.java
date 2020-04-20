package system;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.ColumnDescriptorList;
import table.Database;
import table.Table;
import table.TableDescriptor;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;

import java.util.ArrayList;
import java.util.List;

import static table.TableSchema.SYSTEM_TABLE_TYPE;
import static table.type.SqlConstantImpl.*;

public class UserAccessedDatabases {
    private User user;
    private Table userAccessedDatabase;
//    private TableDescriptor tableDescriptor;
    int length=0;
    public UserAccessedDatabases(User user) throws ClassNotFoundException {
        Table userAccessedDatabase=databaseList();
        length=userAccessedDatabase.size();
        this.user=user;
    }

    public UserAccessedDatabases() throws ClassNotFoundException {
        Table userAccessedDatabase=databaseList();
        length=userAccessedDatabase.size();
    }

    public Table databaseList() throws ClassNotFoundException {
        TableDescriptor tableDescriptor =null;
        String tableName="UserPermissionDatabaseScope";
        ColumnDescriptorList columns=new ColumnDescriptorList();
        DataTypeDescriptor id= new DataTypeDescriptor(INT,false);
        ColumnDescriptor column=new ColumnDescriptor("id",0,id);
        columns.add(column);
        DataTypeDescriptor user= new DataTypeDescriptor(USER,false);
        column=new ColumnDescriptor("user",1,user);
        columns.add(column);
        DataTypeDescriptor t= new DataTypeDescriptor(DATABASE,false);
        column=new ColumnDescriptor("database",2,t);
        columns.add(column);
        DataTypeDescriptor tn= new DataTypeDescriptor(STRING,false);
        column=new ColumnDescriptor("databasename",3,tn);
        columns.add(column);
        String[] primaryKey={"id"};
        tableDescriptor =new TableDescriptor(tableName,SYSTEM_TABLE_TYPE,columns,primaryKey);
        tableDescriptor .setTableInColumnDescriptor(tableDescriptor);
        tableDescriptor .printColumnName();
        userAccessedDatabase=new Table(tableDescriptor);
        return userAccessedDatabase;
    }

    public boolean insertDatabase(Database database) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        int id=length;
        length++;
        List values=new ArrayList();
        values.add(id);
        values.add(user);
        values.add(database);
        values.add(database.getDatabaseName());
        String primaryKey="id";
        String[] attributes=userAccessedDatabase.getTableDescriptor().getColumnNamesArray();
        return userAccessedDatabase.insertRows(attributes,values);
    }

    public void returnUserAccessedDatabaseNames(){
        List names=new ArrayList();
        BPlusTree tree=userAccessedDatabase.getTree();
        BPlusTreeTool.printBPlusTree(tree,"database");
    }

    public void printUserAccessedDatabase(){
//        BPlusTree b=userAccessedDatabase.getTree();
//        BPlusTreeTool.printBPlusTree(b);
        userAccessedDatabase.printTable();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setUserAccessedDatabase(Table userAccessedDatabase) {
        this.userAccessedDatabase = userAccessedDatabase;
    }

    public Table getUserAccessedDatabase() {
        return userAccessedDatabase;
    }
}
