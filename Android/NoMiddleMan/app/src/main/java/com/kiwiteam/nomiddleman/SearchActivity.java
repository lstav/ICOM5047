package com.kiwiteam.nomiddleman;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends ActionBarActivity {

    DatabaseConnection conn;
    private boolean selectedCategory = false;
    private List<Tour> tourInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        conn = (DatabaseConnection)getApplicationContext();

        Intent intent = getIntent();

        String category = intent.getStringExtra("searchCategory");

        if(category == null) {
            selectedCategory = false;
        } else {
            selectedCategory = category.equals("true");
        }

        if(selectedCategory) {
            handleCategoryIntent(intent);
        } else {
            handleIntent(intent);
        }

        initSearchView();
        registerClickCallback();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);

        tourInfo.clear();

        String category = intent.getStringExtra("searchCategory");

        if(category == null) {
            selectedCategory = false;
        } else {
            selectedCategory = category.equals("true");
        }

        if(selectedCategory) {
            handleCategoryIntent(intent);
        } else {
            handleIntent(intent);
        }
        initSearchView();
        registerClickCallback();
    }

    private void handleCategoryIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Integer> indexes;
        TourClass tour;


        String query = intent.getStringExtra("category");
        indexes = conn.searchToursByCategories(query);

        if(!indexes.isEmpty()) {
            for(int i=0;i<indexes.size();i++) {
                tour = conn.getTourInformation(indexes.get(i));
                this.tourInfo.add(new Tour(tour.getTourName(), tour.getTourPrice(), tour.getTourPictures(), indexes.get(i)));
            }

            findViewById(R.id.result).setVisibility(View.GONE);

            ArrayAdapter<Tour> adapter = new MyListAdapter();

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

        } else {
            TextView fName = (TextView) findViewById(R.id.result);
            fName.setText("No Results");
        }
    }

    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Integer> indexes;
        TourClass tour;

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            indexes = conn.searchToursByString(query);

            if(!indexes.isEmpty()) {
                for(int i=0;i<indexes.size();i++) {
                    tour = conn.getTourInformation(indexes.get(i));
                    this.tourInfo.add(new Tour(tour.getTourName(), tour.getTourPrice(), tour.getTourPictures(), indexes.get(i)));
                }

                findViewById(R.id.result).setVisibility(View.GONE);

                ArrayAdapter<Tour> adapter = new MyListAdapter();


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

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                Tour clickedTour = tourInfo.get(position);

                Intent i = new Intent(getApplicationContext(), TourPageActivity.class);

                i.putExtra("tourId",clickedTour.getId());

                startActivity(i);

                /*String message = "You clicked position " + position
                        + " Which is tour name " + clickedTour.getName();
                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();*/
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Tour> {

        public MyListAdapter() {
            super(SearchActivity.this, R.layout.result_list, tourInfo);

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.result_list, parent, false);

            }

            // find the list
            Tour currentTour = tourInfo.get(position);

            // fill the view
            int draw = getResources().getIdentifier(currentTour.getPictures().get(0),"drawable",getPackageName());

            ImageView picture = (ImageView) itemView.findViewById(R.id.tourPic);
            Drawable img = getResources().getDrawable(draw);

            picture.setImageDrawable(img);


            TextView tName = (TextView) itemView.findViewById(R.id.tourName);
            tName.setText(currentTour.getName());
            //System.out.println(currentTour.getName());

            TextView tPrice = (TextView) itemView.findViewById(R.id.tourPrice);
            tPrice.setText(currentTour.getPrice());



            return itemView;

            //return super.getView(position, convertView, parent);
        }
    }
}
