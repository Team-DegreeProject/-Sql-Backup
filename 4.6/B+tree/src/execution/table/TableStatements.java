package execution.table;

import java.util.List;

public class TableStatements {

    public static void createTable(List tokens){
        try {
            CreateTableStatement createTableStatement=new CreateTableStatement(tokens);
            createTableStatement.createImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable(List tokens){
        try {
            DropTableStatement dropTableStatement=new DropTableStatement(tokens);
            dropTableStatement.dropTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static void renameTable(List tokens){
        try {
            RenameTableStatement renameTableStatement = new RenameTableStatement(tokens);
            renameTableStatement.renameTableImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void alterTable(List tokens){
        try {
            AlterTableStatement alterTableStatement=new AlterTableStatement(tokens);
            alterTableStatement.alterTableColumnStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
