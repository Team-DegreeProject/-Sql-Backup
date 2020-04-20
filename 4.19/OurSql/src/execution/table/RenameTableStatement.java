package execution.table;

import execution.ExecuteStatement;
import execution.WhereStatament;
import parsing.Token;
import table.BTree.CglibBean;
import table.Table;
import java.util.ArrayList;
import java.util.List;
import static parsing.SqlParserConstants.EQ;


//5 RENAME TABEL
//5.1 RENAME tbname TO tbname1；
public class RenameTableStatement {

    List statement;

    public RenameTableStatement(List tokens){
        statement=tokens;
    }

    public boolean renameTableImpl() throws ClassNotFoundException {
        Table database=ExecuteStatement.db.getDatabase();
        String oldName=((Token)statement.get(1)).image;
        String newName=((Token)statement.get(3)).image;
        String[] att={"tablename"};
        List values=new ArrayList();
//        att.add("table");
//        att.add("tablename");
        Table change= WhereStatament.compare(database,"tablename",EQ,oldName);
        values.add(newName);
        boolean bool=database.updateTable(att,values,change);
        if(bool==false){
            database.printTable(null);
            return false;
        }

        List list= (List) change.getTree().getDatas();
        CglibBean c= (CglibBean) list.get(0);
        Table table= new Table((Table) c.getValue("table"));
        String[] namestt={"table"};
        values=new ArrayList();
        values.add(table);
        table.getTableDescriptor().setTableName(newName);
        bool=database.updateTable(namestt,values,change);

        database.printTable(null);
        return bool;
    }

}
