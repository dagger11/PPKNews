package com.ppk.news.presentation.view.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ppk.news.R;
import com.ppk.news.data.model.NewsHeaderModel;
import com.ppk.news.presentation.contracts.OnNewsClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewRecyclerViewAdapter extends RecyclerView.Adapter<NewRecyclerViewAdapter.NewsViewHolder> {

    private final String TAG = "NewRecyclerViewAdapter";

    private ArrayList<NewsHeaderModel> newsHeaderList;

    private OnNewsClick onNewsClick;

    public NewRecyclerViewAdapter(OnNewsClick onNewsClick){
        newsHeaderList = new ArrayList<>();
        this.onNewsClick = onNewsClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<NewsHeaderModel> newsHeaderList){
        this.newsHeaderList = newsHeaderList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_header,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        try {
            NewsHeaderModel headerModel = newsHeaderList.get(position);
            holder.title.setText(headerModel.getTitle());
            holder.author.setText(headerModel.getSource().getName());

            if (headerModel.getUrlToImage() != null) {
                Picasso.get().load(headerModel.getUrlToImage()).into(holder.photo);
            }

            holder.newsHolder.setOnClickListener(view -> {
                onNewsClick.onNewsClick(headerModel);
            });

        }catch (IndexOutOfBoundsException| NullPointerException exception){
            Log.e(TAG,exception.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return newsHeaderList == null?0:newsHeaderList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView title, author;
        ImageView photo;
        CardView newsHolder;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            photo = itemView.findViewById(R.id.photo);
            newsHolder = itemView.findViewById(R.id.newsHolder);
        }
    }
}
