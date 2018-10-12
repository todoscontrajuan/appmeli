package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.me.squad.appmeli.R;
import com.me.squad.appmeli.model.Product;
import com.me.squad.appmeli.model.ProductDescription;
import com.me.squad.appmeli.service.ItemSearchService;
import com.me.squad.appmeli.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) toolbar.findViewById(R.id.toolbar_text)).setText(getText(R.string.product_description_title));

        // Get id from intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");

        // Call service
        ItemSearchService service = RetrofitInstance.getRetrofitInstance().create(ItemSearchService.class);
        Call<ProductDescription> call = service.getProductDescription(productId);
        call.enqueue(new Callback<ProductDescription>() {
            @Override
            public void onResponse(Call<ProductDescription> call, Response<ProductDescription> response) {
                TextView descriptionText = findViewById(R.id.product_description_text);
                descriptionText.setText(response.body().getPlainText());
            }

            @Override
            public void onFailure(Call<ProductDescription> call, Throwable t) {
                Log.d("APP_MELI_ERROR", "Something went wrong...Error message: " + t.getMessage());
            }
        });
    }
}
