package table.type.number;

import table.type.SqlType;

import java.math.BigDecimal;

public class SqlDecimal implements SqlType {
    private BigDecimal data=new BigDecimal(0);
    private int scale=-1;
    private int precision=-1;

    public SqlDecimal(BigDecimal data){

        this.data=data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public SqlDecimal(Double d,int scale,int precision){
        this.data=new BigDecimal(d);
        this.scale=scale;
        this.precision=precision;
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


    public void setData(BigDecimal data) {
        this.data = data;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public SqlType addOne() throws Exception {
        return new SqlDecimal((this.data.doubleValue()+1),scale,precision);
    }

    @Override
    public void setValue(String o) {
        setData(new BigDecimal(Double.parseDouble(o)));
    }

    @Override
    public int compareTo(Object o) {
        return data.compareTo(((SqlDecimal)o).data);
    }
}
