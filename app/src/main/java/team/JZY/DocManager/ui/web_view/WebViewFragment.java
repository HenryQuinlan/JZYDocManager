package team.JZY.DocManager.ui.web_view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jetbrains.annotations.NotNull;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.MainActivity;
import team.JZY.DocManager.databinding.WebViewFragmentBinding;
import team.JZY.DocManager.ui.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends DocManagerApplication.Fragment {
    //TODO UNKNOWN MAYBE WEBVIEW? OR LOCAL DOC MANAGER OR  JUST LOCAL DOWNLOAD
    private WebViewFragmentBinding binding;
    private static WebViewFragmentBinding bindingRecord;
    private UserViewModel userViewModel;
    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(bindingRecord == null) {
            binding = WebViewFragmentBinding.inflate(inflater,container,false);
            initWebView();
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
        if(binding.webView.isFocusable()) {
            binding.webView.setFocusable(true);
            binding.webView.setFocusableInTouchMode(true);
            binding.webView.requestFocus();
        }
        Log.d("FFFFFF", getActivity() == null?"ss":"dd");
        //userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setSupportMultipleWindows(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        binding.webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; Nexus 4 Build/KRT16H) AppleWebKit/537.36" +
//            "(KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }
                if (appInstalledOrNot(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                }
                else {
                    Log.d("dddd", "shouldOverrideUrlLoading: ");
                }
                return true;
            }
        });

        binding.webView.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_BACK&&binding.webView.canGoBack()){
                binding.webView.goBack();
                return true;
            }
            return false;
        });
        binding.webView.loadUrl("https://www.baidu.com");
    }

    private boolean appInstalledOrNot(String uri) {
        if(getActivity() == null) Log.d("GGGG","ss");
        PackageManager pm = requireActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


}