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


public class PurchaseHistoryActivity extends ActionBarActivity {

    private DatabaseConnection conn;
    private ArrayList<PurchaseHistory.HistoryItem> purchaseHistory;
    private ArrayAdapter<PurchaseHistory.HistoryItem> activeAdapter;
    private ArrayAdapter<PurchaseHistory.HistoryItem> pastAdapter;
    private ListView upcomingListView;
    private ListView pastListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        conn = (DatabaseConnection) getApplicationContext();

        Intent intent = getIntent();

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        purchaseHistory = conn.getHistory();
        ArrayList<PurchaseHistory.HistoryItem> activeHistory = new ArrayList<>();
        ArrayList<PurchaseHistory.HistoryItem> pastHistory = new ArrayList<>();

        for(int i=0; i<purchaseHistory.size(); i++) {
            if(purchaseHistory.get(i).getTour().sessionIsActive(purchaseHistory.get(i).getSessionID())) {
                activeHistory.add(purchaseHistory.get(i));
                findViewById(R.id.noUpcomingTours).setVisibility(View.GONE);
            } else {
                findViewById(R.id.pastTours).setVisibility(View.GONE);
                pastHistory.add(purchaseHistory.get(i));
            }
        }

        activeAdapter = new MyListAdapter(activeHistory);

        pastAdapter = new MyListAdapter(pastHistory);

        upcomingListView = (ListView) findViewById(R.id.upcommingListView);
        upcomingListView.setAdapter(activeAdapter);

        pastListView = (ListView) findViewById(R.id.pastListView);
        pastListView.setAdapter(pastAdapter);

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

    private class MyListAdapter extends ArrayAdapter<PurchaseHistory.HistoryItem> {
        private ArrayList<PurchaseHistory.HistoryItem> pHistory;

        public MyListAdapter(ArrayList<PurchaseHistory.HistoryItem> purchaseHistory) {
            super(PurchaseHistoryActivity.this, R.layout.shopping_cart_item, purchaseHistory);
            pHistory = purchaseHistory;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.shopping_cart_item, parent, false);

            }

            itemView.findViewById(R.id.remove).setVisibility(View.GONE);

            // find the list
            PurchaseHistory.HistoryItem currentTour = pHistory.get(position);

            // fill the view
            int draw = getResources().getIdentifier(currentTour.getTour().getTourPictures().get(0),"drawable",getPackageName());

            ImageView picture = (ImageView) itemView.findViewById(R.id.tourPic);
            Drawable img = getResources().getDrawable(draw);

            picture.setImageDrawable(img);


            TextView tName = (TextView) itemView.findViewById(R.id.tourName);
            tName.setText(currentTour.getTour().getTourName());

            TextView tPrice = (TextView) itemView.findViewById(R.id.tourPrice);
            double price = currentTour.getPrice();
            tPrice.setText("$"+ String.format("%.2f", price));

            TextView tQuantity = (TextView) itemView.findViewById(R.id.quantity);
            tQuantity.setText(Integer.toString(currentTour.getQuantity()));

            TextView tDate = (TextView) itemView.findViewById(R.id.date);
            tDate.setText(currentTour.getDate());

            TextView tTime = (TextView) itemView.findViewById(R.id.time);
            tTime.setText(currentTour.getTime());

            return itemView;
        }
    }

}
