package execution.table;

import execution.ExecuteStatement;
import execution.WhereStatament;
import parsing.Token;
import table.BTree.CglibBean;
import table.ColumnDescriptorList;
import table.Table;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;

import java.util.List;

import static execution.table.DMLTool.analyseOneRow;
import static parsing.SqlParserConstants.*;

public class AlterTableStatement {

    List statement;

    public AlterTableStatement(List tokens){
        statement=tokens;
    }

    //2.1 ALTER TABLE ADD 列
    //2.1.1 ALTER TABLE tbname ADD new_column data_type [AFTER existing_column]
    //2.1.2 ALTER TABLE tbname ADD new_column INT NOT NULL;
    //2.1.3 ALTER TABLE tbname ADD new_column NUMERIC(20,3) AFTER course_name,
    //ADD max_limit INT AFTER course_name;
    //2.1.4 在 ALTER TABLE 子句之后指定要添加 table_name，表示列所在的表
    //2.1.5 将新列定义放在 ADD 子句之后。 如果要在表中指定新列的顺序，可以使用可选子句 AFTER existing_column
    public boolean alterTableAddColumnStatement() throws ClassNotFoundException {
        String name=((Token)statement.get(2)).image;
        List<List> newColumns= (List) statement.get(3);
        Table database= ExecuteStatement.db.getDatabase();
        Table change= (Table) ((CglibBean)((List)WhereStatament.compare(database,"tablename",EQ,name).getTree().getDatas()).get(0)).getValue("table");
        ColumnDescriptorList primaryKeys=change.getTableDescriptor().getPrimaryKey();
        ColumnDescriptorList columns=new ColumnDescriptorList();
        int size=change.getTableDescriptor().getMaxColumnID()+1;
        for(int i=0;i<newColumns.size();i++){
            DataTypeDescriptor dataTypeDescriptor=analyseOneRow(0,newColumns.get(i));
            String columnName=((Token)newColumns.get(i).get(1)).image;
            ColumnDescriptor column=new ColumnDescriptor(columnName,size+i,dataTypeDescriptor);
            columns.add(column);
            if(dataTypeDescriptor.isPrimaryKey()){
                primaryKeys.add(column);
            }
        }
        change.addColumns(columns);
        change.getTableDescriptor().printColumnName();
        return true;
    }

    //2.2 ALTER TABLE MODIFY // MODIFY 子句用于更改现有列的某些属性
    //2.2.1 ALTER TABLE tbname MODIFY column_definition;
    //2.2.2 ALTER TABLE tbname MODIFY fee NUMERIC (10,2) NOT NULL;
    public boolean alterModifyImpl() throws ClassNotFoundException {
        String name=((Token)statement.get(2)).image;
        List<List> newColumns= (List) statement.get(4);
        Table database= ExecuteStatement.db.getDatabase();
        Table change= (Table) ((CglibBean)((List)WhereStatament.compare(database,"tablename",EQ,name).getTree().getDatas()).get(0)).getValue("table");
        change.modifyColumns(newColumns);
        change.getTableDescriptor().printColumnName();
        return true;
    }


    //2.3 ALTER TABLE DROP
    //2.3.1 删除一个或者多个列
    //2.3.1.1 ALTER TABLE tbname DROP COLUMN column_name, DROP COLUMN
    //column_name.
    //2.3.1.2 ALTER TABLE tbname DROP COLUMN fee;
    public boolean alterTableDropImpl() throws ClassNotFoundException {
        String name=((Token)statement.get(2)).image;
        List<List> newColumns= (List) statement.get(3);
        Table database= ExecuteStatement.db.getDatabase();
        Table change= (Table) ((CglibBean)((List)WhereStatament.compare(database,"tablename",EQ,name).getTree().getDatas()).get(0)).getValue("table");
        change.dropColumns(newColumns);
        change.getTableDescriptor().printColumnName();
        return true;
    }
}
