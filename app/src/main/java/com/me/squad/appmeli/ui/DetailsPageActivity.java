package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.me.squad.appmeli.R;
import com.me.squad.appmeli.model.Picture;
import com.me.squad.appmeli.model.Product;
import com.me.squad.appmeli.service.ItemSearchService;
import com.me.squad.appmeli.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPageActivity extends AppCompatActivity {

    private float productAverageRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) toolbar.findViewById(R.id.toolbar_text)).setText(getText(R.string.product_page_title));

        // Get selected product data
        Intent intent = getIntent();
        String productId = intent.getStringExtra("selected");
        productAverageRating = intent.getFloatExtra("averageRating", 0f);

        // Call service for extra info
        ItemSearchService service = RetrofitInstance.getRetrofitInstance().create(ItemSearchService.class);
        Call<Product> call = service.getProductDetails(productId);
        call.enqueue(new Callback<Product>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                setupSlider(response.body().getPictures());
                setupDetails(response);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(DetailsPageActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupSlider(List<Picture> pictureList) {
        Slider slider = findViewById(R.id.image_slider);
        List<Slide> slideList = new ArrayList<>();
        for (int i = 0; i < pictureList.size(); i++) {
            slideList.add(new Slide(i, pictureList.get(i).getUrl(), getResources().getDimensionPixelSize(R.dimen.slider_corner)));
        }
        slider.addSlides(slideList);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupDetails(Response<Product> response) {
        TextView productTitle = findViewById(R.id.product_title);
        productTitle.setText(response.body().getTitle());
        TextView productPrice = findViewById(R.id.product_price);
        productPrice.setText(String.format("%s$%s", response.body().getCurrencyId(), Float.toString(response.body().getPrice())));
        TextView productRating = findViewById(R.id.product_rating);
        productRating.setText(Float.toString(productAverageRating));
        TextView productShipping = findViewById(R.id.product_shipping);
        if (response.body().getShipping().getFreeShipping()) {
            productShipping.setText(getString(R.string.free_shipping_indicator_text));
            productShipping.setTextColor(getColor(R.color.colorBrandDark));
        } else {
            productShipping.setText(getString(R.string.pay_shipping_indicator_text));
        }
        TextView productState = findViewById(R.id.product_state);
        switch (response.body().getCondition()) {
            case "new":
                productState.setText(getString(R.string.state_new_indicator));
                break;
            case "used":
                productState.setText(getString(R.string.state_used_indicator));
                break;
            default:
                RelativeLayout productStateButton =  findViewById(R.id.product_state_indicator);;
                productStateButton.setVisibility(View.GONE);
                break;
        }
        RelativeLayout productDescriptionButton =  findViewById(R.id.product_description_button);;
        productDescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductDescriptionActivity.class);
                startActivity(intent);
            }
        });
    }
}
