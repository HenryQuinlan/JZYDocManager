package team.JZY.DocManager.ui.user_center;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.data.RecordRepository;
import team.JZY.DocManager.databinding.DocInfoItemBinding;
import team.JZY.DocManager.databinding.RecordItemBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.Record;
import team.JZY.DocManager.ui.DocInfoViewAdapter;
import team.JZY.DocManager.util.ConvertUtil;
import team.JZY.DocManager.util.FileOpenUtil;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private DocManagerApplication.Activity activity;
    private RecordRepository recordRepository;
    private DocInfoRepository docInfoRepository;
    private LiveData<List<Record>> liveRecords;
    private static final int[] DOC_TYPE_IMAGE_SOURCE = {
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_pdf};
    private static final int[] DOC_FAVORITE_IMAGE_SOURCE = {
            R.drawable.ic_toolbar_favorite_normal,
            R.drawable.ic_toolbar_favorite_on
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecordItemBinding binding;
        boolean isFavorite;

        public ViewHolder(@NonNull @NotNull RecordItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
    public RecordAdapter(DocManagerApplication.Activity activity,LiveData<List<Record>> liveRecords) {
        this.activity = activity;
        this.liveRecords = liveRecords;
        this.recordRepository = RecordRepository.getInstance(activity);
        this.docInfoRepository = DocInfoRepository.getInstance(activity);
    }

    @NonNull
    @NotNull
    @Override

    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecordItemBinding itemBinding = RecordItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecordAdapter.ViewHolder(itemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecordAdapter.ViewHolder holder, int position) {
        Record record = liveRecords.getValue().get(position);
        holder.binding.docInfoNameText.setText(record.getDocName());
        holder.binding.docInfoTypeView.setImageResource(
                DOC_TYPE_IMAGE_SOURCE[record.getDocType()]
        );
        if(FileOpenUtil.isFileDownloaded(activity, ConvertUtil.RecordConvertToDocInfo(record))) {
            holder.binding.docInfoDownloadButton.setImageResource(R.drawable.ic_toolbar_download_pressed_disable);
        }
        else {
            holder.binding.docInfoDownloadButton.setImageResource(R.drawable.ic_toolbar_download);
            holder.binding.docInfoDownloadButton.setOnClickListener(v -> {
                onDownloadClicked(record);
                holder.binding.docInfoDownloadButton.setImageResource(R.drawable.ic_toolbar_download_pressed_disable);
            });
        }

        recordRepository.setonRecordReceivedListener(records -> {
            holder.isFavorite = records != null && !records.isEmpty();
            holder.binding.docInfoFavorieButton.setImageResource(DOC_FAVORITE_IMAGE_SOURCE[holder.isFavorite?1:0]);
        }).checkRecord(activity.getLoggedInUserName(),Record.TYPE_FAVORITE,record.getDocID());
        holder.binding.getRoot().setOnClickListener(v -> {
            onVisitClicked(record);
        });
        holder.binding.docInfoFavorieButton.setOnClickListener( v -> {
            onFavoriteClicked(record,holder.isFavorite);
            holder.isFavorite = !(holder.isFavorite);
            holder.binding.docInfoFavorieButton.setImageResource(DOC_FAVORITE_IMAGE_SOURCE[holder.isFavorite?1:0]);
        });
    }



    @Override
    public int getItemCount() {
        return Objects.requireNonNull(liveRecords.getValue()).size();
    }

    private void onDownloadClicked(Record record) {
        FileOpenUtil.downloadAndView(activity,ConvertUtil.RecordConvertToDocInfo(record));
        recordRepository.insertRecord(
                activity.getLoggedInUserName(),
                Record.TYPE_DOWNLOAD,
                record.getDocID(),
                record.getDocName(),
                record.getDocType());
    }
    private void onVisitClicked(Record record) {
        FileOpenUtil.preview(activity,ConvertUtil.RecordConvertToDocInfo(record));
        recordRepository.insertRecord(
                activity.getLoggedInUserName(),
                Record.TYPE_VISIT,
                record.getDocID(),
                record.getDocName(),
                record.getDocType());
    }

    private void onFavoriteClicked(Record record, boolean isFavorite) {
        if(isFavorite) {
            recordRepository.deleteRecord(new Record(
                    activity.getLoggedInUserName(),
                    Record.TYPE_FAVORITE,
                    record.getDocID(),
                    record.getDocName(),
                    record.getDocType()
            ));
        }
        else {
            recordRepository.insertRecord(
                    activity.getLoggedInUserName(),
                    Record.TYPE_FAVORITE,
                    record.getDocID(),
                    record.getDocName(),
                    record.getDocType());
        }
    }
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemLongClick(View view , int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}