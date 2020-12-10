package com.example.parsejsonwithgson;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parsejsonwithgson.adapter.ResultAdapter;
import com.example.parsejsonwithgson.model.BoxResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton;
    private ListView mListView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.button);
        mListView = findViewById(R.id.list_view);
        mTextView = findViewById(R.id.textView);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                GetJsonWithOkhttp();
                break;

        }
    }

    private void GetJsonWithOkhttp() {
        Log.e("MY", "GetJsonWithOkhttp: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.2.93/get_json.json")
                            .build();
                    Log.e("MY", "url: ");
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.e("MY", responseData);
                    ParseWithGson(responseData);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void ParseWithGson(String responseData) {
        Gson gson = new Gson();
        final List<BoxResult> boxResult = GsonUtils.GsonToList(responseData, BoxResult.class);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mTextView.setText(boxResult.toString());
//            }
//        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.setAdapter(new ResultAdapter(MainActivity.this, boxResult));
            }
        });

    }
}