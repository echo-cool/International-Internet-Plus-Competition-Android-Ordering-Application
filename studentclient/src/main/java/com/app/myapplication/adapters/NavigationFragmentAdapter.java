package com.app.myapplication.adapters;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class NavigationFragmentAdapter extends FragmentStateAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    public NavigationFragmentAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle, List<Fragment>  fragmentList) {
        super(fragmentManager, lifecycle);
        this.fragmentList = fragmentList;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
