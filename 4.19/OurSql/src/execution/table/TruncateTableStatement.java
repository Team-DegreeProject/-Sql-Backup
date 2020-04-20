package execution.table;

import execution.FromStatement;
import parsing.Token;
import table.Table;

import java.util.List;

//4 TRUNCATE TABLE 删除表中所有数据
//4.1 TRUNCATE tbname；
public class TruncateTableStatement {

    public List statement;

    public TruncateTableStatement(List tokens){
        statement=tokens;
    }

    public boolean truncateTableImpl() throws Exception {
        String name=((Token)statement.get(1)).image;
        Table truncate=FromStatement.from(name);
        truncate.cleanAllData();
        truncate.printTable(null);
        return true;
    }
}
