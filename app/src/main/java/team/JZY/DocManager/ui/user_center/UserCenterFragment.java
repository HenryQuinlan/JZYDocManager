package team.JZY.DocManager.ui.user_center;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import team.JZY.DocManager.R;
import team.JZY.DocManager.ui.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCenterFragment extends Fragment {

    private UserViewModel userViewModel;

    public UserCenterFragment() {
        // Required empty public constructor
    }


    public static UserCenterFragment newInstance(String param1, String param2) {
        return new UserCenterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intentCollection =new Intent(UserCenterFragment,mCollection);
        Intent intent
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_center_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }
}