package team.JZY.DocManager.ui.homepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.DocInfoItemBinding;
import team.JZY.DocManager.model.DocInfo;

import static java.security.AccessController.getContext;

public class DocInfoViewAdapter extends RecyclerView.Adapter<DocInfoViewAdapter.ViewHolder> {

    private List<DocInfo> docsInfo;
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
        private ImageView docTypeView;
        private ImageButton downloadButton;
        private ImageButton favoriteButton;
        private TextView nameText;
        private TextView attrText;
        public ViewHolder(@NonNull @NotNull DocInfoItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
//            docTypeView = (ImageView)itemView.findViewById(R.id.doc_info_type_view);
//            downloadButton = (ImageButton)itemView.findViewById(R.id.doc_info_download_button);
//            favoriteButton = (ImageButton)itemView.findViewById(R.id.doc_info_favorite_button);
//            nameText = (TextView)itemView.findViewById(R.id.doc_info_name_text);
        }
    }
    public DocInfoViewAdapter(List<DocInfo> docsInfo) {
        this.docsInfo = docsInfo;
    }

    @NonNull
    @NotNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        DocInfoItemBinding itemBinding = DocInfoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return  new ViewHolder(itemBinding);
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
        holder.binding.docInfoDownloadButton.setOnClickListener(v -> {
            Snackbar.make(v,""+docInfo.getSize(),Snackbar.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return docsInfo.size();
    }


}
