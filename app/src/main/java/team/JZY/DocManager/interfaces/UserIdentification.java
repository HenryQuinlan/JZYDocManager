package team.JZY.DocManager.interfaces;

public interface UserIdentification {
    public boolean login(String name,String password);
    public void register(String name,String password);
}
