package table.type.date;

import java.sql.Time;

public class SqlTime extends Time {
    public SqlTime(int hour, int minute, int second) {
        super(hour, minute, second);
    }
}
