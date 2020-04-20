package table.type.number;

import java.math.BigDecimal;

public class SqlDecimal implements Comparable<SqlDecimal>{
    private BigDecimal data;
    private int scale=-1;
    private int precision=-1;

    public SqlDecimal(BigDecimal data){
        this.data=data;
    }

    public SqlDecimal(BigDecimal data,int scale,int precision){
        this.data=data;
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
            if(size>=precision){
                data.setScale(precision,BigDecimal.ROUND_HALF_UP);
            }else{
                data.setScale(size,BigDecimal.ROUND_HALF_UP);
            }
        }
    }

    @Override
    public int compareTo(SqlDecimal o) {
        return data.compareTo(o.data);
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public BigDecimal getData(){
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
