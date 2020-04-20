package table.type.number;


//real型数据的存储大小为4个字节，可精确到小数点后第7位数字。
//这种数据类型的数据存储范围为从-3.40E+38～3.40E+38。


public class SqlReal implements Comparable<SqlReal>{
    private Float data;

    public SqlReal(float f){
        this.data=f;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(float data) {
        this.data = data;
    }

    public float getData() {
        return data;
    }

    public void changeRange() throws Exception {
        if(-3.40E+38<=data&&data<=3.40E+38){
        }else{
            throw new Exception("The number is not in the real range!");
        }

    }

    @Override
    public int compareTo(SqlReal o) {
        return this.data.compareTo(o.data);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
