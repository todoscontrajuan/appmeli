package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

    RecyclerView resultContainer;
    private static final int SCROLL_DIRECTION_UP = -1;
    private static final String TAG = "APP_MELI_ERROR";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Setup Toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String searchValue = intent.getStringExtra(MainActivity.SEARCH_VALUE);
        ((TextView) toolbar.findViewById(R.id.toolbar_text)).setText(searchValue);

        // Call Service and setup screen
        resultContainer = findViewById(R.id.result_container);
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        ItemSearchService service = RetrofitInstance.getRetrofitInstance().create(ItemSearchService.class);
        Call<SearchResult> call = service.getSearchResult(searchValue);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.body().getResults().size() != 0) {
                    setupResultList(response.body().getResults());
                } else {
                    resultContainer.setVisibility(View.GONE);
                    TextView noResultsMessage = findViewById(R.id.no_results_message);
                    noResultsMessage.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.d(TAG, "Something went wrong...Error message: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
        resultContainer.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (resultContainer.canScrollVertically(SCROLL_DIRECTION_UP)) {
                    toolbar.setElevation(getResources().getDimension(R.dimen.card_elevation));
                } else {
                    toolbar.setElevation(getResources().getDimension(R.dimen.no_elevation));
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupResultList(List<Result> resultList) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        resultContainer.setLayoutManager(manager);
        ResultContainerAdapter adapter = new ResultContainerAdapter(this, resultList);
        resultContainer.setAdapter(adapter);
    }
}
