package table.type;

import java.util.HashMap;
import java.util.Map;

public class SqlConstantImpl implements SqlConstant{

    public static Map<Integer,String> sqlMap = new HashMap();

    public SqlConstantImpl(){ constructMap(); }
    public final static int TABLE_DESCRIPTOR=500;
//    public final static int AUTO_START=501;


    private void constructMap(){
        sqlMap.put(INT,"table.type.number.SqlInt");
        sqlMap.put(DECIMAL,"table.type.number.SqlDecimal");
        sqlMap.put(DOUBLE,"table.type.number.SqlDouble");
        sqlMap.put(FLOAT,"table.type.number.SqlFloat");
        sqlMap.put(REAL,"table.type.number.SqlReal");
        sqlMap.put(CHAR,"table.type.text.SqlChar");
        sqlMap.put(VARCHAR,"table.type.text.SqlVarChar");
        sqlMap.put(BLOB,"table.type.text.SqlBlob");
        sqlMap.put(DATE,"table.type.date.SqlDate");
        sqlMap.put(TIME,"table.type.date.SqlTime");
        sqlMap.put(TIMESTAMP,"table.type.date.SqlTimeStamp");
        sqlMap.put(YEAR,"table.type.date.SqlYear");
        sqlMap.put(TABLE_DESCRIPTOR,"table.TableDescriptor");
        sqlMap.put(USER,"system.User");
        sqlMap.put(STRING,"java.lang.String");
        sqlMap.put(TABLE,"table.Table");
        sqlMap.put(DATABASE,"table.Database");
        sqlMap.put(PRIMARY_KEY,"table.type.PrimaryKey");
    }




}
