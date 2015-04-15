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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CheckoutActivity extends ActionBarActivity {

    private DatabaseConnection conn;
    private ArrayAdapter<ShoppingItem> adapter;
    private ListView listView;
    private List<ShoppingItem> shoppingCart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        conn = (DatabaseConnection)getApplicationContext();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        shoppingCart.clear();
        handleIntent(intent);
    }

    public void removeItem(int position) {
        conn.removeFromShoppingCart(position);
        adapter.notifyDataSetChanged();
        TextView tPrice = (TextView) findViewById(R.id.price);
        if(!adapter.isEmpty()) {
            double price = conn.getTotalPrice();
            tPrice.setText("$" + String.format("%.2f", price));

        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shoppingCart = conn.getShoppingCart(0);

        if(!shoppingCart.isEmpty()) {

            adapter = new MyListAdapter();

            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            TextView tPrice = (TextView) findViewById(R.id.price);
            double prices = 0;
            for (int i=0;i<shoppingCart.size();i++) {
                prices = prices + shoppingCart.get(i).getTourPrice();
            }
            tPrice.setText("$" + String.format("%.2f", prices));

        } else {
            TextView fName = (TextView) findViewById(R.id.result);
            fName.setText(R.string.empty_cart);

            TextView tPrice = (TextView) findViewById(R.id.price);
            tPrice.setText("$0.00");
        }
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
        intent.putExtra("Index", conn.getT_key());
        startActivity(intent);
    }


    public void checkout(View view) {
        Intent intent = new Intent(this, PaypalActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }

    private class MyListAdapter extends ArrayAdapter<ShoppingItem> {

        public MyListAdapter() {
            super(CheckoutActivity.this, R.layout.checkout_item, shoppingCart);

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.checkout_item, parent, false);

            }

            itemView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(position);
                }
            });

            // find the list
            ShoppingItem currentTour = shoppingCart.get(position);

            // fill the view
            int draw = getResources().getIdentifier(currentTour.getTourPicture().get(0),"drawable",getPackageName());

            ImageView picture = (ImageView) itemView.findViewById(R.id.tourPic);
            Drawable img = getResources().getDrawable(draw);

            picture.setImageDrawable(img);


            TextView tName = (TextView) itemView.findViewById(R.id.tourName);
            tName.setText(currentTour.getTourName());
            //System.out.println(currentTour.getName());

            TextView tPrice = (TextView) itemView.findViewById(R.id.tourPrice);
            double price = currentTour.getTourPrice();
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
