package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.me.squad.appmeli.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
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
        ArrayList<String> suggestionElementsList = new ArrayList<>();
        // TODO Set this strings in xml
        suggestionElementsList.add("Autos y Motos");
        suggestionElementsList.add("Computación");
        suggestionElementsList.add("Inmuebles");
        suggestionElementsList.add("Electrodomesticos");
        suggestionElementsList.add("Juegos y Juguetes");
        suggestionElementsList.add("Celulares");
        suggestionElementsList.add("Ropa");
        suggestionElementsList.add("Consolas y Videojuegos");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.suggestion_list_item, suggestionElementsList);
        suggestionList.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                performSearch(s);
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
