package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.me.squad.appmeli.R;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH_VALUE = "searchValue";

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
        final ListView suggestionList = findViewById(R.id.suggestion_list);
        final SearchView searchView = findViewById(R.id.search_bar);
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);

        // Search bar listeners setup
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
                fragmentContainer.setVisibility(View.GONE);
                suggestionList.setVisibility(View.VISIBLE);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(true);
                fragmentContainer.setVisibility(View.VISIBLE);
                suggestionList.setVisibility(View.GONE);
            }
        });

        // Setup search bar suggestions
        String[] suggestionElementsList = new String[] {getString(R.string.suggestion_1), getString(R.string.suggestion_2),
                getString(R.string.suggestion_3), getString(R.string.suggestion_4), getString(R.string.suggestion_5),
                getString(R.string.suggestion_6), getString(R.string.suggestion_7), getString(R.string.suggestion_8),
                getString(R.string.suggestion_9), getString(R.string.suggestion_10)};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.suggestion_list_item, suggestionElementsList);
        suggestionList.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                performSearch(s);
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        suggestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = ((TextView) view.findViewById(R.id.suggestion_text)).getText().toString();
                performSearch(selected);
            }
        });
    }

    private void performSearch(String query) {
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra(SEARCH_VALUE, query);
        startActivity(intent);
    }
}
