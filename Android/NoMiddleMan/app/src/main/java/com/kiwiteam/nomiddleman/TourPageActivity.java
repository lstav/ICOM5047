package com.kiwiteam.nomiddleman;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TourPageActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseConnection conn;
    private TourClass tour;
    private int tourID;
    private int quantity = 1;
    private String date = new String();
    private String time = new String();
    private Spinner tDay;
    private Spinner tTime;
    private ArrayAdapter<String> tAdapter;
    private ArrayAdapter<String> dAdapter;
    private ArrayAdapter<RatingClass> rAdapter;
    private List<RatingClass> ratings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_page);

        conn = (DatabaseConnection) getApplicationContext();

        Intent intent = getIntent();
        initTourPage(intent);
    }

    public void initTourPage(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         tourID = intent.getIntExtra("tourId", -1);

        if( tourID == -1) {
            finish();
        } else {
            tour = conn.getTourInformation( tourID);
        }

        ratings = tour.getAllRatings();

        TextView tName = (TextView) findViewById(R.id.tourName);
        tName.setText(tour.getTourName());

        RatingBar tRating = (RatingBar) findViewById(R.id.tourRating);
        tRating.setRating((float) tour.getTourRating());

        tRating = (RatingBar) findViewById(R.id.ratingBar);
        tRating.setRating((float) tour.getTourRating());

        RatingBar extremeBar = (RatingBar) findViewById(R.id.extremeness);
        extremeBar.setRating((float) tour.getExtremeness());

        TextView ratingNumber = (TextView) findViewById(R.id.review_number);
        ratingNumber.setText("(" +tour.getTourRatings().size() + ")");

        TextView totalReviews = (TextView) findViewById(R.id.totalReviews);
        totalReviews.setText(String.format("%.1f", tour.getTourRating()) + " of 5.0");

        ImageView tPicture = (ImageView) findViewById(R.id.tourPicture);
        Drawable img = getResources().getDrawable(getResources().getIdentifier(tour.getTourPictures().get(0),"drawable",getPackageName()));
        tPicture.setImageDrawable(img);

        TextView tPrice = (TextView) findViewById(R.id.tourPrice);
        tPrice.setText("$" + String.format("%.2f", tour.getTourPrice()));

        tDay = (Spinner) findViewById(R.id.day);
        dAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tour.getTourSessionsDate());
        dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tDay.setOnItemSelectedListener(this);

        tDay.setAdapter(dAdapter);

        TextView tDescription = (TextView) findViewById(R.id.description);
        tDescription.setText(tour.getTourDescription());

        rAdapter = new MyListAdapter();

        ListView reviewList = (ListView) findViewById(R.id.reviewList);
        reviewList.setAdapter(rAdapter);
        setListViewHeightBasedOnChildren(reviewList);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        if (conn.isLogged())
        {
            menu.findItem(R.id.account).setVisible(true);
            menu.findItem(R.id.signout).setVisible(true);
        } else {
            menu.findItem(R.id.account).setVisible(false);
            menu.findItem(R.id.signout).setVisible(false);
        }
        //initSearchView(menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        return true;
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
                recreate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void account() {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("Index", conn.getIndex());
        startActivity(intent);
    }

    public void addToCart(View view) {
        EditText qty = (EditText) findViewById(R.id.quantity);
        this.quantity = Integer.parseInt(qty.getText().toString());
        this.date = tDay.getSelectedItem().toString();
        this.time = tTime.getSelectedItem().toString();

        if(quantity > 0) {
            Toast.makeText(this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            conn.putToursToShoppingCart(tourID, quantity, date, time);
        } else {
            Toast.makeText(this, R.string.no_quantity, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String day = parent.getItemAtPosition(position).toString();
        ArrayList<String> times = tour.getTourSessionsTime(day);

        tTime = (Spinner) findViewById(R.id.time);
        tAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,times);
        tAdapter.notifyDataSetChanged();
        tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tTime.setAdapter(tAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void openLink(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.youtube.com"));
        startActivity(browserIntent);
    }

    private class MyListAdapter extends ArrayAdapter<RatingClass> {

        public MyListAdapter() {
            super(TourPageActivity.this, R.layout.review_item, ratings);

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.review_item, parent, false);

            }

            // find the list
            RatingClass currentRating = ratings.get(position);

            // fill the view
            RatingBar tRating = (RatingBar) itemView.findViewById(R.id.tourRating);
            tRating.setRating((float) currentRating.getRating());

            TextView tReview = (TextView) itemView.findViewById(R.id.review);
            tReview.setText(currentRating.getReview());

            return itemView;

        }
    }
}
