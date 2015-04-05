package com.kiwiteam.nomiddleman;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class SearchActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseConnection conn;
    private boolean selectedCategory = false;
    private List<Tour> tourInfo = new ArrayList<>();
    private Menu menu;
    private Spinner sortBy;
    private ArrayAdapter<CharSequence> sAdapter;
    private ListView listView;

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
                if(tour.isActive()) {
                    this.tourInfo.add(new Tour(tour.getTourName(), tour.getTourPrice(), tour.getTourPictures(), indexes.get(i), tour.getExtremeness()));
                }
            }

            findViewById(R.id.result).setVisibility(View.GONE);

            sortBy = (Spinner) findViewById(R.id.sortBy);
            sAdapter = ArrayAdapter.createFromResource(this, R.array.sort_by_array, android.R.layout.simple_spinner_item);
            sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sortBy.setOnItemSelectedListener(this);

            sortBy.setAdapter(sAdapter);


            ArrayAdapter<Tour> adapter = new MyListAdapter();

            listView = (ListView) findViewById(R.id.listView);
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
                    if(tour.isActive()) {
                        this.tourInfo.add(new Tour(tour.getTourName(), tour.getTourPrice(), tour.getTourPictures(), indexes.get(i), tour.getExtremeness()));
                    }
                }

                findViewById(R.id.result).setVisibility(View.GONE);

                sortBy = (Spinner) findViewById(R.id.sortBy);
                sAdapter = ArrayAdapter.createFromResource(this, R.array.sort_by_array, android.R.layout.simple_spinner_item);
                sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sortBy.setOnItemSelectedListener(this);

                sortBy.setAdapter(sAdapter);


                ArrayAdapter<Tour> adapter = new MyListAdapter();


                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);

            } else {
                TextView fName = (TextView) findViewById(R.id.result);
                fName.setText(R.string.no_results);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        if (conn.isLogged())
        {
            menu.findItem(R.id.account).setVisible(true);
            menu.findItem(R.id.signout).setVisible(true);
        } else {
            menu.findItem(R.id.account).setVisible(false);
            menu.findItem(R.id.signout).setVisible(false);
        }
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_cart:
                intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                return true;
            case R.id.account:
                account();
                return true;
            case R.id.signout:
                conn.signout();
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void account() {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("Index", conn.getIndex());
        startActivity(intent);
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
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

            RatingBar eRating = (RatingBar) itemView.findViewById(R.id.tourRating);
            eRating.setRating((float) currentTour.getExtremeness());

            TextView tName = (TextView) itemView.findViewById(R.id.tourName);
            tName.setText(currentTour.getName());
            //System.out.println(currentTour.getName());

            TextView tPrice = (TextView) itemView.findViewById(R.id.tourPrice);
            tPrice.setText("$" + String.format("%.2f", currentTour.getPrice()));



            return itemView;

            //return super.getView(position, convertView, parent);
        }
    }
}
