package execution.database;

import execution.ExecuteStatement;
import parsing.Token;
import table.Database;

import java.util.List;

public class CreateDatabaseStatement{
    List statement=null;
    public CreateDatabaseStatement(List l){
        statement=l;
    }
    public CreateDatabaseStatement(){}

    public String createDatabaseImpl() throws Exception {
        if(statement==null){
            return "Create Database Wrong!";
        }
        String databaseName=  ((Token)statement.get(2)).image;
        Database db=new Database(databaseName);
        ExecuteStatement.uad.insertDatabase(db);
        String output=ExecuteStatement.uad.getUserAccessedDatabase().printTable(null);
        return output;
    }

//    public Database createDatabaseImpl(int i) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
////        TableDescriptor td=null;
//        String databasename="test";
//        Database db=new Database(databasename);
//        ExecuteStatement.uad.insertDatabase(db);
//        return db;
//    }

}
