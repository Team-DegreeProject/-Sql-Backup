package execution.table;

import execution.ExecuteStatement;
import parsing.Token;
import table.ColumnDescriptorList;
import table.Table;
import table.TableDescriptor;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.type.PrimaryKey;
import table.type.SqlConstant;

import java.util.ArrayList;
import java.util.List;

import static table.TableSchema.BASE_TABLE_TYPE;

public class CreateTableStatement implements SqlConstant {
    List statement;
    ColumnDescriptorList columns;
    ColumnDescriptorList primaryKeys;
    public CreateTableStatement(List l){
        statement=l;
        columns=new ColumnDescriptorList();
        primaryKeys=new ColumnDescriptorList();
    }

    public Table createImpl() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List pk=new ArrayList();
        TableDescriptor td=null;
        if(statement==null){
            return null;
        }
        String tableName=((Token)statement.get(2)).image;
        List<List> attributes= (List) statement.get(3);
        for(int i=0;i<attributes.size();i++){
            DataTypeDescriptor dataTypeDescriptor=analyseOneRow(attributes.get(i));
            String columnName=((Token)attributes.get(i).get(0)).image;
//            List att= (List) attributes.get(i);
//            String columnName=((Token)att.get(0)).image;
//            int type=(Integer) att.get(1);
//            DataTypeDescriptor dataType= new DataTypeDescriptor(type);
            ColumnDescriptor column=new ColumnDescriptor(columnName,i,dataTypeDescriptor);
            columns.add(column);
            if(dataTypeDescriptor.isPrimaryKey()){
                primaryKeys.add(column);
            }
        }
//        String[] pk=null;
        td=new TableDescriptor(tableName,BASE_TABLE_TYPE,columns,primaryKeys);
        td.setTableInColumnDescriptor(td);
//        ColumnDescriptor cd=td.getColumnDescriptorList().getColumnDescriptor(1);
        Table table=new Table(td);
        ExecuteStatement.db.insertTable(table);
        td.printColumnName();
        table.printTable();
//        return table;
        return null;
    }

    public DataTypeDescriptor analyseOneRow(List tokens){
        DataTypeDescriptor dataType= new DataTypeDescriptor( ((Token)tokens.get(1)).kind  );
        for(int i=2;i<tokens.size();i++){
            Token t= (Token) tokens.get(i);
            setType(dataType,t);
        }
        return dataType;
    }

    public void setType(DataTypeDescriptor d,Token t){
        if(t.kind==PRIMARY_KEY){
            d.setPrimaryKey(true);
        }else if(t.kind==NOT_NULL){
            d.setNullable(false);
        }else if(t.kind==NUMBER){
            if(d.getScale()==-1){
                d.setScale(Integer.parseInt(t.image));
            }else{
                d.setPrecision(Integer.parseInt(t.image));
            }
        }

    }

//    public Object checkType(){
//
//    }

}
