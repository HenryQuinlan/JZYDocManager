package team.JZY.DocManager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Record {
    @Ignore
    private DocInfo docInfo;
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="Operator")
    private String userName;
    @ColumnInfo(name="OperationType")
    private int operationType;

    @ColumnInfo(name="DocID")
    private long DocID;
    @ColumnInfo(name="DocName")
    private String DocName;
    @ColumnInfo(name="DocType")
    private int DocType;

    public Record(String userName, int operationType, long docID, String docName, int docType) {
        this.userName = userName;
        this.operationType = operationType;
        DocID = docID;
        DocName = docName;
        DocType = docType;
    }

    public Record(String userName, DocInfo docInfo, int operationType){
        this.userName=userName;
        this.docInfo=docInfo;
        this.operationType=operationType;
        this.DocID=docInfo.getId();
        this.DocName=docInfo.getName();
        this.DocType=docInfo.getType();
    }

    public DocInfo getDocInfo() {
        return docInfo;
    }

    public void setDocInfo(DocInfo docInfo) {
        this.docInfo = docInfo;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getOperationType() {
        return operationType;
    }

    public long getDocID() {
        return DocID;
    }

    public String getDocName() {
        return DocName;
    }

    public int getDocType() {
        return DocType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
