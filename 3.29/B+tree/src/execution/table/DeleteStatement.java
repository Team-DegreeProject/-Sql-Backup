package execution.table;

import table.BTree.BPlusTree;
import table.BTree.BPlusTreeTool;
import table.BTree.CglibBean;
import table.type.SqlConstant;

import java.util.List;

public class DeleteStatement implements SqlConstant {
    BPlusTree<CglibBean, Integer> b ;
    public DeleteStatement(BPlusTree bt){
        b= bt;
    }

//    public boolean deleteImplementation(HashMap condition){
//        List list=where(condition);
//        if(list.isEmpty()){
//            System.out.println("There is no row needed to be deleted.");
//            return false;
//        }
//        for(int i=0;i<list.size();i++){
//            CglibBean d = (CglibBean) list.get(i);
//            b.delete((Integer) d.getValue("id"));
//        }
//        System.out.println(list==null);
//        return true;
//    }


//    public List<Object> where(HashMap conditions){
//        List list=null;
//        while(!conditions.isEmpty()){
//            HashMap<Character, Integer> conditionAnd= (HashMap<Character, Integer>) conditions.get("and");
//            HashMap<Character, Integer> conditionOr= (HashMap<Character, Integer>) conditions.get("or");
//            if(conditionAnd!=null){
//               list=whereAnd(conditionAnd);
//               conditions.remove("and");
//            }else{
////                list=whereOr(conditionOr);
//                conditions.remove("or");
//            }
//        }
//        return list;
//    }


    public static BPlusTree whereAnd(BPlusTree b1,BPlusTree b2){
        BPlusTree returnTree=BPlusTreeTool.mergeTreeAnd(b1,b2);
        return returnTree;
    }


    public static BPlusTree whereOr(BPlusTree b1,BPlusTree b2){
        BPlusTree returnTree=BPlusTreeTool.mergeTreeOr(b1,b2);
        return returnTree;
    }
//    public static BPlusTree compare(BPlusTree b,List list){
//
//    }

    public static BPlusTree compare(BPlusTree b,String attribute,int type,Comparable compare,String primaryKey){
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
        return returnTree;
    }


//    public List compareThreeType(Character c,int num){
//        List<Object> list=null;
//        if(c=='='){
//            CglibBean t=null;
//            t=b.select(num);
//            list = new ArrayList<>();
//            if(t!=null){
//                list.add(t);
//            }
//        }else if(c=='<'){
//            list=b.getSmallDatas(num);
//        }else{
//            list=b.getBigDatas(num);
//        }
//        return list;
//    }

}
