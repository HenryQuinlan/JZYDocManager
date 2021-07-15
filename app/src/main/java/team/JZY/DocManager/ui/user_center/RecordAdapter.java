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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.RecordItemBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.Record;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<Record> recordList;
    private static final int[] DOC_TYPE_IMAGE_SOURCE = {
            //文件图片
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_doc,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_ppt,
            R.drawable.ic_doctype_pdf};

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        private RecordItemBinding binding;
        private ImageView docTypeView;
        private ImageButton downloadButton;
        private TextView nameText;
        private TextView attrText;
        public ViewHolder(@NonNull @NotNull RecordItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
//            docTypeView = (ImageView)itemView.findViewById(R.id.doc_info_type_view);
//            downloadButton = (ImageButton)itemView.findViewById(R.id.doc_info_download_button);
//            favoriteButton = (ImageButton)itemView.findViewById(R.id.doc_info_favorite_button);
//            nameText = (TextView)itemView.findViewById(R.id.doc_info_name_text);
        }
        public ViewHolder(View view){
            super(view);
            this.view=view;
        }
    }
    public RecordAdapter(List<Record> recordList) {
        this.recordList = recordList;
    }

    @NonNull
    @NotNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecordItemBinding itemBinding = RecordItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return  new ViewHolder(itemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Record record = recordList.get(position);
        holder.binding.docInfoNameText.setText(record.getDocName());
        holder.binding.docInfoTypeView.setImageResource(
                DOC_TYPE_IMAGE_SOURCE[record.getDocType()]
        );
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemLongClick(View view , int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}