package team.JZY.DocManager.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.DocInfoItemBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.util.FileOpenUtil;

public class DocInfoViewAdapter extends RecyclerView.Adapter<DocInfoViewAdapter.ViewHolder> {

    private DocManagerApplication.Activity activity;
    private LiveData<List<DocInfo>> liveDocsInfo;
    private static final int[] DOC_TYPE_IMAGE_SOURCE = {
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_pdf};
    private static final String TextDocInfoVisitsPrefix = "浏览量：";
    private static final String TextDocInfoSizePrefix = "大小：";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private DocInfoItemBinding binding;
//        private ImageView docTypeView;
//        private ImageButton downloadButton;
//        private ImageButton favoriteButton;
//        private TextView nameText;
//        private TextView attrText;
        public ViewHolder(@NonNull @NotNull DocInfoItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
//            docTypeView = (ImageView)itemView.findViewById(R.id.doc_info_type_view);
//            downloadButton = (ImageButton)itemView.findViewById(R.id.doc_info_download_button);
//            favoriteButton = (ImageButton)itemView.findViewById(R.id.doc_info_favorite_button);
//            nameText = (TextView)itemView.findViewById(R.id.doc_info_name_text);
        }
    }
    public DocInfoViewAdapter(DocManagerApplication.Activity activity,LiveData<List<DocInfo>> liveDocsInfo) {
        this.activity = activity;
        this.liveDocsInfo = liveDocsInfo;
    }

    @NonNull
    @NotNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DocInfoItemBinding itemBinding = DocInfoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DocInfo docInfo = liveDocsInfo.getValue().get(position);
        holder.binding.docInfoNameText.setText(docInfo.getName());
        holder.binding.docInfoVisitsAndSizeText.setText(
                TextDocInfoVisitsPrefix+docInfo.getVisits()+"  "+
                TextDocInfoSizePrefix+ docInfo.getSize());
        holder.binding.docInfoTypeView.setImageResource(
                DOC_TYPE_IMAGE_SOURCE[docInfo.getType()]
        );
        //TODO USER
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
        holder.binding.getRoot().setOnClickListener(v -> {
            onVisitClicked(liveDocsInfo.getValue().get(holder.getAdapterPosition()));
        });
        holder.binding.docInfoFavorieButton.setOnClickListener( v -> {

        });
    }

    @Override
    public int getItemCount() {
        return liveDocsInfo.getValue().size();
    }

    private void onDownloadClicked(DocInfo docInfo) {
        FileOpenUtil.downloadAndView(activity,docInfo);
    }
    private void onVisitClicked(DocInfo docInfo) {
        FileOpenUtil.preview(activity,docInfo);
    }
}
