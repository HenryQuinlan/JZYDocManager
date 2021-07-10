package team.JZY.DocManager.model;

public class DocInfo {

    public String name;
    public int visits;
    public double size;
    public int type;
    public DocInfo(String a, int b, double c, int d){
        name=a;
        visits=b;
        size=c;
        type=d;
    }
}
