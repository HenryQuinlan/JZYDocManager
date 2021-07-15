package team.JZY.DocManager.ui.user_center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.MainActivity;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.RecordRepository;
import team.JZY.DocManager.databinding.ActivityMcollectionBinding;
import team.JZY.DocManager.model.Record;

public class mCollection extends DocManagerApplication.Activity {
    private RecordRepository recordRepository;
    private String username;
    private RecordViewModel recordViewModel;
    private ActivityMcollectionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = getLoggedInUserName();
        recordRepository = RecordRepository.getInstance(this);
        recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);

        RecyclerView recyclerView =binding.mCollectionView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecordAdapter recordAdapter = new RecordAdapter(this,recordViewModel.getLiveRecord());
        recyclerView.setAdapter(recordAdapter);

        recordViewModel.getLiveRecord().observe(this,(Observer<List<Record>>)records->{
            recordAdapter.notifyDataSetChanged();
        });
        getData();
        binding.refresh.setOnRefreshListener(this::getData);

        recordAdapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int pos) {
                PopupMenu popupMenu=new PopupMenu(mCollection.this,view);
                Record record =recordViewModel.getLiveRecord().getValue().get(pos);
                popupMenu.getMenuInflater().inflate(R.menu.collection_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.open_file){

                        }
                        if(item.getItemId()==R.id.cancel) {
                            recordRepository.deleteRecord(record);
                            recordAdapter.notifyItemRemoved(pos);
                        }
                        if(item.getItemId()==R.id.download){
                           recordRepository.insertRecord(
                                   username,
                                   Record.TYPE_DOWNLOAD,
                                   record.getDocID(),
                                   record.getDocName(),
                                   record.getDocType());
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    public void getData() {
        recordRepository.setonRecordReceivedListener(records -> {
            runOnUiThread(()->{
                recordViewModel.setRecords(records);
                binding.refresh.setRefreshing(false);
            });
        }).getFavoriteRecord(username);
    }

}