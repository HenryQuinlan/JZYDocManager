package team.JZY.DocManager.ui.classification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.ClassificationFragmentBinding;
import team.JZY.DocManager.ui.UserViewModel;
import team.JZY.DocManager.ui.homepage.HomepageFragment;
import team.JZY.DocManager.ui.search.SearchableActivity;

public class ClassificationFragment extends DocManagerApplication.Fragment {

    private ClassificationFragmentBinding binding;
    private static ClassificationFragmentBinding bindingRecord;
    private UserViewModel userViewModel;

    public static ClassificationFragment newInstance() {
        return new ClassificationFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(bindingRecord == null) {
            binding = ClassificationFragmentBinding.inflate(inflater, container, false);
            RecyclerView recyclerView = binding.classificationRecyclerView;
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            ((AppCompatActivity) getMActivity()).setSupportActionBar(binding.homepageToolbar);
            userViewModel = new ViewModelProvider(getMActivity()).get(UserViewModel.class);
            ClassificationViewAdapter adapter = new ClassificationViewAdapter(requireContext());
            recyclerView.setAdapter(adapter);
            bindingRecord = binding;
        }
        else {
            binding = bindingRecord;
        }
        ViewGroup parent = (ViewGroup)binding.getRoot().getParent();
        if(parent != null) {
            parent.removeView(binding.getRoot());
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.tool_bar_search_view).getActionView();

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("搜索...");
        searchView.setOnClickListener(v->{
            SearchableActivity.start(getMActivity());
        });
        searchView.setOnQueryTextFocusChangeListener((v,hasFocus)->{
            if(hasFocus) SearchableActivity.start(getMActivity());
            searchView.clearFocus();
        });
    }

}