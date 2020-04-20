package table.type.date;

import java.sql.Timestamp;

public class SqlTimeStamp extends Timestamp {
    public SqlTimeStamp(int year, int month, int date, int hour, int minute, int second, int nano) {
        super(year, month, date, hour, minute, second, nano);
    }
}
