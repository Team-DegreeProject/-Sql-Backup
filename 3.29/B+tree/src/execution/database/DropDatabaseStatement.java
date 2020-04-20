package execution.database;

import execution.ExecuteStatement;
import execution.table.WhereStatament;
import parsing.Token;
import table.Table;

import java.util.List;

import static parsing.SqlParserConstants.EQ;

public class DropDatabaseStatement {
    List statement=null;
    public DropDatabaseStatement(){}
    public DropDatabaseStatement(List tokens){
        statement=tokens;
    }
    public boolean dropDatabaseStatementImpl() throws ClassNotFoundException {
        if(statement==null){
            return false;
        }
        String databaseName=((Token)statement.get(2)).image;
        Table delete=WhereStatament.compare(ExecuteStatement.uad.getUserAccessedDatabase(),"databasename",EQ,databaseName,"id");
        ExecuteStatement.uad.getUserAccessedDatabase().deleteRows(delete);
        return true;
    }
}
