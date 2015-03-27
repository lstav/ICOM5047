package com.kiwiteam.nomiddleman;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ShoppingCartActivity extends ActionBarActivity {

    DatabaseConnection conn;
    private List<Tour> tourInfo = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        conn = (DatabaseConnection)getApplicationContext();

        Intent intent = getIntent();

        handleIntent(intent);

    }

    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Integer> indexes = conn.getShoppingCart(0);
        TourClass tour;

        if(!indexes.isEmpty()) {
            for(int i=0;i<indexes.size();i++) {
                tour = conn.getTourInformation(indexes.get(i));
                this.tourInfo.add(new Tour(tour.getTourName(), tour.getTourPrice(), tour.getTourPictures(), indexes.get(i)));
            }

            findViewById(R.id.result).setVisibility(View.GONE);

            ArrayAdapter<Tour> adapter = new MyListAdapter();


            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            TextView tPrice = (TextView) findViewById(R.id.price);
            tPrice.setText(tourInfo.get(0).getPrice());

        } else {
            TextView fName = (TextView) findViewById(R.id.result);
            fName.setText("No Results");
        }



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
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyListAdapter extends ArrayAdapter<Tour> {

        public MyListAdapter() {
            super(ShoppingCartActivity.this, R.layout.shopping_cart_item, tourInfo);

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.shopping_cart_item, parent, false);

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
