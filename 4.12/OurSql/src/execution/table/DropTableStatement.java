package execution.table;

import execution.ExecuteStatement;
import execution.WhereStatament;
import parsing.Token;
import table.Table;
import table.TableDescriptor;
import java.util.List;
import static parsing.SqlParserConstants.EQ;


//3 DROP TABLE
//3.1 DROP TABLE 一个表
//3.1.1 DROP TABLE tbname；
//3.2 DROP TABLE 多个表
//3.2.1 DROP TABLE TBNAME1,TBNAME2,….;
public class DropTableStatement {
    List statement=null;
    public DropTableStatement(List tokens){
        statement=tokens;
    }

    public boolean dropTableImpl() throws ClassNotFoundException {
        Table database=ExecuteStatement.db.getDatabase();
        List names= (List) statement.get(2);
        for(int i=0;i<names.size();i++){
            String name=((Token)names.get(i)).image;
            Table delete= WhereStatament.compare(database,"tablename",EQ,name);
            database.deleteRows(delete);
        }
        database.printTable();
        return true;
    }
}
