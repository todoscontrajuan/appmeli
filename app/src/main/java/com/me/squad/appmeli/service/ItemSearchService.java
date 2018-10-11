package com.me.squad.appmeli.service;

import com.me.squad.appmeli.model.Product;
import com.me.squad.appmeli.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ItemSearchService {
    // Get search results based on a query
    @GET("sites/MLU/search")
    Call<SearchResult> getSearchResult(@Query("q") String query);

    // Get extra info item
    @GET("items/{id}")
    Call<Product> getProductDetails(@Path("id") String id);
}
