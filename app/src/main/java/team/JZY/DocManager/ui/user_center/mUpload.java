package team.JZY.DocManager.ui.user_center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.MainActivity;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.RecordRepository;
import team.JZY.DocManager.databinding.ActivityMcollectionBinding;
import team.JZY.DocManager.databinding.ActivityMuploadBinding;
import team.JZY.DocManager.model.Record;
import team.JZY.DocManager.util.ConvertUtil;
import team.JZY.DocManager.util.FileOpenUtil;
import team.JZY.DocManager.util.RecordOperateUtil;

import static team.JZY.DocManager.ui.user_center.RecordAdapter.DOC_FAVORITE_IMAGE_SOURCE;

public class mUpload extends DocManagerApplication.Activity {
    private RecordRepository recordRepository;
    private String username;
    private RecordViewModel recordViewModel;
    private ActivityMuploadBinding binding;
    private RecordAdapter recordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMuploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = getLoggedInUserName();
        recordRepository = RecordRepository.getInstance(this);
        recordViewModel = new ViewModelProvider(this).get(RecordViewModel.class);

        RecyclerView recyclerView =binding.mUploadView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecordAdapter recordAdapter = new RecordAdapter(this,recordViewModel.getLiveRecord());
        recordAdapter.setLongClickListener(this::setRecordLongClickPopupMenu);
        recyclerView.setAdapter(recordAdapter);

        recordViewModel.getLiveRecord().observe(this,(Observer<List<Record>>) records->{
            recordAdapter.notifyDataSetChanged();
        });
        getData();
        binding.refresh.setOnRefreshListener(this::getData);

    }
    public void getData() {
        recordRepository.setonRecordReceivedListener(records -> {
            runOnUiThread(()->{
                recordViewModel.setRecords(records);
                binding.refresh.setRefreshing(false);
            });
        }).getUploadRecord(username);
    }

    private void setRecordLongClickPopupMenu(RecordAdapter.ViewHolder holder, int position , Record record) {
        PopupMenu popupMenu=new PopupMenu(mUpload.this,holder.itemView, Gravity.END);
        popupMenu.getMenuInflater().inflate(R.menu.update_menu,popupMenu.getMenu());
        popupMenu.getMenu().findItem(R.id.favourite).setTitle(holder.isFavorite?"取消收藏":"添加收藏");
        popupMenu.setOnMenuItemClickListener(item -> {
            if(item.getItemId()==R.id.favourite) {
                RecordOperateUtil.favorite(mUpload.this,record,holder.isFavorite);
                holder.isFavorite = !(holder.isFavorite);
                holder.getBinding().docInfoFavorieButton.setImageResource(DOC_FAVORITE_IMAGE_SOURCE[holder.isFavorite?1:0]);
            }
            else if(item.getItemId()==R.id.delete){
                recordRepository.setOnOperationOverListener(this::getData).deleteRecord(record);
            }
            return false;
        });
        popupMenu.show();
    }
}
