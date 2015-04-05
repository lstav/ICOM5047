package com.kiwiteam.nomiddleman;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        handleIntent(intent);
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

    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        purchaseHistory = conn.getHistory();
        ArrayList<PurchaseHistory.HistoryItem> activeHistory = new ArrayList<>();
        ArrayList<PurchaseHistory.HistoryItem> pastHistory = new ArrayList<>();

        for(int i=0; i<purchaseHistory.size(); i++) {
            if(purchaseHistory.get(i).getTour().sessionIsActive(purchaseHistory.get(i).getSessionID())) {
                activeHistory.add(purchaseHistory.get(i));
                findViewById(R.id.noUpcomingTours).setVisibility(View.GONE);
                System.out.println("Active Index " + i);
            } else {
                findViewById(R.id.pastTours).setVisibility(View.GONE);
                pastHistory.add(purchaseHistory.get(i));
            }
        }

        activeAdapter = new MyListAdapter(activeHistory);

        pastAdapter = new MyListAdapter(pastHistory);

        upcomingListView = (ListView) findViewById(R.id.upcommingListView);
        pastListView = (ListView) findViewById(R.id.pastListView);

        upcomingListView.setAdapter(activeAdapter);
        pastListView.setAdapter(pastAdapter);

        setListViewHeightBasedOnChildren(upcomingListView);
        setListViewHeightBasedOnChildren(pastListView);
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



    public void rateItem(int tourID, int position) {
        Intent intent = new Intent(this, RateActivity.class);
        intent.putExtra("Tour ID", tourID);
        intent.putExtra("History ID", position);
        startActivity(intent);
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

            if(!pHistory.get(position).isRated()) {
                itemView.findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateItem(pHistory.get(position).getTour().getTourID(), position);
                    }
                });
            } else {
                itemView.findViewById(R.id.rate).setVisibility(View.GONE);
            }

            itemView.findViewById(R.id.tourPic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TourPageActivity.class);
                    intent.putExtra("tourId",pHistory.get(position).getTour().getTourID());
                    startActivity(intent);
                }
            });

            return itemView;
        }
    }

}
