package com.example.shopappfirebase.fragments.sellTabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopappfirebase.fragments.AccountFragment;
import com.example.shopappfirebase.fragments.HomeFragment;
import com.example.shopappfirebase.fragments.SellFragment;

public class SellViewPagerAdapter extends FragmentStateAdapter {
    public SellViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ProductsFragment();
            case 1:
                return new OrdersFragment();
            default:
                return new HomeFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }



}
