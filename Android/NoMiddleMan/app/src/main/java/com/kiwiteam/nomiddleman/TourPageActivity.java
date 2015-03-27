package com.kiwiteam.nomiddleman;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class TourPageActivity extends ActionBarActivity {

    DatabaseConnection conn;
    TourClass tour;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_page);

        conn = (DatabaseConnection)getApplicationContext();

        Intent intent = getIntent();
        initTourPage(intent);
    }

    public void initTourPage(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        index = intent.getIntExtra("tourId", -1);

        if(index == -1) {
            finish();
        } else {
            tour = conn.getTourInformation(index);
        }

        TextView tName = (TextView) findViewById(R.id.tourName);
        tName.setText(tour.getTourName());

        RatingBar tRating = (RatingBar) findViewById(R.id.tourRating);
        tRating.setRating((float) tour.getTourRating());

        ImageView tPicture = (ImageView) findViewById(R.id.tourPicture);
        Drawable img = getResources().getDrawable(getResources().getIdentifier(tour.getTourPictures().get(0),"drawable",getPackageName()));
        tPicture.setImageDrawable(img);

        TextView tPrice = (TextView) findViewById(R.id.tourPrice);
        tPrice.setText(tour.getTourPrice());

        Spinner day = (Spinner) findViewById(R.id.day);
        ArrayAdapter<String> dAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tour.getTourSessionsDate());
        day.setAdapter(dAdapter);

        Spinner time = (Spinner) findViewById(R.id.time);
        ArrayAdapter<String> tAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tour.getTourSessionsTime());
        time.setAdapter(tAdapter);

        TextView tDescription = (TextView) findViewById(R.id.description);
        tDescription.setText(tour.getTourDescription());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
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
            case R.id.action_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToCart(View view) {
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
        conn.putToursToShoppingCart(index);
    }
}
