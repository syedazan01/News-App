package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsItemClicked {
    RecyclerView recyclerView;
    String url;
    private  NewsAdapter mAdapter = new NewsAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hooks
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fetchData();
        //this is for the interface
        recyclerView.setAdapter(mAdapter);

    }

    private void fetchData() {
        //url of the api
       // url = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=943a77e3df724a6a8b827ccf1012dae0";
        url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json";
        //for calling api we need to use json
        //creating JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray newsJsonArray;
                        ArrayList<News> newsArray = new ArrayList<>();
                        JSONObject newsJsonObject;
                        try {
                            newsJsonArray = response.getJSONArray("articles");
                            for(int i=0;i<newsJsonArray.length();i++){

                                newsJsonObject = newsJsonArray.getJSONObject(i);
                                News  news = new News(newsJsonObject.getString("title"),
                                        newsJsonObject.getString("author"),
                                        newsJsonObject.getString("url"),
                                        newsJsonObject.getString("urlToImage"));
                                newsArray.add(news);
                            }
                            mAdapter.updateNews(newsArray);


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }


    @Override
    public void onItemClicked(News item) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));

    }
}