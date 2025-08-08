package com.vlg.testick.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vlg.testick.App;
import com.vlg.testick.MainActivity;
import com.vlg.testick.Navigation;

public abstract class BaseFragment extends Fragment {
    protected Navigation navigation;
    protected App app;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigation = (Navigation) context;
        app = ((MainActivity) context).getApp();
    }
}
