package execution;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.Table;
import table.type.Distinct;
import table.type.PrimaryKey;

import java.util.HashMap;
import java.util.List;

import static parsing.SqlParserConstants.DISTINCT;
import static parsing.SqlParserConstants.PRIMARY_KEY;
import static table.type.SqlConstantImpl.sqlMap;

public class DistinctStatement {

    public static Table distinctImpl(Table t,List<String> names) throws ClassNotFoundException {
        HashMap property=new HashMap();
        property.put("distinct",Class.forName(sqlMap.get(DISTINCT)));
        property.put("primary key",Class.forName(sqlMap.get(PRIMARY_KEY)));
        List datas=t.getTree().getDatas();
        BPlusTree tree=new BPlusTree();
        for(int i=0;i<datas.size();i++){
            CglibBean c= (CglibBean) datas.get(i);
            CglibBean nc=new CglibBean(property);
            Distinct dc=new Distinct();
            for(int j=0;j<names.size();j++){
                Comparable com= (Comparable) c.getValue(names.get(j));
                dc.addDistinct(names.get(j),com);
            }
            nc.setValue("distinct",dc);
            nc.setValue("primary key",c.getValue("primary key"));
//            System.out.println(dc+"----------"+c.getValue("primary key"));
            tree.insert(nc,dc);
        }
//        BPlusTreeTool.printBPlusTree(tree,property);
        BPlusTree nt=new BPlusTree();
        BPlusTree ot=t.getTree();
        List sd=tree.getDatas();
//        BPlusTreeTool.printBPlusTree(ot,t.getPropertyMap());
//        t.printTable(null);
        for(int i=0;i<sd.size();i++){
            CglibBean c= (CglibBean) sd.get(i);
            PrimaryKey pk= (PrimaryKey) c.getValue("primary key");
            CglibBean co= (CglibBean) ot.select(pk);
//            System.out.println(pk);
//            pk.printPK();
//            System.out.println(co);
            if(co!=null){
                nt.insert(co,pk);
            }
        }
        t.setTree(nt);
//        t.printTable(null);
        return t;
    }

}
