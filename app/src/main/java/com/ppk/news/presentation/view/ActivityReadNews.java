package com.ppk.news.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppk.news.R;
import com.ppk.news.data.model.NewsHeaderModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ActivityReadNews extends AppCompatActivity {

    private NewsHeaderModel newsHeaderModel;

    TextView title,date,newsLetter,newsAuthor,newsDescription;
    ImageView newsPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);

        widgets();
        initiate();
    }

    private void widgets() {
        addToolbar();
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        newsLetter = findViewById(R.id.newsLetter);
        newsAuthor = findViewById(R.id.newsAuthor);
        newsPhoto = findViewById(R.id.newsPhoto);
        newsDescription = findViewById(R.id.newsDescription);
    }

    private void initiate() {
        newsHeaderModel = (NewsHeaderModel) getIntent().getSerializableExtra("newsHeader");

        title.setText(newsHeaderModel.getTitle());
        date.setText(newsHeaderModel.getPublishedAt());
        newsLetter.setText(newsHeaderModel.getContent());
        newsAuthor.setText(newsHeaderModel.getAuthor());
        Picasso.get().load(newsHeaderModel.getUrlToImage()).into(newsPhoto);
        newsDescription.setText(newsHeaderModel.getDescription());
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed()); }

    @Override
    public void onBackPressed() {
        finish();
    }
}