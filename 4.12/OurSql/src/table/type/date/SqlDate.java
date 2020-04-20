package table.type.date;


import table.type.SqlType;
import java.sql.Date;

//yyyy-[m]m-[d]d
public class SqlDate implements SqlType {

    private Date date=null;

    public SqlDate(){}

    public SqlDate(String date){
        this.date= Date.valueOf(date);
    }

    @Override
    public void setValue(String o) {
        this.date=Date.valueOf(o);
    }


    @Override
    public int compareTo(Object o) {
        int i=date.compareTo(((SqlDate)o).getDate());
        return i;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public SqlType addOne() {
        return this;
    }
}
