package table.column;


import table.TableDescriptor;

public class ColumnDescriptor{

    private TableDescriptor table;
    private String columnName;
    private int columnPosition;
    private DataTypeDescriptor columnType=null;
    private long autoincStart=0;
    private long autoincInc=0;
    private long autoincValue=0;
    private boolean autoincCycle=false;
    private Object columnDefaultValue=null;


    /**
     * ColumnDescriptor的构造器
     *@param columnDefaultInfo 列的默认信息
     * @param columnName        列的名字
     * @param columnPosition    列的位置
     * @param columnType        列的DataTypeDescriptor
     * @param table             列所在的表描述
     * @param autoincStart      默认自增长开始值
     * @param autoincInc        是否是自增长列
     */
    public ColumnDescriptor(String columnName, int columnPosition,
                            DataTypeDescriptor columnType,
                            TableDescriptor table, long autoincStart, long autoincInc, boolean autoincCycle,Object columnDefaultInfo) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
        if (table != null) {
            this.table = table;
        }
        this.autoincStart = autoincStart;
        this.autoincValue = autoincStart;
        this.autoincInc = autoincInc;
        this.autoincCycle = autoincCycle;
        this.columnDefaultValue=columnDefaultInfo;
    }

    public ColumnDescriptor(String columnName, int columnPosition,
                            DataTypeDescriptor columnType,
                            TableDescriptor table, long autoincStart, long autoincInc, boolean autoincCycle) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
        if (table != null) {
            this.table = table;
        }
        this.autoincStart = autoincStart;
        this.autoincValue = autoincStart;
        this.autoincInc = autoincInc;
        this.autoincCycle = autoincCycle;
    }

    public ColumnDescriptor(String columnName, int columnPosition,
                             DataTypeDescriptor columnType, TableDescriptor table) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
        if (table != null) {
            this.table = table;
        }
        this.autoincStart = autoincStart;
        this.autoincValue = autoincStart;
        this.autoincInc = autoincInc;
        this.autoincCycle = autoincCycle;
    }

//    long autoinc_create_or_modify_Start_Increment = -1;

    public ColumnDescriptor(String columnName, int columnPosition,
                            DataTypeDescriptor columnType) {
        this.columnName = columnName;
        this.columnPosition = columnPosition;
        this.columnType = columnType;
    }



    public DataTypeDescriptor getType() {
        return columnType;
    }


    /**
     * 自动增长的开始值
     */
    public long getAutoincStart() {
        return autoincStart;
    }

    public boolean getAutoincCycle() {
        return autoincCycle;
    }

    public long getAutoincInc() {
        return autoincInc;
    }

    /**
     * 获取增长列当前的值
     */
    public long getAutoincValue() {
        return autoincValue;
    }

    public void setPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public boolean isAutoincrement() {
        return (autoincInc != 0);
    }

    public String getColumnName() {
        return columnName;
    }

    /**
     * 获取列的位置
     */
    public int getPosition() {
        return columnPosition;
    }

//    public long getAutoinc_create_or_modify_Start_Increment() {
//        return autoinc_create_or_modify_Start_Increment;
//    }

    /**
     * 获取列默认信息
     */
    public Object getDefaultInfo() {
        return columnDefaultValue;
    }


    public void setTableDescriptor(TableDescriptor tableDescriptor) {
        this.table = tableDescriptor;
    }

    public TableDescriptor getTableDescriptor(){ return table; }



}
