package com.example.statussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.statussaver.databinding.ActivityMainBinding;
import com.example.statussaver.databinding.ActivityWhatsappBinding;
import com.example.statussaver.fragments.ImageFragment;
import com.example.statussaver.fragments.VideoFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class WhatsappActivity extends AppCompatActivity {

    private ActivityWhatsappBinding binding;
    private WhatsappActivity activity;
    private  ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_whatsapp);

        activity = this;

        initView();
    }

    private void initView() {
        adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(),
                activity.getLifecycle());
        adapter.addFragment(new ImageFragment(), "Images");
        adapter.addFragment(new VideoFragment(), "Videos");

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) ->{
                 tab.setText(adapter.fragmentTitleList.get(position));
                } ).attach();

        for (int i=0; i<binding.tabLayout.getTabCount(); i++){
            TextView tv = (TextView) LayoutInflater.from(activity).
                    inflate(R.layout.custom_tab, null);

            binding.tabLayout.getTabAt(i).setCustomView(tv);

        }

    }

    class  ViewPagerAdapter extends FragmentStateAdapter{
private final List<Fragment> fragmentList = new ArrayList<>();
private final List<String> fragmentTitleList = new ArrayList<>();



        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

   public  void  addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
   }
        @NonNull
        @org.jetbrains.annotations.NotNull ///extra
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}