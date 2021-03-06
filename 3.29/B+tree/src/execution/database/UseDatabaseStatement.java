package execution.database;

import execution.ExecuteStatement;
import execution.table.WhereStatament;
import parsing.Token;
import system.UserAccessedDatabases;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.Database;
import table.Table;

import java.util.List;

import static parsing.SqlParserConstants.EQ;

public class UseDatabaseStatement {
    List statement=null;
    public UseDatabaseStatement(){}
    public UseDatabaseStatement(List token){
        statement=token;
    }
    public  boolean useDatabaseStatementImpl() throws ClassNotFoundException {
        ExecuteStatement.uad.printUserAccessedDatabase();
        Table table=ExecuteStatement.uad.getUserAccessedDatabase();
        String databaseName=((Token)statement.get(1)).image;
        Table t=WhereStatament.compare(table,"databasename",EQ,databaseName,"id");
        ExecuteStatement.uad.printUserAccessedDatabase();
        CglibBean c= (CglibBean) t.getTree().getDatas().get(0);
        Database db= (Database) c.getValue("database");
        db.getDatabase().printTable();
        return true;
    }
}
