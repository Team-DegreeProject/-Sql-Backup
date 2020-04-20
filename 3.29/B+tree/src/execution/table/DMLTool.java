package execution.table;

import java.util.HashMap;

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
}
