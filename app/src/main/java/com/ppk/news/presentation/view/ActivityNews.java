package com.ppk.news.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.ppk.news.R;
import com.ppk.news.data.model.NewsHeaderModel;
import com.ppk.news.presentation.contracts.NewsActivityContract;
import com.ppk.news.presentation.contracts.OnNewsClick;
import com.ppk.news.presentation.presenter.NewsPresenter;
import com.ppk.news.presentation.view.adapters.NewRecyclerViewAdapter;
import com.ppk.news.util.Default_Config;

import java.util.ArrayList;

public class ActivityNews extends AppCompatActivity implements NewsActivityContract.View, OnNewsClick {

    private NewsActivityContract.Presenter presenter;

    private RecyclerView newsRecyclerView;
    private NewRecyclerViewAdapter recyclerViewAdapter;
    private ProgressDialog dialog;

    private Chip general,sport,health,technology,business,entertainment,science;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        widgets();
        initiate();
    }

    private void widgets(){
        general = findViewById(R.id.general);
        health = findViewById(R.id.health);
        sport = findViewById(R.id.sport);
        technology = findViewById(R.id.technology);
        business = findViewById(R.id.business);
        entertainment = findViewById(R.id.entertainment);
        science = findViewById(R.id.science);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewAdapter = new NewRecyclerViewAdapter(this);

        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initiate() {
        general.setOnClickListener(view -> loadNews(Default_Config.GENERAL));
        health.setOnClickListener(view -> loadNews(Default_Config.HEALTH));
        sport.setOnClickListener(view -> loadNews(Default_Config.SPORT));
        science.setOnClickListener(view -> loadNews(Default_Config.SCIENCE));
        entertainment.setOnClickListener(view -> loadNews(Default_Config.ENTERTAINMENT));
        business.setOnClickListener(view -> loadNews(Default_Config.BUSINESS));
        technology.setOnClickListener(view -> loadNews(Default_Config.TECHNOLOGY));

        presenter = new NewsPresenter();
        presenter.setView(this);
    }

    @Override
    public void onNewsLoaded(ArrayList<NewsHeaderModel> newsHeaderList) {
        recyclerViewAdapter.setData(newsHeaderList);
        dialog.dismiss();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialog.dismiss();
        dialog = null;  //protect the memory leakage
    }

    @Override
    public void onNewsClick(NewsHeaderModel headerModel) {
        Intent intent =  new Intent(this,ActivityReadNews.class);
        intent.putExtra("newsHeader",headerModel);
        startActivity(intent);
    }

    private void loadNews(String category){
        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.loading));
        dialog.show();
        presenter.loadNews(category);
    }
}