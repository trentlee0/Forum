package com.example.forum.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.forum.R;
import com.example.forum.databinding.FragmentHomeBinding;
import com.example.forum.ui.post.PostEditorActivity;
import com.example.forum.util.ToastUtil;
import com.example.forum.util.UserUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private List<Fragment> tabs = new ArrayList<>();
    private String[] titles = {"板块", "帖子"};

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FloatingActionButton addPostActionBtn = root.findViewById(R.id.fab_add_post);
        addPostActionBtn.setOnClickListener(v -> {
            if (UserUtil.isLogin()) {
                startActivity(new Intent(getContext(), PostEditorActivity.class));
            } else {
                ToastUtil.show(getContext(), "请登录！");
            }
        });

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        viewPager.setAdapter(new PagerAdapter(getActivity()));
        tabs.add(new PlateFragment());
        tabs.add(new PostFragment());

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(titles[position])).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return tabs.get(position);
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }
    }
}