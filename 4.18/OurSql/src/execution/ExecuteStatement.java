package execution;

import execution.data.DataStatements;
import execution.database.*;
import execution.table.*;
import parsing.Token;
import system.User;
import system.UserAccessedDatabases;
import table.Database;
import java.util.List;

import static parsing.SqlParserConstants.*;

public class ExecuteStatement {

    public static User user;//%%
    public static UserAccessedDatabases uad=setUser();//%%
    public static Database db=null;


    public static UserAccessedDatabases setUser(){
        user=new User(0,"root");
        uad=user.getUserAccessedDatabases();
        uad.setUser(user);
        return uad;
    }



    public static void rename(List tokens){
        int type=((Token)tokens.get(1)).kind;
        if(type==DATABASE){
            DatabaseStatements.renameDatabase(tokens);
        }else{
            TableStatements.renameTable(tokens);
        }
    }

    public static void create(List tokens){
        int name=((Token)tokens.get(1)).kind;
        if(name==DATABASE){
            DatabaseStatements.createDatabase(tokens);
        }else if(name==TABLE){
            try {
                if(db==null){
                    throw new Exception("There is no database;");
                }
                TableStatements.createTable(tokens);
                db.printDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void drop(List tokens){
        int type=((Token)tokens.get(1)).kind;
        if(type==DATABASE){
            DatabaseStatements.dropDatabase(tokens);
        }else{
            TableStatements.dropTable(tokens);
        }
    }

    public static void alter(List tokens){
        TableStatements.alterTable(tokens);
    }

    public static void insert(List tokens){
        DataStatements.insertData(tokens);
    }

    public static void delete(List tokens){DataStatements.deleteData(tokens);}

    public static void update(List tokens){DataStatements.updateData(tokens);}

    public static void truncate(List tokens){TableStatements.truncateTable(tokens);};

    public static void select(List tokens){DataStatements.selectData(tokens);}



}
