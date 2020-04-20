package execution.table;

import parsing.Token;
import table.column.DataTypeDescriptor;

import java.util.HashMap;
import java.util.List;

import static parsing.SqlParserConstants.*;

public class DMLTool {
    public static void andCondition(HashMap condition, char t, int num){
        if(t=='>'){
            if(condition.get('>')==null){
                condition.put('>',num);
            }else{
                int g=(int)condition.get('>');
                if(num>g){
                    condition.replace('>',num);
                }
            }
        }else if(t=='<'){
            if(condition.get('<')==null){
                condition.put('<',num);
            }else{
                int g=(int)condition.get('<');
                if(num<g){
                    condition.replace('<',num);
                }
            }
        }else if(t=='='){
            if(condition.get('=')==null){
                condition.put('=',num);
            }else{
                System.out.println("Warning:There are two =");
            }
        }

    }

    public static void orCondition(HashMap condition,char t,int num){
        if(t=='>'){
            if(condition.get('>')==null){
                condition.put('>',num);
            }else{
                int g=(int)condition.get('>');
                if(num<g){
                    condition.replace('>',num);
                }
            }
        }else if(t=='<'){
            if(condition.get('<')==null){
                condition.put('<',num);
            }else{
                int g=(int)condition.get('<');
                if(num>g){
                    condition.replace('<',num);
                }
            }
        }else if(t=='='){
            if(condition.get('=')==null){
                condition.put('=',num);
            }else{
                System.out.println("Warning:There are two =");
            }
        }
    }


    public static DataTypeDescriptor analyseOneRow(int k,List tokens){
        DataTypeDescriptor dataType=null;
        if(k==0){
            dataType= new DataTypeDescriptor( ((Token)tokens.get(2)).kind  );
            for(int i=3;i<tokens.size();i++){
                Token t= (Token) tokens.get(i);
                setType(dataType,t);
            }
        }else if(k==1){
            dataType= new DataTypeDescriptor( ((Token)tokens.get(1)).kind  );
            for(int i=2;i<tokens.size();i++){
                Token t= (Token) tokens.get(i);
                setType(dataType,t);
            }
        }

        return dataType;
    }

    public static void setType(DataTypeDescriptor d,Token t){
        if(t.kind==PRIMARY_KEY){
            d.setPrimaryKey(true);
        }else if(t.kind==NOT_NULL){
            d.setNullable(false);
        }else if(t.kind==NUMBER){
            if(d.getScale()==-1){
                d.setScale(Integer.parseInt(t.image));
            }else{
                d.setPrecision(Integer.parseInt(t.image));
            }
        }

    }
}
