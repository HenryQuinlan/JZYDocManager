package team.JZY.DocManager.interfaces;

import team.JZY.DocManager.data.UserRepository;

public interface UserIdentification {
    public void login(String name, String password);
    public void register(String name,String password,String c_password);
}
