package table.type.text;

public class SqlVarChar implements Comparable<SqlVarChar>{
    private String string=null;
    private int scale=-1;

    public SqlVarChar(){

    }

    public SqlVarChar(int l, String str){
        scale=l;
        if(str.length()<=scale){
            string=str;
        }else{
            string=str.substring(0,scale);
        }
    }

    @Override
    public int compareTo(SqlVarChar o) {
        int re=this.string.compareTo(o.string);
        return re;
    }

    public int getLength(){
        return scale;
    }

    public String getString() {
        return string;
    }

    public void setLength(int length) {
        this.scale = length;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string.toString();
    }
}
