package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.me.squad.appmeli.R;
import com.me.squad.appmeli.model.SearchResultItem;
import com.me.squad.appmeli.ui.adapter.ResultContainerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String searchValue = intent.getStringExtra(MainActivity.SEARCH_VALUE);
        ((TextView) toolbar.findViewById(R.id.toolbar_text)).setText(searchValue);

        // Setup RecyclerView
        RecyclerView resultContainer = findViewById(R.id.result_container);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        resultContainer.setLayoutManager(manager);
        // Adding Test Data
        List<SearchResultItem> resultList = new ArrayList<>();
        resultList.add(new SearchResultItem("Item de prueba", "$400.000,00", R.drawable.ic_launcher_background, true));
        resultList.add(new SearchResultItem("Item de prueba 2", "$4.000,00", R.drawable.ic_launcher_background, false));
        resultList.add(new SearchResultItem("Item de prueba 3", "$5,50", R.drawable.ic_launcher_background, true));
        resultList.add(new SearchResultItem("Item de prueba con un nombre largo 4", "$400,00", R.drawable.ic_launcher_background, false));
        resultList.add(new SearchResultItem("Item de prueba 5", "$400,00", R.drawable.ic_launcher_background, false));
        resultList.add(new SearchResultItem("Item de prueba 6", "$400,00", R.drawable.ic_launcher_background, false));
        ResultContainerAdapter adapter = new ResultContainerAdapter(resultList);
        resultContainer.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
