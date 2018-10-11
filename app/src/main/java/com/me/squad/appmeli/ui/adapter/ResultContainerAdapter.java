package com.me.squad.appmeli.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.squad.appmeli.R;
import com.me.squad.appmeli.model.Result;
import com.me.squad.appmeli.ui.DetailsPageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultContainerAdapter extends RecyclerView.Adapter<ResultContainerAdapter.ResultItemViewHolder>{

    private List<Result> resultList;
    private Context context;

    public ResultContainerAdapter(Context context, List<Result> resultList) {
        this.resultList = resultList;
        this.context = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ResultItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_item, viewGroup, false);
        return new ResultItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultItemViewHolder resultItemViewHolder, int i) {
        resultItemViewHolder.itemTitle.setText(resultList.get(i).getTitle());
        resultItemViewHolder.itemPrice.setText(String.format("%s$%s", resultList.get(i).getCurrencyId(), Float.toString(resultList.get(i).getPrice())));
        Picasso.get().load(resultList.get(i).getThumbnail()).into(resultItemViewHolder.itemImage);
        if (resultList.get(i).getShipping().getFreeShipping()) {
            resultItemViewHolder.itemFreeShipping.setVisibility(View.VISIBLE);
        }
        resultItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsPageActivity.class);
                intent.putExtra("selected", resultList.get(resultItemViewHolder.getAdapterPosition()).getId());
                intent.putExtra("averageRating", resultList.get(resultItemViewHolder.getAdapterPosition()).getReviews().getRatingAverage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public static class ResultItemViewHolder extends RecyclerView.ViewHolder {
        CardView container;
        TextView itemTitle;
        TextView itemPrice;
        ImageView itemImage;
        TextView itemFreeShipping;

        ResultItemViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.search_result_item_container);
            itemTitle = itemView.findViewById(R.id.search_result_item_title);
            itemPrice = itemView.findViewById(R.id.search_result_item_price);
            itemImage = itemView.findViewById(R.id.search_result_item_image);
            itemFreeShipping = itemView.findViewById(R.id.search_result_item_free_shipping);;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
