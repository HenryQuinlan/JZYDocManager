package team.JZY.DocManager.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.databinding.ActivitySearchKeyWordBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.ui.DocInfoViewAdapter;
import team.JZY.DocManager.ui.DocInfoViewModel;


public class SearchKeyWordActivity extends DocManagerApplication.Activity {

    private ActivitySearchKeyWordBinding binding;
    public static final String SEARCH_KeyWord_KEY = "SearchKeyWordKey";
    private DocInfoViewModel searchKeyWordViewModel;
    private RecyclerView recyclerView;
    private DocInfoViewAdapter docInfoViewAdapter;
    private DocInfoRepository docInfoRepository;
    private String searchKeyWord;
    public static void start(Context context, String searchKeyWord) {
        Intent intent = new Intent(context, SearchKeyWordActivity.class);
        intent.putExtra(SEARCH_KeyWord_KEY,searchKeyWord);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchKeyWord = getIntent().getStringExtra(SEARCH_KeyWord_KEY);

        binding = ActivitySearchKeyWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = binding.searchKeyWordRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchKeyWordViewModel = new ViewModelProvider(this).get(DocInfoViewModel.class);
        docInfoViewAdapter = new DocInfoViewAdapter(this,searchKeyWordViewModel.getLiveInfo());
        recyclerView.setAdapter(docInfoViewAdapter);
        searchKeyWordViewModel.getLiveInfo().observe(this,(Observer<List<DocInfo>>) docsInfo->{
            docInfoViewAdapter.notifyDataSetChanged();
        });
        docInfoRepository  = DocInfoRepository.getInstance(this);
        getData();
        binding.searchKeyWordRefresh.setOnRefreshListener(()->getData());
    }

    public void getData() {
        docInfoRepository.setRequestListener(docsInfo -> {
            runOnUiThread(()->{
                searchKeyWordViewModel.setDocsInfo(docsInfo);
                binding.searchKeyWordRefresh.setRefreshing(false);
            });
        }).request(searchKeyWord);
    }
}