package execution.database;

import execution.ExecuteStatement;
import execution.WhereStatament;
import parsing.Token;
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
        Table t=WhereStatament.compare(table,"databasename",EQ,databaseName);
        ExecuteStatement.uad.printUserAccessedDatabase();
        CglibBean c= (CglibBean) t.getTree().getDatas().get(0);
        Database database= (Database) c.getValue("database");
        database.getDatabase().printTable();
        ExecuteStatement.db=database;
        return true;
    }
}
