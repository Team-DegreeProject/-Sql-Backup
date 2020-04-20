package execution;

import javafx.scene.control.Tab;
import parsing.Token;
import table.ColumnDescriptorList;
import table.TableDescriptor;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;
import table.type.SqlType;

import java.util.HashMap;
import java.util.List;

import static parsing.SqlParserConstants.*;

public class DMLTool {
//    public static void andCondition(HashMap condition, char t, int num){
//        if(t=='>'){
//            if(condition.get('>')==null){
//                condition.put('>',num);
//            }else{
//                int g=(int)condition.get('>');
//                if(num>g){
//                    condition.replace('>',num);
//                }
//            }
//        }else if(t=='<'){
//            if(condition.get('<')==null){
//                condition.put('<',num);
//            }else{
//                int g=(int)condition.get('<');
//                if(num<g){
//                    condition.replace('<',num);
//                }
//            }
//        }else if(t=='='){
//            if(condition.get('=')==null){
//                condition.put('=',num);
//            }else{
//                System.out.println("Warning:There are two =");
//            }
//        }
//
//    }
//
//    public static void orCondition(HashMap condition,char t,int num){
//        if(t=='>'){
//            if(condition.get('>')==null){
//                condition.put('>',num);
//            }else{
//                int g=(int)condition.get('>');
//                if(num<g){
//                    condition.replace('>',num);
//                }
//            }
//        }else if(t=='<'){
//            if(condition.get('<')==null){
//                condition.put('<',num);
//            }else{
//                int g=(int)condition.get('<');
//                if(num>g){
//                    condition.replace('<',num);
//                }
//            }
//        }else if(t=='='){
//            if(condition.get('=')==null){
//                condition.put('=',num);
//            }else{
//                System.out.println("Warning:There are two =");
//            }
//        }
//    }


    public static SqlType convertToValue(String att, String str, HashMap propertyMap) throws IllegalAccessException, InstantiationException {
        Class c= (Class) propertyMap.get(att);
        SqlType value=(SqlType)c.newInstance();
        value.setValue(str);
        return value;
    }



    public static ColumnDescriptor analyseOneRow(int k, List tokens,int position){
        DataTypeDescriptor dataType=null;
        ColumnDescriptor column=new ColumnDescriptor(position);
        boolean comment=false;
        String columnName=null;
        if(k==0){
            columnName=((Token)tokens.get(1)).image;
            dataType= new DataTypeDescriptor( ((Token)tokens.get(2)).kind  );
            for(int i=3;i<tokens.size();i++){
                Token t= (Token) tokens.get(i);
                if(comment){
                    column.setComment(t.image);
                    comment=false;
                }else {
                    comment = setType(dataType, t, column);
                }
            }
        }else if(k==1){
            columnName=((Token)tokens.get(0)).image;
            dataType= new DataTypeDescriptor( ((Token)tokens.get(1)).kind  );
            for(int i=2;i<tokens.size();i++){
                Token t= (Token) tokens.get(i);
                if(comment){
                    column.setComment(t.image);
                    comment=false;
                }else {
                    comment = setType(dataType, t, column);
                }
            }
        }
        column.setColumnName(columnName);
        column.setColumnType(dataType);
        return column;
    }

    public static boolean setType(DataTypeDescriptor d,Token t,ColumnDescriptor cd){
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
        }else if(t.kind==AUTO_INCREMENT){
            cd.setAutoincInc(true);
        }else if(t.kind==COMMENT){
            return true;
        }
        return false;
    }

//    public void setTablePrimaryKey(TableDescriptor td){
//        ColumnDescriptorList pk=td.getPrimaryKey();
//        pk=new ColumnDescriptorList();
//        ColumnDescriptorList cdl=td.getColumnDescriptorList();
//        for(int i=0;i<cdl.size();i++){
//            ColumnDescriptor c=cdl.elementAt(i);
//            boolean b=c.getType().isPrimaryKey();
//            if(b){
//                pk.add(c);
//            }
//        }
//    }
}
