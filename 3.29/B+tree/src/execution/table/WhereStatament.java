package execution.table;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.Table;

import java.util.List;

import static parsing.SqlParserConstants.*;

public class WhereStatament {

    public static Table whereAnd(Table t1,Table t2) throws ClassNotFoundException {
        BPlusTree b1=t1.getTree();
        BPlusTree b2=t2.getTree();
        BPlusTree returnTree= BPlusTreeTool.mergeTreeAnd(b1,b2);
        Table t=new Table(t1.getTableDescriptor(),returnTree);
        return t;
    }


    public static Table whereOr(Table t1,Table t2) throws ClassNotFoundException {
        BPlusTree b1=t1.getTree();
        BPlusTree b2=t2.getTree();
        BPlusTree returnTree=BPlusTreeTool.mergeTreeOr(b1,b2);
        Table t=new Table(t1.getTableDescriptor(),returnTree);
        return t;
    }

    public static Table compare(Table table, String attribute, int type, Comparable compare, String primaryKey) throws ClassNotFoundException {
        BPlusTree b=table.getTree();
        BPlusTree returnTree=new BPlusTree();
        List btree=b.getDatas();
        switch (type){
            case EQ:{
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)==0){
                        returnTree.insert(temp, (Comparable) temp.getValue(primaryKey));
                    }
                }
                break;
            }
            case LESS_THAN_OPERATOR:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)<0){
                        returnTree.insert(temp, (Comparable) temp.getValue(primaryKey));
                    }
                }
                break;
            case GREATER_THAN_OPERATOR:
                for(int i=0;i<btree.size();i++){
                    CglibBean temp= (CglibBean) btree.get(i);
                    Comparable c= (Comparable) temp.getValue(attribute);
                    if(c.compareTo(compare)>0){
                        returnTree.insert(temp, (Comparable) temp.getValue(primaryKey));
                    }
                }
                break;
        }
        Table t=new Table(table.getTableDescriptor(),returnTree);
        return t;
    }
}
