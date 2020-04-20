package execution.database;

import java.util.List;

public class DatabaseStatements {

    public static void createDatabase(List tokens){
        try {
            CreateDatabaseStatement cds=new CreateDatabaseStatement(tokens);
            cds.createDatabaseImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void renameDatabase(List tokens){
        try {
            RenameDatabaseStatement renameDatabaseStatement=new RenameDatabaseStatement(tokens);
            renameDatabaseStatement.renameDatabaseImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showDatabase(List tokens){
        ShowDatabaseStatement sds=new ShowDatabaseStatement();
        sds.showDatabaseStatementImpl();
    }

    public static void dropDatabase(List tokens){
        try {
            DropDatabaseStatement dds=new DropDatabaseStatement(tokens);
            dds.dropDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void useDatabase(List tokens){
        try {
            UseDatabaseStatement uds=new UseDatabaseStatement(tokens);
            uds.useDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
