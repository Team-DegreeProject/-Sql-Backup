package table.type.text;

import table.type.SqlType;

import java.sql.Blob;

public class SqlBlob implements SqlType {

    private Blob blob;

    public SqlBlob(Blob b){
        this.blob=b;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Blob getBlob() {
        return blob;
    }

    @Override
    public void setValue(String o) {

    }

    @Override
    public SqlType addOne() throws Exception {

        return this;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
