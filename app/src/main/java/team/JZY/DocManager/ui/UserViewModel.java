package team.JZY.DocManager.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;

import team.JZY.DocManager.model.User;

public class UserViewModel extends ViewModel {

    private MutableLiveData<User> liveUser;
    public LiveData<User> getLiveUser() {
       if(liveUser == null) {
           liveUser = new MutableLiveData<User>(null);
       }
       return liveUser;
    }
    public void setUser(User user) {
        if(liveUser == null) {
            liveUser = new MutableLiveData<User>(user);
        }
        else {
            liveUser.setValue(user);
        }
    }
    public User getUser(){
        return liveUser.getValue();
    }

}