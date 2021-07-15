package team.JZY.DocManager.interfaces;

import team.JZY.DocManager.data.UserRepository;

public interface UserIdentification {
    public UserRepository.booleanListener login(String name, String password);
    public void register(String name,String password);
}
