package table.type.number;


//float型的数据存储大小为8个字节，可精确到小数点后第15位数字。
//这种数据类型的数据存储范围为从-1.79E+308～-2.23E-308，0和2.23E+308～1.79E+308。
//FLOAT(size,d) 带有浮动小数点的小数字。在括号中规定最大位数。在 d 参数中规定小数点右侧的最大位数。


public class SqlFloat implements Comparable<SqlFloat>{
    private Float data;

    public SqlFloat(float f){
        this.data=f;
    }

    public void setData(float data) {
        this.data = data;
    }

    public float getData() {
        return data;
    }


    @Override
    public int compareTo(SqlFloat o) {
        return this.data.compareTo(o.data);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
