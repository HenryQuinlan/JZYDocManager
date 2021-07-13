package team.JZY.DocManager.ui.classification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.ClassificationItemBinding;

public class ClassificationViewAdapter extends RecyclerView.Adapter<ClassificationViewAdapter.ViewHolder> {

    private List<ClassificationHolder> classificationHolders;
    private Context context;
    public ClassificationViewAdapter (Context context, List<ClassificationHolder> classificationHolders) {
        this.context = context;
        this.classificationHolders = classificationHolders;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ClassificationItemBinding itemBinding = ClassificationItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ClassificationHolder classificationHolder = classificationHolders.get(position);
        holder.binding.classificationImageButton.setBackgroundResource(R.drawable.ic_doctype_doc);
        holder.binding.classificationNameText.setText(CLASSIFICATION_TEXT_RESOURCE[position]);
       // holder.binding.getRoot().setOnClickListener(v->);
    }

    @Override
    public int getItemCount() {
        return classificationHolders.size();
    }

    private void onItemClicked(int positon) {
       // Intent intent = new Intent(context,)
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ClassificationItemBinding binding;
        public ViewHolder(ClassificationItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
    public static class ClassificationHolder {
        String classificationName;
        int classificationImageResource;

        public ClassificationHolder(String classificationName, int classificationImageResource) {
            this.classificationName = classificationName;
            this.classificationImageResource = classificationImageResource;
        }
    }

    public static int[] CLASSIFICATION_TEXT_RESOURCE = {
            R.string.text_college,
            R.string.text_school,
            R.string.text_workplace,
            R.string.text_economy,
            R.string.text_IT,
            R.string.text_science,
            R.string.text_health,
            R.string.text_culture,
            R.string.text_algriculture,
            R.string.text_literature,
            R.string.text_leisure,
            R.string.text_life,
            R.string.text_amusement,
            R.string.text_fashion,
            R.string.text_sport,
            R.string.text_society,
            R.string.text_anime,
            R.string.text_other
    };

    //TODO public static int CLASSIFICATION_IMAGE_RESOURCE
}
