package team.JZY.DocManager.ui.homepage;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.databinding.HomepageFragmentBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.User;
import team.JZY.DocManager.ui.UserViewModel;

public class HomepageFragment extends DocManagerApplication.Fragment {

    private HomepageViewModel homepageViewModel;
    private UserViewModel userViewModel;
    private HomepageFragmentBinding binding;
    private RecyclerView recyclerView;
    private DocInfoRepository docInfoRepository;
    public static HomepageFragment newInstance() {
        return new HomepageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomepageFragmentBinding.inflate(inflater, container, false);
        recyclerView = binding.homepageRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        homepageViewModel = new ViewModelProvider(this).get(HomepageViewModel.class);
        homepageViewModel.getLiveInfo().observe(getViewLifecycleOwner(),(Observer<List<DocInfo>>)docsInfo->{
            if(docsInfo == null)return;
            DocInfoViewAdapter adapter = new DocInfoViewAdapter(HomepageFragment.this.getMActivity(),docsInfo);
            recyclerView.setAdapter(adapter);
        });
        docInfoRepository  = DocInfoRepository.getInstance(requireActivity());
        getData();
    }

    public void getData(){
        docInfoRepository.setRequestListener(docsInfo -> {
            requireActivity().runOnUiThread(()->{
                homepageViewModel.setDocsInfo(docsInfo);
            });
        }).request(200);
    }
}