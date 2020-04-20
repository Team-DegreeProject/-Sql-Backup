package table.type.text;

import java.sql.Blob;

public class SqlBlob {
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
}
