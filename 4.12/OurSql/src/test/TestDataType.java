package test;

import java.util.HashMap;


import table.type.SqlConstantImpl;
import table.type.SqlType;

import static parsing.SqlParserConstants.INT;

public class TestDataType {
    public static void main(String[] args){
        try {
            Class c=Class.forName("table.type.number.SqlInt");
            SqlType o=(SqlType)c.newInstance();
            o.setValue("1");
            System.out.println(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
