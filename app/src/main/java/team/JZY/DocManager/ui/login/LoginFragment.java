package team.JZY.DocManager.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.MainActivity;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.DocManagerDataBase;
import team.JZY.DocManager.data.UserDao;
import team.JZY.DocManager.data.UserRepository;
import team.JZY.DocManager.databinding.LoginFragmentBinding;
import team.JZY.DocManager.ui.UserViewModel;

public class LoginFragment extends DocManagerApplication.Fragment {
    private UserRepository userRepository;

    private LoginViewModel loginViewModel;
    private LoginFragmentBinding binding;
    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        binding.buttonLogin.setOnClickListener(v->onButtonLoginClicked());
        binding.buttonRegister.setOnClickListener(v ->onButtonRegisterClicked());
        userRepository = UserRepository.getInstance(requireContext());


        return binding.getRoot();
    }

    // private void onButtonRegisterClicked() {
//        userDao
//    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        loginViewModel.getLoggedInUserName().observe(getViewLifecycleOwner(), (Observer<String>)loggedInUserName->{
            if(loggedInUserName != null) {
                SharedPreferences sharedPref = requireContext().getSharedPreferences(getString(R.string.jzy_docManager_shared_preference_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(LoginViewModel.SAVE_LOGGED_IN_STATE_KEY, loggedInUserName);
                editor.apply();
                setLoggedInUserName(loggedInUserName);
                MainActivity.start(getContext());
                requireActivity().finish();
            }
        });
        SharedPreferences sharedPref = requireContext().getSharedPreferences(getString(R.string.jzy_docManager_shared_preference_key), Context.MODE_PRIVATE);
        String name = sharedPref.getString(LoginViewModel.SAVE_LOGGED_IN_STATE_KEY,null);
        loginViewModel.setLoggedInUserName(name);
    }


    public void onButtonLoginClicked() {
        String name = binding.textName.getText().toString();
        String password = binding.textPassword.getText().toString();
        userRepository.setStringListener(tips-> getMActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast toast = Toast.makeText(requireContext(),tips,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                if(tips.equals("登录成功")) {
                    loginViewModel.setLoggedInUserName(name);
                }
            }
        })).login(name,password);
    }

    private void onButtonRegisterClicked() {
        Intent intent=new Intent(requireActivity(),Register.class);
        startActivity(intent);
    }
}