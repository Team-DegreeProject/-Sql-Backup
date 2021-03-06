package execution.data;

import execution.FromStatement;
import parsing.Token;
import table.Table;

import java.util.List;

public class InsertDataStatement {

    List statement;

    public InsertDataStatement(List tokens){
        statement=tokens;
    }

    //2.1 表中插入一行
    //2.1.1 INSERT INTO table1 (column1, coulumn2,…) VALUES (value1,
    //value2 , …);//value = number or text;
    //2.1.2 INSERT INTO table1 VALUES (value1, value2,…) //值序列与表中列的顺序匹
    //配
    //2.2 表中插入多行
    //2.2.1 INSERT INTO table1 VALUES (value1, value2,…), (value1, value2,…),…;
    //2.2.2 INSERT INTO table1 (name1, name2) VALUES (value1, value2), (value1,
    //value2),…;
    public boolean insertDataImpl() throws Exception {
        String tablename=((Token)statement.get(2)).image;
        Table table= FromStatement.from(tablename);
        List<Token> attibutes= (List<Token>) statement.get(3);
        table.insertRows(attibutes,statement,5);
        table.printTable(null);
        return true;
    }
}
