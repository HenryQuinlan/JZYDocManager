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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import team.JZY.DocManager.MainActivity;
import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.LoginFragmentBinding;
import team.JZY.DocManager.ui.UserViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private LoginFragmentBinding binding;
    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        binding.buttonLogin.setOnClickListener(v->onButtonLoginClicked());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);


        loginViewModel.getLoggedInUserName().observe(getViewLifecycleOwner(), (Observer<String>)loggedInUserName->{
            if(loggedInUserName != null) {
                SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.jzy_docManager_shared_preference_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(LoginViewModel.SAVE_LOGGED_IN_STATE_KEY, loggedInUserName);
                editor.apply();
                MainActivity.start(getContext(),loggedInUserName);
                getActivity().finish();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.jzy_docManager_shared_preference_key), Context.MODE_PRIVATE);
        String name = sharedPref.getString(LoginViewModel.SAVE_LOGGED_IN_STATE_KEY,null);
        loginViewModel.setLoggedInUserName(name);
    }

    public void onButtonLoginClicked() {
        String name = binding.textName.getText().toString();
        String password = binding.textPassword.getText().toString();
        loginViewModel.login(name,password);
    }
}