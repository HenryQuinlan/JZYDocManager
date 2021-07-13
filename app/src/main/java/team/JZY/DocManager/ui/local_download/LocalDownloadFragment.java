package team.JZY.DocManager.ui.local_download;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.model.User;
import team.JZY.DocManager.ui.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalDownloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalDownloadFragment extends DocManagerApplication.Fragment {
    //TODO UNKNOWN MAYBE WEBVIEW? OR LOCAL DOC MANAGER OR  JUST LOCAL DOWNLOAD
    private UserViewModel userViewModel;
    public LocalDownloadFragment() {
        // Required empty public constructor
    }

    public static LocalDownloadFragment newInstance() {
        return new LocalDownloadFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.local_download_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }
}