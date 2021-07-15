package team.JZY.DocManager.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.model.DocInfo;

public class SearchableActivity extends DocManagerApplication.Activity {
    private SearchView searchView;
    private DocInfoRepository docInfoRepository;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        docInfoRepository = DocInfoRepository.getInstance(this);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        ListView listView = (ListView) findViewById(R.id.lv);

        listView.setAdapter(adapter);

        searchView=(SearchView)findViewById(R.id.sv);

        searchView = (SearchView) findViewById(R.id.sv);
        //设置SearchView自动缩小为图标
        searchView.setIconifiedByDefault(false);//设为true则搜索栏 缩小成俄日一个图标点击展开
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //设置默认提示文字
        searchView.setQueryHint("搜索...");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setQuery(adapter.getItem(position),true);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                //此处添加查询开始后的具体时间和方法
                SearchKeyWordActivity.start(SearchableActivity.this,query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null ||newText.equals("")) {
                    clearData();
                    return true;
                }
                docInfoRepository.setRecommendListener(docsName -> {
                    runOnUiThread(() -> {
                        setData(docsName);
                    });
                }).searchRecommend(newText);
                return true;
            }
        });
    }
    public void setData(List<String> docsName){
        adapter.clear();
        adapter.addAll(docsName);
        adapter.notifyDataSetChanged();
    }
    private void clearData() {
        adapter.clear();
        adapter.notifyDataSetChanged();
    }
    public static void start(Context context) {
        Intent intent = new Intent(context,SearchableActivity.class);
        context.startActivity(intent);
    }
}