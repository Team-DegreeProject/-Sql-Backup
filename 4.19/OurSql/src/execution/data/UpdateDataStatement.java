package execution.data;

import execution.FromStatement;
import execution.WhereStatament;
import parsing.Token;
import table.Table;

import java.util.List;

public class UpdateDataStatement {

    public List statement;

    public UpdateDataStatement(List tokens){
        statement=tokens;
    }
    //UPDATE table SET column1 = value1, column2 = value2 WHERE condition;
    public boolean updateDataBasicImpl(){

        return true;
    }

    public boolean updateDataImpl() throws Exception {
        String tablename=((Token)statement.get(1)).image;
        Table table= FromStatement.from(tablename);
        List changes= (List) statement.get(3);
        List conditions= (List) statement.get(5);
        Table subTable= WhereStatament.whereImpl(table,conditions);
        table.updateTable(changes,subTable);
        table.printTable(null);
        return true;
    }

}
