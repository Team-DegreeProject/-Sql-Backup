package table.type.date;
import java.sql.Date;
public class SqlYear extends Date {
    public SqlYear(int year) {
        super(year,0, 0);
    }
}
