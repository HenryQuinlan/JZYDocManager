package team.JZY.DocManager.ui.local_download;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.JZY.DocManager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalDownloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalDownloadFragment extends Fragment {


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
}