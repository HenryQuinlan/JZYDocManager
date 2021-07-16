package team.JZY.DocManager.ui.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.databinding.HomepageFragmentBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.ui.DocInfoViewAdapter;
import team.JZY.DocManager.ui.DocInfoViewModel;
import team.JZY.DocManager.ui.UserViewModel;
import team.JZY.DocManager.ui.classification.ClassificationActivity;
import team.JZY.DocManager.ui.search.SearchableActivity;

public class HomepageFragment extends DocManagerApplication.Fragment {

    private DocInfoViewModel homepageViewModel;
    private UserViewModel userViewModel;
    private HomepageFragmentBinding binding;
    private RecyclerView recyclerView;
    private DocInfoViewAdapter docInfoViewAdapter;
    private DocInfoRepository docInfoRepository;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        binding = HomepageFragmentBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.homepageRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.homepageToolbar);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        homepageViewModel = new ViewModelProvider(requireActivity()).get(DocInfoViewModel.class);

        docInfoViewAdapter = new DocInfoViewAdapter(getMActivity(),homepageViewModel.getLiveInfo());
        recyclerView.setAdapter(docInfoViewAdapter);
        homepageViewModel.getLiveInfo().observe(getViewLifecycleOwner(),(Observer<List<DocInfo>>)docsInfo->{
            docInfoViewAdapter.notifyDataSetChanged();
        });
        docInfoRepository  = DocInfoRepository.getInstance(requireActivity());
        if(homepageViewModel.getLiveInfo().getValue().isEmpty())getData();
        binding.homepageRefresh.setOnRefreshListener(this::getData);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void getData(){
        docInfoRepository.setRequestListener(docsInfo -> {
            requireActivity().runOnUiThread(()->{
                homepageViewModel.setDocsInfo(docsInfo);
                binding.homepageRefresh.setRefreshing(false);
            });
        }).request(200);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.tool_bar_search_view).getActionView();

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("搜索...");
        searchView.setOnClickListener(v->{
            SearchableActivity.start(requireActivity());
        });

        searchView.setOnQueryTextFocusChangeListener((v,hasFocus)->{
                if(hasFocus) SearchableActivity.start(requireActivity());
                searchView.clearFocus();
        });
    }

}