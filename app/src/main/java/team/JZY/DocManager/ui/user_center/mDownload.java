package team.JZY.DocManager.ui.user_center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.MainActivity;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.RecordRepository;
import team.JZY.DocManager.model.Record;

public class mDownload extends AppCompatActivity {
    private RecordRepository recordRepository=RecordRepository.getInstance(this);
    Intent intent=getIntent();
    String username=intent.getStringExtra("username");
    private List<Record> recordList=recordRepository.getDownloadRecord(username);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdownload);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.m_download_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecordAdapter recordAdapter=new RecordAdapter(recordList);
        recyclerView.setAdapter(recordAdapter);
        recordAdapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View view, int pos) {
                PopupMenu popupMenu=new PopupMenu(mDownload.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.download_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.open_file){

                        }
                        if(item.getItemId()==R.id.favourite) {
                            //recordRepository.insertRecord(username,Record.TYPE_FAVORITE,);

                        }
                        if(item.getItemId()==R.id.delete){
                            //recordRepository.deleteRecord(username,Record.TYPE_DOWNLOAD,);
                            recordAdapter.notifyItemRemoved(pos);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
//    private void initRecords(){
//        for(int i=0;i<recordList.size();)
//    }
}
