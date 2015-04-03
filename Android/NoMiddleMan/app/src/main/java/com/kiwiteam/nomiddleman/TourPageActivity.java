package com.kiwiteam.nomiddleman;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


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

        TextView tName = (TextView) findViewById(R.id.tourName);
        tName.setText(tour.getTourName());

        RatingBar tRating = (RatingBar) findViewById(R.id.tourRating);
        tRating.setRating((float) tour.getTourRating());

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

        /*tTime = (Spinner) findViewById(R.id.time);
        tAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,tour.getTourSessionsTime(tDay.getSelectedItem().toString()));
        tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tTime.setAdapter(tAdapter);*/

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
        EditText qty = (EditText) findViewById(R.id.quantity);
        this.quantity = Integer.parseInt(qty.getText().toString());
        this.date = tDay.getSelectedItem().toString();
        this.time = tTime.getSelectedItem().toString();

        if(quantity > 0) {
            Toast.makeText(this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            conn.putToursToShoppingCart( tourID, quantity, date, time);
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
}
