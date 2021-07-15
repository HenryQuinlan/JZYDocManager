package team.JZY.DocManager.ui.classification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.databinding.ActivityClassificationBinding;
import team.JZY.DocManager.databinding.HomepageFragmentBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.ui.DocInfoViewModel;
import team.JZY.DocManager.ui.UserViewModel;
import team.JZY.DocManager.ui.DocInfoViewAdapter;

public class ClassificationActivity extends DocManagerApplication.Activity {

    private ActivityClassificationBinding binding;
    public static final String CLASSIFICATION_KEY = "ClassificationKey";
    private DocInfoViewModel classificationViewModel;
    private RecyclerView recyclerView;
    private DocInfoViewAdapter docInfoViewAdapter;
    private DocInfoRepository docInfoRepository;
    private int classification;
    public static void start(Context context,int classification) {
        Intent intent = new Intent(context, ClassificationActivity.class);
        intent.putExtra(CLASSIFICATION_KEY,classification);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        classification = getIntent().getIntExtra(CLASSIFICATION_KEY,classification);

        binding = ActivityClassificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = binding.classificationRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classificationViewModel = new ViewModelProvider(this).get(DocInfoViewModel.class);
        docInfoViewAdapter = new DocInfoViewAdapter(this,classificationViewModel.getLiveInfo());
        recyclerView.setAdapter(docInfoViewAdapter);
        classificationViewModel.getLiveInfo().observe(this,(Observer<List<DocInfo>>) docsInfo->{
            docInfoViewAdapter.notifyDataSetChanged();
        });
        docInfoRepository  = DocInfoRepository.getInstance(this);
        getData();
        binding.classificationRefresh.setOnRefreshListener(this::getData);
    }

    public void getData() {
        docInfoRepository.setRequestListener(docsInfo -> {
            runOnUiThread(()->{
                classificationViewModel.setDocsInfo(docsInfo);
                binding.classificationRefresh.setRefreshing(false);
            });
        }).request(200,classification);
    }

}