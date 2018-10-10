
package com.me.squad.appmeli.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reviews implements Serializable {
    @SerializedName("rating_average")
    @Expose
    private float ratingAverage;
    @SerializedName("total")
    @Expose
    private int total;

    public float getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(float ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
