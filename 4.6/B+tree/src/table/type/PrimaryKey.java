package table.type;

import table.column.ColumnDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrimaryKey implements Comparable{
    List<Comparable> list=null;
    public PrimaryKey(List list){
        this.list=list;
    }
    public PrimaryKey(){
        list=new ArrayList<Comparable>();
    }

    public void addPrimaryKey(Comparable t){
        list.add(t);
    }

    public  Comparable getPrimaryKey(int i){
        return list.get(i);
    }

    public List<Comparable> getPrimaryKeys(){
        return list;
    }

    @Override
    public int compareTo(Object o) {
        PrimaryKey pk2=(PrimaryKey)o;
        int outcome=0;
        for(int i=0;i<list.size();i++){
            Comparable c1=list.get(i);
            Comparable c2= pk2.getPrimaryKey(i);
            outcome=c1.compareTo(c2);
            if(outcome!=0){
                return outcome;
            }
        }
        return outcome;
    }
}
