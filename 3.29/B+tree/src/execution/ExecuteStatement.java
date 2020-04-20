package execution;

import execution.database.*;
import system.User;
import system.UserAccessedDatabases;
import table.Database;
import java.util.List;

public class ExecuteStatement {
    public static User user;//%%
    public static UserAccessedDatabases uad;//%%
    public static void delete(List tokens){
        System.out.println("tokens");
    }
    public static void createDatabase(List tokens){
        try {
            uad=setUser();//%%
            CreateDatabaseStatement cds=new CreateDatabaseStatement(tokens);
            cds.createDatabaseImpl();
            ShowDatabaseStatement sds=new ShowDatabaseStatement();
            sds.showDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void renameDatabase(List tokens){
        try {
            uad=setUser();
            CreateDatabaseStatement c=new CreateDatabaseStatement();//%%
            Database d=c.createDatabaseImpl(1);
            ShowDatabaseStatement sds=new ShowDatabaseStatement();
            sds.showDatabaseStatementImpl();
            RenameDatabaseStatement renameDatabaseStatement=new RenameDatabaseStatement(tokens);
            renameDatabaseStatement.renameDatabaseImpl();
            sds.showDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void showDatabase(List tokens){
        try {
            uad=setUser();
            CreateDatabaseStatement cds=new CreateDatabaseStatement(tokens);
            Database d=cds.createDatabaseImpl(1);
            ShowDatabaseStatement sds=new ShowDatabaseStatement();
            sds.showDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void dropDatabase(List tokens){
        try {
            uad=setUser();
            CreateDatabaseStatement cds=new CreateDatabaseStatement(tokens);
            Database d=cds.createDatabaseImpl(1);
            ShowDatabaseStatement sds=new ShowDatabaseStatement();
            sds.showDatabaseStatementImpl();
            DropDatabaseStatement dds=new DropDatabaseStatement(tokens);
            dds.dropDatabaseStatementImpl();
            sds.showDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void useDatabase(List tokens){
        try {
            uad=setUser();
            CreateDatabaseStatement cds=new CreateDatabaseStatement(tokens);
            Database d=cds.createDatabaseImpl(1);
            UseDatabaseStatement uds=new UseDatabaseStatement(tokens);
            uds.useDatabaseStatementImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static UserAccessedDatabases setUser(){
        user=new User(0,"root");
        uad=user.getUserAccessedDatabases();
        uad.setUser(user);
        return uad;
    }


}
