package team.JZY.DocManager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DocInfo {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name="name")
    private String name;
//    @ColumnInfo(name="uri")
//    private String uri;
    @ColumnInfo(name="classification")
    private int classification;
    @ColumnInfo(name="visits")
    private int visits;
    @ColumnInfo(name="type")
    private int type;
    @ColumnInfo(name="size")
    private int size;

    public DocInfo(String name, int classification, int visits, int type, int size) {
        this.name = name;
        this.classification = classification;
        this.visits = visits;
        this.type = type;
        this.size = size;
    }

    public long getId() {
        return id;
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

    public int getVisits() {
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
