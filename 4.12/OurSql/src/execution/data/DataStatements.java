package execution.data;

import java.util.List;

public class DataStatements {

    public static void insertData(List tokens){
        try {
            InsertDataStatement insertDataStatement=new InsertDataStatement(tokens);
            insertDataStatement.insertDataImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteData(List tokens){
        try {
            DeleteDataStatement deleteDataStatement=new DeleteDataStatement(tokens);
            deleteDataStatement.deleteDataImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateData(List tokens) {
        try {
            UpdateDataStatement updateDataStatement=new UpdateDataStatement(tokens);
            updateDataStatement.updateDataImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
