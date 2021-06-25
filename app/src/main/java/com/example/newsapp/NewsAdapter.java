package com.example.newsapp;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<News> newsListData = new ArrayList<>();
    private NewsItemClicked newsItemClicked;

    public NewsAdapter(NewsItemClicked newsItemClicked) {
        this.newsItemClicked = newsItemClicked;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        final News currentItem =newsListData.get(position);
        holder.textView.setText(newsListData.get(position).getTitle());
        holder.author.setText((newsListData.get(position).getAuthor()));
        Glide.with(holder.itemView.getContext()).load(newsListData.get(position).getImageUrl()).into(holder.imageView);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsItemClicked.onItemClicked(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsListData.size();
    }

    void updateNews(ArrayList<News> updateNews) {
        newsListData.clear();
        newsListData.addAll(updateNews);

        notifyDataSetChanged();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView textView,author;
        ImageView imageView;
        ConstraintLayout constraintLayout;


        public NewsViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_textView);
            author = itemView.findViewById(R.id.author);
            imageView = itemView.findViewById(R.id.imageView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }

    interface NewsItemClicked {
        public void onItemClicked(News item);

    }
}





