package table.type;

public class TypeException extends Exception{
    String s;
    TypeException(String s){
        super("There is a problem with the attributes you entered ！");
        this.s=s;
    }

    public String toString(){
        return s+','+this.getMessage();
    }
}
