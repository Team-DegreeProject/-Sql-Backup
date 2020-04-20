package table;

import parsing.Token;
import table.column.ColumnDescriptor;
import table.column.DataTypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class ColumnDescriptorList extends ArrayList<ColumnDescriptor> {

    public ColumnDescriptor elementAt(int n) {
        return get(n);
    }

    /**
     * 根据表id和列id获取列相关描述
     */
    public ColumnDescriptor getColumnDescriptor(int columnID) {
        ColumnDescriptor returnValue = null;
        for (ColumnDescriptor columnDescriptor : this) {
            if ((columnID == columnDescriptor.getPosition())) {
                returnValue = columnDescriptor;
                break;
            }
        }
        return returnValue;
    }

    public ColumnDescriptor getColumnDescriptor(String columnName) {
        ColumnDescriptor returnValue = null;
        for (ColumnDescriptor columnDescriptor : this) {
            if (columnName.equals(columnDescriptor.getColumnName()) ) {
                returnValue = columnDescriptor;
                break;
            }
        }
        return returnValue;
    }

    public void addColumns(List<ColumnDescriptor> list){
        for(int i=0;i<list.size();i++){
            this.add(list.get(i));
        }
    }

    public void dropColumn(String columnName){
        ColumnDescriptor returnValue = null;
        for (ColumnDescriptor columnDescriptor : this) {
            if (columnName.equals(columnDescriptor.getColumnName()) ) {
                returnValue = columnDescriptor;
                break;
            }
        }
        this.remove(returnValue);
    }


    public boolean checkHavePrimaryKey(List<Token> columnNames) {
        for (ColumnDescriptor columnDescriptor : this) {
            boolean in=false;
            for(int i=0;i<columnNames.size();i++){
                String columnName=columnNames.get(i).image;
                if (columnName.equals(columnDescriptor.getColumnName()) ) {
                    in=true;
                }
            }
            if(!in){
                return false;
            }
        }
        return true;
    }

    public boolean checkNotNull(List<Token> columnNames,List<List<Token>> values){
        System.out.println(this.size());
        for (ColumnDescriptor columnDescriptor : this) {
            System.out.println(columnDescriptor);
            DataTypeDescriptor dataTypeDescriptor=columnDescriptor.getType();
            System.out.println(dataTypeDescriptor.isNullable());
            if(columnDescriptor.getColumnName()!="primary key") {
                if (!dataTypeDescriptor.isNullable()) {
                    boolean b = false;
                    for (int i = 0; i < columnNames.size(); i++) {
                        String name = columnNames.get(i).image;
                        if (name.equals(columnDescriptor.getColumnName())) {
                            System.out.println(values.get(i).get(0).image);
                            if (values.get(i).get(0).image.equals("null")) {
                                return false;
                            }
                            b = true;
                            break;
                        }
                    }
                    if(b==false){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ColumnDescriptorList getAutoIncrementList(){
        ColumnDescriptorList columnDescriptors=new ColumnDescriptorList();
        for (ColumnDescriptor columnDescriptor : this) {
            if(columnDescriptor.isAutoincInc()){
                columnDescriptors.add(columnDescriptor);
            }
        }
        return columnDescriptors;
    }


}
