package team.JZY.DocManager.ui.homepage;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.databinding.HomepageFragmentBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.ui.DocInfoViewAdapter;
import team.JZY.DocManager.ui.DocInfoViewModel;
import team.JZY.DocManager.ui.UserViewModel;

public class HomepageFragment extends DocManagerApplication.Fragment {

    private DocInfoViewModel homepageViewModel;
    private UserViewModel userViewModel;
    private HomepageFragmentBinding binding;
    private RecyclerView recyclerView;
    private DocInfoViewAdapter docInfoViewAdapter;
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
        homepageViewModel = new ViewModelProvider(this).get(DocInfoViewModel.class);
        docInfoViewAdapter = new team.JZY.DocManager.ui.homepage.DocInfoViewAdapter(getMActivity(),homepageViewModel.getLiveInfo());
        recyclerView.setAdapter(docInfoViewAdapter);
        homepageViewModel.getLiveInfo().observe(getViewLifecycleOwner(),(Observer<List<DocInfo>>)docsInfo->{
            docInfoViewAdapter.notifyDataSetChanged();
        });
        docInfoRepository  = DocInfoRepository.getInstance(requireActivity());
        getData();
        binding.homepageRefresh.setOnRefreshListener(()->getData());
    }

    public void getData(){
        docInfoRepository.setRequestListener(docsInfo -> {
            requireActivity().runOnUiThread(()->{
                homepageViewModel.setDocsInfo(docsInfo);
                binding.homepageRefresh.setRefreshing(false);
            });
        }).request(200);
    }

}