package execution.data;

import execution.DistinctStatement;
import execution.FromStatement;
import execution.InnerJoinStatement;
import execution.OrderByStatement;
import javafx.scene.control.Tab;
import parsing.Token;
import table.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static parsing.SqlParserConstants.*;

public class SelectDataStatement {

    public List statement;

    public SelectDataStatement(List tokens){
        statement=tokens;
    }

    public String selectDataImpl() throws ClassNotFoundException {
        HashMap from=getFrom();

        List<List<Token>> tablenames= (List<List<Token>>) statement.get(3);
//        String tablename= tablenames.get(0).get(0).image;
//        Table table= FromStatement.from(tablename);
        Table table= InnerJoinStatement.innerJoinImpl(tablenames);

        List distinctNames=checkDistinct();
        Table show= DistinctStatement.distinctImpl(table,distinctNames);
//        table.printTable(null);
//        show.printTable(null);

        List<List<Token>> columns= getColumns();
        show=table.selectSomeColumns(tablenames,columns);



        List<List<Token>> orderbys=getOrderByLists();
        List datas=OrderByStatement.orderByImpl(show,orderbys,table);

        String output=show.printTable(datas);
        table.printTable(null);

        System.out.println("=================12345=====================");
        System.out.println(output);
        return output;
    }

    public List checkDistinct(){
        List<List<Token>> list= getColumns();
        List re=new ArrayList();
        for(int i=0;i<list.size();i++){
            List<Token> l=list.get(i);
            Token t=l.get(0);
            if(t.kind==DISTINCT){
                String name="";
                if(l.size()==2){
                    name=l.get(1).image;
                }else if(l.size()==3){
                    name=l.get(3).image;
                }else if(l.size()==4){
                    name=l.get(4).image;
                }
                re.add(name);
                l.remove(0);
            }
        }
        return re;
    }



    public List<List<Token>> getColumns(){
        Object o=statement.get(1);
        if(o instanceof Token){
            if(((Token) o).kind==ASTERISK ||((Token) o).kind==ALL){
                return changeReturnList(o);
            }
        }else if(o instanceof List){
            return (List<List<Token>>) o;
        }
        return null;
    }

    public List changeReturnList(Object o){
        List ret=new ArrayList();
        List re=new ArrayList();
        re.add(o);
        ret.add(re);
        return ret;
    }

    public List getOrderByLists(){
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                if(((Token) o).kind==ORDER_BY){
                    return (List) statement.get(i+1);
                }
            }
        }
        return null;
    }

    public List getWhereLists(){
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                if(((Token) o).kind==ORDER_BY){
                    return (List) statement.get(i+1);
                }
            }
        }
        return null;
    }

    public HashMap getFrom(){
        List re=new ArrayList();
        for(int i=0;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                if(((Token) o).kind==FROM){
                    HashMap hm=getJoin(i);
                    return hm;
                }
            }
        }
        return null;
    }

    public HashMap getJoin(int s){
        HashMap hashMap=new HashMap();
        List names=new ArrayList();
        HashMap inner= new HashMap();
        HashMap left= new HashMap();
        HashMap right= new HashMap();
        List start= (List) ((List) statement.get(s+1)).get(0);
        names.add(start);

        for(int i=s+2;i<statement.size();i++){
            Object o=statement.get(i);
            if(o instanceof Token){
                Token t=(Token)o;
                if(t.kind==INNER){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
                    names.add(rl);
                    inner.put(rl,on);
                }else if(t.kind==LEFT){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
                    names.add(rl);
                    left.put(rl,on);
                }else if(t.kind==RIGHT){
                    Token nt= (Token) statement.get(i+2);
                    List rl=new ArrayList();
                    rl.add(nt);
                    List on= (List) statement.get(i+4);
                    names.add(rl);
                    right.put(rl,on);
                }
            }
        }
        hashMap.put("names",names);
        hashMap.put("start",start);
        hashMap.put("inner",inner);
        hashMap.put("left",left);
        hashMap.put("right",right);
        return hashMap;
    }

    public void dealWithFrom(HashMap from){
        List<List<Token>> names= (List<List<Token>>) from.get("names");
        List<Token> start= (List<Token>) from.get("start");
        HashMap inner= (HashMap) from.get("inner");
        HashMap left= (HashMap) from.get("left");
        HashMap right= (HashMap) from.get("right");
    }



}
