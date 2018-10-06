package com.me.squad.appmeli;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {
        // Show landing page by default
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new LandingPageFragment());
        fragmentTransaction.commit();

        // Find layout elements
        final FrameLayout fragmentContainer = findViewById(R.id.fragment_container);
        final RecyclerView suggestionList = findViewById(R.id.suggestion_list);
        final SearchView searchView = findViewById(R.id.search_bar);

        // Search bar listeners setup
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                fragmentContainer.setVisibility(View.GONE);
                suggestionList.setVisibility(View.VISIBLE);
            }
        });
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(true);
                fragmentContainer.setVisibility(View.VISIBLE);
                suggestionList.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
