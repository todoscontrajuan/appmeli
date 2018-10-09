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
import com.me.squad.appmeli.model.SearchResultItem;
import com.me.squad.appmeli.ui.DetailsPageActivity;

import java.util.List;

public class ResultContainerAdapter extends RecyclerView.Adapter<ResultContainerAdapter.ResultItemViewHolder>{

    private List<SearchResultItem> resultList;
    private Context context;

    public ResultContainerAdapter(Context context, List<SearchResultItem> resultList) {
        this.resultList = resultList;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_item, viewGroup, false);
        return new ResultItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResultItemViewHolder resultItemViewHolder, int i) {
        resultItemViewHolder.itemTitle.setText(resultList.get(i).getItemTitle());
        resultItemViewHolder.itemPrice.setText(resultList.get(i).getItemPrice());
        resultItemViewHolder.itemImage.setImageResource(resultList.get(i).getImageItemId());
        if (resultList.get(i).isFreeShipping()) {
            resultItemViewHolder.itemFreeShipping.setVisibility(View.VISIBLE);
        }
        resultItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsPageActivity.class);
                intent.putExtra("selected", resultList.get(resultItemViewHolder.getAdapterPosition()));
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

}
