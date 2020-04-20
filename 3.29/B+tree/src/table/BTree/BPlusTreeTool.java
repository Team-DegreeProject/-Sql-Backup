package table.BTree;

import table.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BPlusTreeTool {

    /**
     * 将list转化为b树
     * @param list
     * @return
     */
    public static BPlusTree listToBplusTree(List<CglibBean> list){
        BPlusTree<CglibBean, Integer> b = new BPlusTree<>(4);
        for (int i=0;i<list.size();i++){
            CglibBean c=list.get(i);
            b.insert(c,(Integer)c.getValue("id"));
        }
        return b;
    }

    /**
     * 取两棵b树的交集
     * @param b1
     * @param b2
     * @return
     */
    public static BPlusTree mergeTreeAnd(BPlusTree b1,BPlusTree b2){
        List l1=b1.getDatas();
        List l2=b2.getDatas();
        List l3=mergeListAnd(l1,l2);
        BPlusTree b=listToBplusTree(l3);
        return b;
    }

    /**
     * 取两个b树的并集
     * @param b1
     * @param b2
     * @return
     */
    public static BPlusTree mergeTreeOr(BPlusTree b1,BPlusTree b2){
        List l1=b1.getDatas();
        List l2=b2.getDatas();
        List l3=mergeListOr(l1,l2);
        return listToBplusTree(l3);
    }

    /**
     * 两个list的并集
     * @param l1
     * @param l2
     * @return 并集
     */
    public static List mergeListOr(List l1, List l2){
        if(l1!=null&&l2!=null){
            l1.removeAll(l2);//l1中去掉两者共同有的数据
            l2.addAll(l1);
            return l2;
        } else if (l1 == null&&l2==null) {
            return null;
        } else if(l1==null){
            return l2;
        }
        return l1;
    }

    /**
     * 两个list的交集
     * @param l1
     * @param l2
     * @return 交集
     */
    public  static List mergeListAnd(List l1,List l2){
        if(l1!=null&&l2!=null){
            l1.retainAll(l2);
            return l1;
        }else if(l1==null&&l2==null){
            return null;
        }else if(l1==null){
            return l2;
        }
        return l1;
    }

    public static void printList(List list,String attribute){
        for(int i=0;i<list.size();i++){
            CglibBean c= (CglibBean) list.get(i);
            System.out.println(c.getValue(attribute));
        }
    }

    public static void printBPlusTree(BPlusTree b,String attribute){
        List list=b.getDatas();
        printList(list,attribute);
    }

    public static void printList(List list){
        boolean first=true;
        List<String> attribute=new ArrayList();
        for(int i=0;i<list.size();i++){
            CglibBean c= (CglibBean) list.get(i);
            if(first){
                Set s=c.beanMap.keySet();
                Iterator it=  s.iterator();
                while(it.hasNext()){
                    String att= (String) it.next();
                    attribute.add(att);
                }
                first=false;
//                for(int j=0;j<attribute.size();j++){
//                    System.out.print(attribute.get(j));
//                    if(j!=attribute.size()-1){
//                        System.out.print(",");
//                    }else{
//                        System.out.println(";");
//                    }
//                }
            }
            for(int j=0;j<attribute.size();j++){
                System.out.print(c.getValue(attribute.get(j)));
                if(j!=attribute.size()-1){
                    System.out.print(",");
                }else{
                    System.out.println(";");
                }
            }
        }
    }

    public static void printBPlusTree(BPlusTree b){
        List list=b.getDatas();
        printList(list);
        System.out.println("-------------------------------------------------------");
    }

    public static List<CglibBean> getParticularAttribute(Table t, String attribute, Object value){
        BPlusTree b=t.getTree();
        List<CglibBean> returnlist=new ArrayList();
        List list=b.getDatas();
        for(int i=0;i<list.size();i++){
            CglibBean c= (CglibBean) list.get(i);
            Object att=c.getValue(attribute);
            if(att.equals(value)){
                returnlist.add(c);
            }
        }
        return returnlist;
    }
}
