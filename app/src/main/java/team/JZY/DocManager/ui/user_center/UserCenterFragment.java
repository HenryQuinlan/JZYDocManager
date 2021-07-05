package team.JZY.DocManager.ui.user_center;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.JZY.DocManager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCenterFragment extends Fragment {



    public UserCenterFragment() {
        // Required empty public constructor
    }


    public static UserCenterFragment newInstance(String param1, String param2) {
        return new UserCenterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_center_fragment, container, false);
    }
}