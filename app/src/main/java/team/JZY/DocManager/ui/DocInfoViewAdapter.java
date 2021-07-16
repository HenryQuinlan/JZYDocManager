package team.JZY.DocManager.ui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.data.RecordRepository;
import team.JZY.DocManager.databinding.DocInfoItemBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.Record;
import team.JZY.DocManager.util.FileOpenUtil;

public class DocInfoViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DocManagerApplication.Activity activity;
    private RecordRepository recordRepository;
    private DocInfoRepository docInfoRepository;
    private LiveData<List<DocInfo>> liveDocsInfo;
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_NORMAL = 1;
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
    private static final String TextDocInfoVisitsPrefix = "浏览量：";
    private static final String TextDocInfoSizePrefix = "大小：";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private DocInfoItemBinding binding;
        boolean isFavorite;

        public ViewHolder(@NonNull @NotNull DocInfoItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
    public DocInfoViewAdapter(DocManagerApplication.Activity activity,LiveData<List<DocInfo>> liveDocsInfo) {
        this.activity = activity;
        this.liveDocsInfo = liveDocsInfo;
        this.recordRepository = RecordRepository.getInstance(activity);
        this.docInfoRepository = DocInfoRepository.getInstance(activity);
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_EMPTY) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(activity).inflate(R.layout.empty_layout,parent,false)){};
        }
        DocInfoItemBinding itemBinding = DocInfoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder recyclerViewHolder, int position) {
        if(!(recyclerViewHolder instanceof ViewHolder))return;
        ViewHolder holder = (ViewHolder)recyclerViewHolder;
        DocInfo docInfo = liveDocsInfo.getValue().get(position);
        holder.binding.docInfoNameText.setText(docInfo.getName());
        holder.binding.docInfoVisitsAndSizeText.setText(
                TextDocInfoVisitsPrefix+docInfo.getVisits()+"  "+
                TextDocInfoSizePrefix+ docInfo.getSize());
        holder.binding.docInfoTypeView.setImageResource(
                DOC_TYPE_IMAGE_SOURCE[docInfo.getType()]
        );

        if(FileOpenUtil.isFileDownloaded(activity,docInfo)) {
            holder.binding.docInfoDownloadButton.setImageResource(R.drawable.ic_toolbar_download_pressed_disable);
        }
        else {
            holder.binding.docInfoDownloadButton.setImageResource(R.drawable.ic_toolbar_download);
            holder.binding.docInfoDownloadButton.setOnClickListener(v -> {
                onDownloadClicked(liveDocsInfo.getValue().get(holder.getAdapterPosition()));
                holder.binding.docInfoDownloadButton.setImageResource(R.drawable.ic_toolbar_download_pressed_disable);
            });
        }

        recordRepository.setonRecordReceivedListener(records -> {
            holder.isFavorite = records != null && !records.isEmpty();
            holder.binding.docInfoFavorieButton.setImageResource(DOC_FAVORITE_IMAGE_SOURCE[holder.isFavorite?1:0]);
        }).checkRecord(activity.getLoggedInUserName(),Record.TYPE_FAVORITE,docInfo.getId());

        holder.binding.getRoot().setOnClickListener(v -> {
            onVisitClicked(liveDocsInfo.getValue().get(holder.getAdapterPosition()));
        });

        holder.binding.docInfoFavorieButton.setOnClickListener( v -> {
            onFavoriteClicked(liveDocsInfo.getValue().get(holder.getAdapterPosition()),holder.isFavorite);
            holder.isFavorite = !(holder.isFavorite);
            holder.binding.docInfoFavorieButton.setImageResource(DOC_FAVORITE_IMAGE_SOURCE[holder.isFavorite?1:0]);
        });
    }



    @Override
    public int getItemCount() {
        int size = (Objects.requireNonNull(liveDocsInfo.getValue()).size());
        return size <= 0 ? 1 : size;
    }

    @Override
    public int getItemViewType(int position) {
        if(Objects.requireNonNull(liveDocsInfo.getValue()).size()<=0){
            return TYPE_EMPTY;
        }
        return  TYPE_NORMAL;
    }

    private void onDownloadClicked(DocInfo docInfo) {
        FileOpenUtil.downloadAndView(activity,docInfo);
        recordRepository.insertRecord(
                activity.getLoggedInUserName(),
                Record.TYPE_DOWNLOAD,
                docInfo.getId(),
                docInfo.getName(),
                docInfo.getType());
    }
    private void onVisitClicked(DocInfo docInfo) {
        FileOpenUtil.preview(activity,docInfo);
        docInfoRepository.update(docInfo.getId(),docInfo.getVisits()+1);
        recordRepository.insertRecord(
                activity.getLoggedInUserName(),
                Record.TYPE_VISIT,
                docInfo.getId(),
                docInfo.getName(),
                docInfo.getType());
    }

    private void onFavoriteClicked(DocInfo docInfo, boolean isFavorite) {
        if(isFavorite) {
            recordRepository.deleteRecord(new Record(
                    activity.getLoggedInUserName(),
                    Record.TYPE_FAVORITE,
                    docInfo.getId(),
                    docInfo.getName(),
                    docInfo.getType()
            ));
        }
        else {
                recordRepository.insertRecord(
                        activity.getLoggedInUserName(),
                        Record.TYPE_FAVORITE,
                        docInfo.getId(),
                        docInfo.getName(),
                        docInfo.getType());
        }
    }
}
