package com.example.shopappfirebase.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopappfirebase.databinding.FragmentSellBinding;
import com.example.shopappfirebase.fragments.sellTabs.SellViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class SellFragment extends Fragment {

    private TabLayout tabLayout;

    private ViewPager2 viewPager2;

    private FragmentSellBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellBinding.inflate(inflater, container, false);
        tabLayout = binding.tabLayout;
        viewPager2 = binding.sellViewPager;

        SellViewPagerAdapter adapter = new SellViewPagerAdapter(getActivity());
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("My product");
                        break;
                    case 1:
                        tab.setText("Orders received");
                        break;
                }
            }
        }).attach();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}