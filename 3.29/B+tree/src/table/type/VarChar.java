package table.type;

public class VarChar implements Comparable<VarChar>{
    private String string=null;
    private int length=-1;

    public VarChar(){

    }

    public VarChar(int l,String str){
        length=l;
        if(str.length()<=length){
            string=str;
        }else{
            string=str.substring(0,length);
        }
    }

    public VarChar(String str){
        length=-2;
        string=str;
    }

    @Override
    public int compareTo(VarChar o) {
        int re=this.string.compareTo(o.string);
        return re;
    }

    public int getLength(){
        return length;
    }

    public String getString() {
        return string;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setString(String string) {
        this.string = string;
    }


}
