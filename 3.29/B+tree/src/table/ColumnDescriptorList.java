package table;

import table.column.ColumnDescriptor;

import java.util.ArrayList;

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
}
