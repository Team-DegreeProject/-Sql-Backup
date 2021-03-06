package table;

import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.type.PrimaryKey;
import table.type.number.SqlInt;
import java.util.ArrayList;
import java.util.List;
import static parsing.SqlParserConstants.*;
import static table.TableSchema.BASE_TABLE_TYPE;

public class Database{
    private  int id=0;
    private  Table database;
    public Database(Table t){
        database=t;
    }

    public Database(Database t){
        this.id=t.id;
        this.database=t.database;
    }

    public Database(String databasename) throws ClassNotFoundException {
        createDatabase(databasename);
    }

    public boolean createDatabase(String databasename) throws ClassNotFoundException {
        TableDescriptor td=null;
        ColumnDescriptorList primaryKey=new ColumnDescriptorList();
        ColumnDescriptorList columns=new ColumnDescriptorList();
        DataTypeDescriptor dataType= new DataTypeDescriptor(INT,false);
        ColumnDescriptor columnId=new ColumnDescriptor("id",1,dataType);
        columnId.setUnique(true);
        dataType= new DataTypeDescriptor(TABLE,false);
        ColumnDescriptor columnTable=new ColumnDescriptor("table",2,dataType);
        dataType= new DataTypeDescriptor(STRING,false);
        ColumnDescriptor columnTableName=new ColumnDescriptor("tablename",3,dataType);
        columnTableName.setUnique(true);
        DataTypeDescriptor tp=new DataTypeDescriptor(PRIMARY_KEY,false);
        ColumnDescriptor columnp=new ColumnDescriptor("primary key",0,tp);
        columns.add(columnp);
        columns.add(columnId);
        columns.add(columnTable);
        columns.add(columnTableName);
        primaryKey.add(columnId);
        td=new TableDescriptor(databasename,BASE_TABLE_TYPE,columns,primaryKey);
        td.setTableInColumnDescriptor(td);
        td.printColumnName();
        database=new Table(td);
        return true;
    }

    public void setDatabase(Table database){
        this.database=database;
    }

    public Table getDatabase() {
        return database;
    }

    public String getDatabaseName() {
        return this.database.getTableDescriptor().getName();
    }

    public void setDatabaseName(String databaseName) {
        this.database.getTableDescriptor().setTableName(databaseName);
        System.out.println("=========="+database.getTableDescriptor().getName());
    }

    public boolean insertTable(Table t) throws Exception {
        List values=new ArrayList();
        PrimaryKey pk=new PrimaryKey();
        SqlInt sqlid=new SqlInt(id);
        pk.addPrimaryKey("id",sqlid);
        values.add(pk);
        values.add(sqlid);
        values.add(t);
        values.add(t.getTableDescriptor().getName());
        id++;
//        String[] attributes=database.getTableDescriptor().getColumnNamesArray();
        return database.insertARow(values);
    }

    public void printDatabase(){
        database.printTable();
    }
}
