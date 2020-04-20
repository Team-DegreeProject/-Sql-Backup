package table.type;

public interface SqlType extends Comparable {
    public void setValue(String o);
    public String toString();
    public SqlType addOne() throws Exception;
}
