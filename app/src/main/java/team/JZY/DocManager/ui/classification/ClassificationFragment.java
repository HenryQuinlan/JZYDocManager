package team.JZY.DocManager.ui.classification;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.ClassificationFragmentBinding;
import team.JZY.DocManager.ui.DocInfoViewAdapter;
import team.JZY.DocManager.ui.UserViewModel;
import team.JZY.DocManager.ui.homepage.HomepageFragment;

public class ClassificationFragment extends DocManagerApplication.Fragment {

    private ClassificationFragmentBinding binding;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    public static ClassificationFragment newInstance() {
        return new ClassificationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ClassificationFragmentBinding.inflate(inflater,container,false);
        recyclerView = binding.classificationRecyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        ClassificationViewAdapter adapter = new ClassificationViewAdapter(requireContext());
        recyclerView.setAdapter(adapter);
    }

}