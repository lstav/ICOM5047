package com.kiwiteam.nomiddleman;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    DatabaseConnection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        conn = (DatabaseConnection)getApplicationContext();

        initSearchView();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
       setIntent(intent);
       handleIntent(intent);
       initSearchView();
    }

    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Integer> indexes;
        String[] tour;
        ArrayList<String> tourNames = new ArrayList<String>();
        ArrayList<String> tourPrices = new ArrayList<String>();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            indexes = conn.searchToursByCategories(query);

            if(!indexes.isEmpty()) {
                //ListView lv = (ListView) findViewById(R.id.listView);
                for(int i=0;i<indexes.size();i++) {
                    tour = conn.getTourInformation(indexes.get(i));
                    tourNames.add(tour[0]);
                    tourPrices.add(tour[3]);
                }


                int[] toViews = {R.id.tourName, R.id.tourPrice};

                findViewById(R.id.result).setVisibility(View.GONE);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.result_list, R.id.tourName, tourNames);

                /*TextView fName = (TextView) findViewById(R.id.result);
                fName.setText(tourNames.get(0));*/

                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);

            } else {
                TextView fName = (TextView) findViewById(R.id.result);
                fName.setText("No Results");
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    private void initSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
