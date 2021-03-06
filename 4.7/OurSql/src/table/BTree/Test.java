package table.BTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {


        // 设置类成员属性
        HashMap propertyMap = new HashMap();
        propertyMap.put("id", Class.forName("java.lang.Integer"));
        propertyMap.put("name", Class.forName("java.lang.String"));
        propertyMap.put("address", Class.forName("java.lang.String"));
        BPlusTree<CglibBean, Integer> b = new BPlusTree<>(4);
        long time1 = System.nanoTime();

        for (int i = 100; i >=0; i--) {
            CglibBean bean = new CglibBean(propertyMap);
            bean.setValue("id", new Integer(i));
            bean.setValue("name", "test");
            bean.setValue("address", "789");
            b.insert(bean,(Integer) bean.getValue("id"));
        }
//        b.getNodes(b.getRoot());
//        System.out.println("-----------------------------------------------------");

        //测试大于小于
//        List l=b.getMiddleDatas(23,57);
//        for(int i=0;i<l.size();i++){
//            CglibBean bean=(CglibBean)l.get(i);
//            System.out.println((Integer) bean.getValue("id"));
//        }
        long time2 = System.nanoTime();

        CglibBean b1 = b.select(23);
        long time3 = System.nanoTime();

        for (int i = 100; i >=0; i--) {
            b.delete(i);
        }
        long time4 = System.nanoTime();

        for (int i = 100; i >=0; i--) {
            CglibBean bean = new CglibBean(propertyMap);
            bean.setValue("id", new Integer(i));
            bean.setValue("name", "test");
            bean.setValue("address", "789");
            b.insert(bean,(Integer) bean.getValue("id"));
//            p = new Product(i, "test", 1.0 * i);
//            b.insert(p, p.getId());
        }
//        b.getNodes(b.getRoot());

        long time5 = System.nanoTime();
        System.out.println("插入耗时: " + (time2 - time1));
        System.out.println("查询耗时: " + (time3 - time2));
        System.out.println("删除耗时: " + (time4 - time3));
        System.out.println("再次插入耗时: " + (time5 - time4));


//        List<Object> data=b.getDatas();
//        SecondIndex <Object,Integer> m=new SecondIndex<>();
//        Map<Object,List<Integer>> testmap=m.createIndex(data,"id","address");
//        for (Object entry : testmap.keySet()) {
//            System.out.println("key : " + entry);
//            List templist=(List)testmap.get(entry);
//            System.out.println("value: ");
//            for(int j=0;j<templist.size();j++){
//                System.out.print(templist.get(j)+"  ");
//            }
//        }
    }
}
