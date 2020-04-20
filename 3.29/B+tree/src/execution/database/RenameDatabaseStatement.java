package execution.database;

import execution.ExecuteStatement;
import execution.table.WhereStatament;
import parsing.Token;
import system.User;
import table.BTree.CglibBean;
import table.Database;
import table.Table;
import table.TableDescriptor;

import java.util.ArrayList;
import java.util.List;

import static parsing.SqlParserConstants.EQ;

public class RenameDatabaseStatement {
    List statement=null;
    public RenameDatabaseStatement(List l){
        statement=l;
    }
    public boolean renameDatabaseImpl() throws ClassNotFoundException {
        String databaseName=((Token)statement.get(2)).image;
        String newDatabaseName=((Token)statement.get(4)).image;
        List att=new ArrayList();
        List values=new ArrayList();
        att.add("database");
        att.add("databasename");
        Table usa=ExecuteStatement.uad.getUserAccessedDatabase();
        Table change=WhereStatament.compare(usa,"databasename",EQ,databaseName,"id");
        List list= (List) change.getTree().getDatas();
        CglibBean c= (CglibBean) list.get(0);
        Database database= (Database) c.getValue("database");
        database.setDatabaseName(newDatabaseName);
        values.add(database);
        values.add(newDatabaseName);
        boolean bool=usa.updateTable(att,values,change);
        return bool;
    }

}
