package table.type.number;

import java.math.BigDecimal;

public class SqlDouble implements Comparable<SqlDouble>{
    private int scale=-1;
    private int precision=-1;
    private Double data;

    public SqlDouble(double d){
        data=d;
    }

    public SqlDouble(double d,int scale,int precision){
        data=d;
        this.scale=scale;
        this.precision=precision;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeRange() throws Exception {
        if(scale==-1&&precision==-1){
        }else if(scale<=precision){
            throw new Exception("Scale should not be smaller than or equal to precision.");
        }else{
            int temp=(int)data.doubleValue();
            int size=scale-String.valueOf(temp).length();
            BigDecimal bd=BigDecimal.valueOf(data);
            if(size>=precision){
                bd.setScale(precision,BigDecimal.ROUND_HALF_UP);
            }else{
                bd.setScale(size,BigDecimal.ROUND_HALF_UP);
            }
            data=bd.doubleValue();
        }
    }

    public void setData(double data) {
        this.data = data;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }

    public double getData() {
        return data;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public int compareTo(SqlDouble o) {
        return this.data.compareTo(o.data);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
