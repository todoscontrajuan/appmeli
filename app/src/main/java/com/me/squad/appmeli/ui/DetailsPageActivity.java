package com.me.squad.appmeli.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.me.squad.appmeli.R;
import com.me.squad.appmeli.model.Result;

import java.util.ArrayList;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

public class DetailsPageActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        // Setup Slider
        Slider slider = findViewById(R.id.image_slider);
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0,"http://cssslider.com/sliders/demo-20/data1/images/picjumbo.com_img_4635.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_corner)));
        slideList.add(new Slide(1,"http://cssslider.com/sliders/demo-12/data1/images/picjumbo.com_hnck1995.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_corner)));
        slideList.add(new Slide(2,"http://cssslider.com/sliders/demo-19/data1/images/picjumbo.com_hnck1588.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_corner)));
        slideList.add(new Slide(3,"http://wowslider.com/sliders/demo-18/data1/images/shanghai.jpg" , getResources().getDimensionPixelSize(R.dimen.slider_corner)));
        slider.addSlides(slideList);

        // Setup page details
        Intent intent = getIntent();
        Result product = (Result) intent.getSerializableExtra("selected");
        TextView productTitle = findViewById(R.id.product_title);
        productTitle.setText(product.getTitle());
        TextView productPrice = findViewById(R.id.product_price);
        productPrice.setText("$" + Float.toString(product.getPrice()));
        TextView productRating = findViewById(R.id.product_rating);
        productRating.setText(Float.toString(product.getReviews().getRatingAverage()));
        TextView productShipping = findViewById(R.id.product_shipping);
        if (product.getShipping().getFreeShipping()) {
            productShipping.setText(getString(R.string.free_shipping_indicator_text));
            productShipping.setTextColor(getColor(R.color.colorBrandDark));
        } else {
            productShipping.setText(getString(R.string.pay_shipping_indicator_text));
        }
        TextView productSeller = findViewById(R.id.product_seller);
        productSeller.setText("Vendedor de prueba");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
