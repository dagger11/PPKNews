package com.ppk.news.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.EditText;
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
import java.util.Objects;

public class ActivityNews extends AppCompatActivity implements NewsActivityContract.View, OnNewsClick {

    private NewsActivityContract.Presenter presenter;

    private RecyclerView newsRecyclerView;
    private NewRecyclerViewAdapter recyclerViewAdapter;
    private ProgressDialog dialog;

    private String currentCategory; //to get the user selected category

    private Chip general,sport,health,technology,business,entertainment,science;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        widgets();
        initiate();
    }

    private void widgets(){
        addToolbar();
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
        loadNews(Default_Config.GENERAL);   // load general as default cuz I have no enough time to write with check listener on chips
    }

    @Override
    public void onNewsLoaded(ArrayList<NewsHeaderModel> newsHeaderList) {
        if (newsHeaderList.isEmpty())
            showToast("No News To Show");
        recyclerViewAdapter.setData(newsHeaderList);
        dialog.dismiss();
        hideKeyboard();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        dialog.dismiss();
        dialog = null;  //protect the memory leakage

       finish();
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
        currentCategory = category;
        presenter.loadNews(category);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView =(SearchView) item.getActionView();
        searchView.setQueryHint("Search News");
        EditText editText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setHintTextColor(getResources().getColor(R.color.gray));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.loadNewsWithQuery(currentCategory,query);
                dialog.setTitle(getString(R.string.loading));
                dialog.show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed()); }

        private void hideKeyboard(){
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

}