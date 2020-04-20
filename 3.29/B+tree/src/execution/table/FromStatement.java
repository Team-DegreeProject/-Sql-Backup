package execution.table;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.Table;

import java.util.ArrayList;
import java.util.List;

public class FromStatement {

    public static List<Table> from(Table database,String tableName){
        BPlusTree databaseTable=database.getTree();
        List<Table> tables=new ArrayList<>();
        List<CglibBean> list=BPlusTreeTool.getParticularAttribute(database,"tablename",tableName);
        for(int i=0;i<list.size();i++){
            CglibBean c=list.get(i);
            tables.add((Table) c.getValue("table"));
        }
        return tables;
    }

}
