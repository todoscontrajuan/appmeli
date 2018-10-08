package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.me.squad.appmeli.R;
import com.me.squad.appmeli.model.SearchResultItem;

public class DetailPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        // Setup Toolbar
        Intent intent = getIntent();
        SearchResultItem item = (SearchResultItem) intent.getSerializableExtra("selected");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) toolbar.findViewById(R.id.toolbar_text)).setText(item.getItemTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
