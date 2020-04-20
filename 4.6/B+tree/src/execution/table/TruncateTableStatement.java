package execution.table;

import execution.ExecuteStatement;
import parsing.Token;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
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
        Table database= ExecuteStatement.db.getDatabase();
        List<CglibBean> cl=BPlusTreeTool.getParticularAttribute(database,"tablename",name);
        if(cl.size()<1){
            throw new Exception("There is no table needed to be truncated.");
        }
        for(int i=0;i<cl.size();i++){
            CglibBean c=cl.get(i);
//            Table t= (Table) c.getValue("table");
//            t.cleanAllData();
        }
        return true;

    }
}
