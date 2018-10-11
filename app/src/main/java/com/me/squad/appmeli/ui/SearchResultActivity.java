package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.me.squad.appmeli.R;
import com.me.squad.appmeli.model.Result;
import com.me.squad.appmeli.model.SearchResult;
import com.me.squad.appmeli.service.ItemSearchService;
import com.me.squad.appmeli.service.RetrofitInstance;
import com.me.squad.appmeli.ui.adapter.ResultContainerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        // Call Service
        ItemSearchService service = RetrofitInstance.getRetrofitInstance().create(ItemSearchService.class);
        Call<SearchResult> call = service.getSearchResult(searchValue);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.body() != null) {
                    setupResultList(response.body().getResults());
                } else {
                    Toast.makeText(SearchResultActivity.this, "No results", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Toast.makeText(SearchResultActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupResultList(List<Result> resultList) {
        RecyclerView resultContainer = findViewById(R.id.result_container);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        resultContainer.setLayoutManager(manager);
        ResultContainerAdapter adapter = new ResultContainerAdapter(this, resultList);
        resultContainer.setAdapter(adapter);
    }
}
