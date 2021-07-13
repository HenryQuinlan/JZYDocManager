package team.JZY.DocManager.ui.homepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
    private List<DocInfo> docsInfo;
    private static final int[] DOC_TYPE_IMAGE_SOURCE = {
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_pdf};
    private static final String TextDocInfoVisitsPrefix = "浏览量：";
    private static final String TextDocInfoSizePrefix = "大小：";
    private String savePathDir;
    private String tempPathDir;
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
    public DocInfoViewAdapter(DocManagerApplication.Activity activity,List<DocInfo> docsInfo) {
        this.activity = activity;
        this.docsInfo = docsInfo;
        this.savePathDir = savePathDir;
        this.tempPathDir = tempPathDir;
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
        DocInfo docInfo = docsInfo.get(position);
        holder.binding.docInfoNameText.setText(docInfo.getName());
        holder.binding.docInfoVisitsAndSizeText.setText(
                TextDocInfoVisitsPrefix+docInfo.getVisits()+"  "+
                TextDocInfoSizePrefix+ docInfo.getSize());
        holder.binding.docInfoTypeView.setImageResource(
                DOC_TYPE_IMAGE_SOURCE[docInfo.getType()]
        );
        //TODO USER
        holder.binding.docInfoDownloadButton.setOnClickListener(v -> {
            onDownloadClicked(docsInfo.get(holder.getAdapterPosition()));
        });
        holder.binding.getRoot().setOnClickListener(v -> {
            onVisitClicked(docsInfo.get(holder.getAdapterPosition()));
        });
        holder.binding.docInfoFavorieButton.setOnClickListener( v -> {

        });
    }

    @Override
    public int getItemCount() {
        return docsInfo.size();
    }

    private void onDownloadClicked(DocInfo docInfo) {
        FileOpenUtil.downloadAndView(activity,docInfo);
    }
    private void onVisitClicked(DocInfo docInfo) {
        FileOpenUtil.preview(activity,docInfo);
    }
}
