package table.type.number;

import table.type.SqlType;
import table.type.date.SqlDate;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class SqlDouble implements SqlType {
    private int scale=-1;
    private int precision=-1;
    private Double data=0.0;

    public SqlDouble(){}

    public SqlDouble(double d){
        data=d;
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        }else if(precision==-1){
            int temp=data.intValue();
            int length=String.valueOf(temp).length();
            if(length>=scale){
                String str=String.valueOf(temp);
                str=str.substring(0,scale);
                data=Double.parseDouble(str);
            }else{
                int size=scale-String.valueOf(temp).length();
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setGroupingUsed(false);
                nf.setMaximumFractionDigits(size);
                data=Double.parseDouble(nf.format(data));
            }
        }else{
            int temp=data.intValue();
            int size=scale-String.valueOf(temp).length();
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(false);
            if(size>=precision){
                nf.setMaximumFractionDigits(precision);
            }else{
                nf.setMaximumFractionDigits(size);
            }
            data=Double.parseDouble(nf.format(data));
        }
    }

    public void setData(double data){
        this.data = data;
//        this.data = data;
//        System.out.println("=============setValue"+this.data);
        try {
            changeRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setScale(int scale) throws Exception {
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

    public void setPrecision(int precision) throws Exception {
        this.precision = precision;
    }

    @Override
    public void updateValue() throws Exception {
        changeRange();
    }


    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public SqlType addOne() throws Exception {
        return new SqlDouble((this.data+1),this.scale,this.precision);
    }

    @Override
    public void setValue(String o){
        setData(Double.valueOf(o));
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((SqlDouble)o).data);
    }
}
