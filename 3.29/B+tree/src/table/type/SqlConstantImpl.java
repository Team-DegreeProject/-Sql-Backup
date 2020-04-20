package table.type;

import java.util.HashMap;
import java.util.Map;

public class SqlConstantImpl implements SqlConstant{

    protected Map<Integer,String> sqlMap = new HashMap();

    public SqlConstantImpl(){ constructMap(); }
    public final static int TABLE_DESCRIPTOR=500;


    private void constructMap(){
        sqlMap.put(INT,"java.lang.Integer");
        sqlMap.put(CHAR,"table.type.Char");
        sqlMap.put(VARCHAR,"table.type.VarChar");
        sqlMap.put(DATE,"java.util.Date");
        sqlMap.put(TABLE_DESCRIPTOR,"table.TableDescriptor");
        sqlMap.put(USER,"system.User");
        sqlMap.put(STRING,"java.lang.String");
        sqlMap.put(TABLE,"table.Table");
        sqlMap.put(DATABASE,"table.Database");
    }




}
