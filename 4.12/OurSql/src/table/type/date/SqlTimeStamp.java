package table.type.date;

import table.type.SqlType;

import java.sql.Timestamp;
//yyyy-[m]m-[d]d hh:mm:ss
public class SqlTimeStamp implements SqlType {

    private  Timestamp timestamp=null;

    public SqlTimeStamp(){}

    public SqlTimeStamp(String ts){
        this.timestamp=Timestamp.valueOf(ts);
    }

   @Override
    public void setValue(String o) {
       this.timestamp=Timestamp.valueOf(o);
    }


    @Override
    public int compareTo(Object o) {
        int i=this.timestamp.compareTo(((SqlTimeStamp)o).getTimestamp());
        return i;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return timestamp.toString();
    }

    @Override
    public SqlType addOne() {

        return this;
    }
}
