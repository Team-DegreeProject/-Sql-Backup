package system;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.ColumnDescriptorList;
import table.Database;
import table.Table;
import table.TableDescriptor;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.type.PrimaryKey;
import table.type.number.SqlInt;
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
        ColumnDescriptorList primaryKey=new ColumnDescriptorList();
        ColumnDescriptorList columns=new ColumnDescriptorList();
        DataTypeDescriptor id= new DataTypeDescriptor(INT,false);
        ColumnDescriptor column=new ColumnDescriptor("id",1,id);
        column.setUnique(true);
        primaryKey.add(column);
        columns.add(column);
        DataTypeDescriptor user= new DataTypeDescriptor(USER,false);
        column=new ColumnDescriptor("user",2,user);
        columns.add(column);
        DataTypeDescriptor t= new DataTypeDescriptor(DATABASE,false);
        column=new ColumnDescriptor("database",3,t);
        columns.add(column);
        DataTypeDescriptor tn= new DataTypeDescriptor(STRING,false);
        column=new ColumnDescriptor("databasename",4,tn);
        column.setUnique(true);
        columns.add(column);
        DataTypeDescriptor tp=new DataTypeDescriptor(PRIMARY_KEY,false);
        column=new ColumnDescriptor("primary key",0,tp);
        columns.add(column);
        tableDescriptor =new TableDescriptor(tableName,SYSTEM_TABLE_TYPE,columns,primaryKey);
        tableDescriptor .setTableInColumnDescriptor(tableDescriptor);
        tableDescriptor .printColumnName();
        userAccessedDatabase=new Table(tableDescriptor);
        return userAccessedDatabase;
    }

    public boolean insertDatabase(Database database) throws Exception {
        int id=length;
        length++;
        PrimaryKey pk=new PrimaryKey();
        SqlInt sqlid=new SqlInt(id);
        pk.addPrimaryKey("id",sqlid);
        List values=new ArrayList();
        values.add(pk);
        values.add(sqlid);
        values.add(user);
        values.add(database);
        values.add(database.getDatabaseName());
        return userAccessedDatabase.insertARow(values);
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
