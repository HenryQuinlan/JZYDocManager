package team.JZY.DocManager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DocInfo {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="name")
    private String name;
//    @ColumnInfo(name="uri")
//    private String uri;
    @ColumnInfo(name="type")
    private int type;
    @ColumnInfo(name="classification")
    private int classification;
    @ColumnInfo(name="visits")
    private long visits;

    @ColumnInfo(name="size")
    private String size;

    public DocInfo(String name, int type, int classification, long visits, String size) {
        this.name = name;
        this.type = type;
        this.classification = classification;
        this.visits = visits;
        this.size = size;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public long getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
