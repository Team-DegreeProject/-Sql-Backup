package test;

import table.type.number.SqlInt;
import table.type.text.SqlBlob;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;

public class testDouble {
    public static void main(String[] args) {
        Timestamp t=Timestamp.valueOf("2011-11-03 08:11:09.0");
        System.out.println(t);

    }
}
