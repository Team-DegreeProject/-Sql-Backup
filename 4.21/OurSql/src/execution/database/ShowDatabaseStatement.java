package execution.database;

import execution.ExecuteStatement;
import table.Table;

import java.util.List;

public class ShowDatabaseStatement {
    List statement=null;
    public ShowDatabaseStatement(){}
    public ShowDatabaseStatement(List list){
        this.statement=list;
    }

    public String showDatabaseStatementImpl(){
//        Table usa= ExecuteStatement.uad.getUserAccessedDatabase();
//        usa.printTable();
        String output=ExecuteStatement.uad.printUserAccessedDatabase();
        return output;
    }
}
