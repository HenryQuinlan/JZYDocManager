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


    private final Context context;
    public ClassificationViewAdapter (Context context) {
        this.context = context;
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

        holder.binding.classificationImageButton.setBackgroundResource(CLASSIFICATION_IMAGE_RESOURCE[position]);
        holder.binding.classificationNameText.setText(CLASSIFICATION_TEXT_RESOURCE[position]);
        holder.binding.getRoot().setOnClickListener(v->onItemClicked(position));
        holder.binding.classificationNameText.setOnClickListener(v->onItemClicked(position));
        holder.binding.classificationImageButton.setOnClickListener(v->onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return CLASSIFICATION_NUMBER;
    }

    private void onItemClicked(int position) {
        ClassificationActivity.start(context,position);
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
    public static int CLASSIFICATION_NUMBER = 18;
    public static int[] CLASSIFICATION_TEXT_RESOURCE = {
            R.string.text_college,
            R.string.text_school,
            R.string.text_workplace,
            R.string.text_economy,
            R.string.text_IT,
            R.string.text_science,
            R.string.text_health,
            R.string.text_culture,
            R.string.text_agriculture,
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

    public static int CLASSIFICATION_IMAGE_RESOURCE[] = {
            R.drawable.image_college,
            R.drawable.image_school,
            R.drawable.image_workplace,
            R.drawable.image_economy,
            R.drawable.image_it,
            R.drawable.image_science,
            R.drawable.image_health,
            R.drawable.image_culture,
            R.drawable.image_agriculture,
            R.drawable.image_literature,
            R.drawable.image_leisure,
            R.drawable.image_life,
            R.drawable.image_amusement,
            R.drawable.image_fashion,
            R.drawable.image_sport,
            R.drawable.image_society,
            R.drawable.image_anime,
            R.drawable.image_other
    };
}
