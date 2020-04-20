package table;

import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import java.util.ArrayList;
import java.util.List;

import static parsing.SqlParserConstants.*;
import static table.TableSchema.BASE_TABLE_TYPE;

public class Database {
    private  int id=0;
    private  Table database;
    public Database(Table t){
        database=t;
    }

    public Database(String databasename) throws ClassNotFoundException {
        createDatabase(databasename);
    }

    public boolean createDatabase(String databasename) throws ClassNotFoundException {
        TableDescriptor td=null;
        ColumnDescriptorList columns=new ColumnDescriptorList();
        DataTypeDescriptor dataType= new DataTypeDescriptor(INT);
        ColumnDescriptor columnId=new ColumnDescriptor("id",0,dataType);
        dataType= new DataTypeDescriptor(TABLE);
        ColumnDescriptor columnTable=new ColumnDescriptor("table",1,dataType);
        dataType= new DataTypeDescriptor(STRING);
        ColumnDescriptor columnTableName=new ColumnDescriptor("tablename",2,dataType);
        columns.add(columnId);
        columns.add(columnTable);
        columns.add(columnTableName);
        String[] primaryKey={"id"};
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
    }

    public boolean insertTable(Table t) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List values=new ArrayList();
        values.add(id);
        values.add(t);
        values.add(t.getTableDescriptor().getName());
        String[] primaryKey={"id"};
        id++;
        String[] attributes=database.getTableDescriptor().getColumnNamesArray();
        return database.insertRows(attributes,values);

    }
}
