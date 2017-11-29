package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class GamesHistoryActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<DataModelGameHistory> dataModels;
    ListView listView;
    private static MyCustomAdapter adapter;
    DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_history);

        listView = (ListView)findViewById(R.id.listView_gameHistory);
        mDbHelper = new DBHelper(this);
        dataModels = mDbHelper.getAllRows();
        adapter = new MyCustomAdapter(dataModels, this);

        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_back)){
            onBackPressed();
        }
    }
}
