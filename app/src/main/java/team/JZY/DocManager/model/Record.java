package team.JZY.DocManager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Record {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="Operator")
    private String Operator;
    @ColumnInfo(name="OperationType")
    private int OperationType;

    @ColumnInfo(name="DocID")
    private long DocID;
    @ColumnInfo(name="DocName")
    private String DocName;
    @ColumnInfo(name="DocType")
    private int DocType;

    @Ignore
    public static int TYPE_UPLOAD = 0;
    @Ignore
    public static int TYPE_DOWNLOAD = 1;
    @Ignore
    public static int TYPE_VISIT = 2;
    @Ignore
    public static int TYPE_FAVORITE = 3;

    public Record(String operator, int operationType, long docID, String docName, int docType) {
        this.Operator = operator;
        this.OperationType = operationType;
        this.DocID = docID;
        this.DocName = docName;
        this.DocType = docType;
    }
    public Record(String operator, int operationType, long docID) {
        this.Operator = operator;
        this.OperationType = operationType;
        this.DocID = docID;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public int getOperationType() {
        return OperationType;
    }

    public void setOperationType(int operationType) {
        OperationType = operationType;
    }

    public long getDocID() {
        return DocID;
    }

    public void setDocID(long docID) {
        DocID = docID;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public int getDocType() {
        return DocType;
    }

    public void setDocType(int docType) {
        DocType = docType;
    }
}
