package com.kiwiteam.nomiddleman;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ShoppingCartActivity extends ActionBarActivity {

    private DatabaseConnection conn;
    private ArrayAdapter<ShoppingItem> adapter;
    private ListView listView;
    private List<ShoppingItem> shoppingCart = new ArrayList<>();

    private double totalPrice = 0.0;

    private Bitmap bitmap;
    private ProgressDialog pDialog;
    private ImageView picture;

    private JSONArray backup;

    private static final String TAG_KEY = "key";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_EXTREMENESS = "extremeness";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_QUANTITY = "quantity";
    private static final String TAG_DATE = "date";
    private static final String TAG_TIME = "time";
    private static final String TAG_ACTIVE = "isActive";
    private static final String TAG_SUCCESS = "success";


    private static String url_get_shopping_cart = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/getShoppingCart.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

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
        //conn.removeFromShoppingCart(position);
        adapter.notifyDataSetChanged();
        TextView tPrice = (TextView) findViewById(R.id.price);
        if(!adapter.isEmpty()) {
            double price = conn.getTotalPrice();
            tPrice.setText("$" + String.format("%.2f", price));

        } else {
            TextView fName = (TextView) findViewById(R.id.result);
            findViewById(R.id.result).setVisibility(View.VISIBLE);
            fName.setText(R.string.empty_cart);

            findViewById(R.id.items).setVisibility(View.GONE);
            findViewById(R.id.price).setVisibility(View.GONE);
            findViewById(R.id.checkout).setVisibility(View.GONE);

            //tPrice.setText("$0.00");
        }
    }

    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new LoadShoppingCart().execute();
        /*shoppingCart = conn.getShoppingCart(0);

        if(!shoppingCart.isEmpty()) {

            findViewById(R.id.result).setVisibility(View.GONE);



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

            findViewById(R.id.items).setVisibility(View.GONE);
            findViewById(R.id.price).setVisibility(View.GONE);
            findViewById(R.id.checkout).setVisibility(View.GONE);
        }*/
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

    private void account() {
            Intent intent = new Intent(this, AccountActivity.class);
            intent.putExtra("Index", conn.getT_key());
            startActivity(intent);
        }

    public void checkout(View view) {
        if(conn.isLogged()) {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private class MyListAdapter extends ArrayAdapter<ShoppingItem> {

        public MyListAdapter() {
            super(ShoppingCartActivity.this, R.layout.shopping_cart_item, shoppingCart);

        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.shopping_cart_item, parent, false);

            }

            itemView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(position);
                }
            });

            itemView.findViewById(R.id.rate).setVisibility(View.GONE);

            // find the list
            ShoppingItem currentTour = shoppingCart.get(position);

            /*if(!currentTour.isActive()) {
                itemView.findViewById(R.id.active).setVisibility(View.VISIBLE);
            }*/

            // fill the view
           /* int draw = getResources().getIdentifier(currentTour.getTourPicture().get(0),"drawable",getPackageName());

            ImageView picture = (ImageView) itemView.findViewById(R.id.tourPic);
            Drawable img = getResources().getDrawable(draw);

            picture.setImageDrawable(img);
*/
            picture = (ImageView) itemView.findViewById(R.id.tourPic);
            picture.setImageBitmap(currentTour.getTourPicture().get(0));

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

    /**
     * Search database with results by keyword
     */
    class LoadShoppingCart extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShoppingCartActivity.this);
            pDialog.setMessage("Loading results. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                String url;

                List<NameValuePair> categoryName = new ArrayList<>();
                categoryName.add(new BasicNameValuePair("t_key", Integer.toString(conn.getT_key())));

                HttpPost httppost = new HttpPost(url_get_shopping_cart);

                httppost.setEntity(new UrlEncodedFormEntity(categoryName));

                HttpResponse response = httpClient.execute(httppost);

                HttpEntity entity = response.getEntity();
                InputStream webs = entity.getContent();

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(webs,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    webs.close();
                    result=sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jObj = new JSONObject(result);
                backup = jObj.getJSONArray("tours");

                for (int i=0; i<backup.length(); i++) {
                    JSONObject c = backup.getJSONObject(i);
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(c.getString(TAG_PHOTO).trim() + "img1.jpg").getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    boolean isActive = false;
                    if(c.getString(TAG_ACTIVE).equals("t")) {
                        isActive = true;
                    }
                    shoppingCart.add(new ShoppingItem(new Tour(c.getString(TAG_NAME),
                            Price.getDouble(c.getString(TAG_PRICE)),
                            new ArrayList<>(Arrays.asList(bitmap)),
                            c.getInt(TAG_KEY),c.getDouble(TAG_EXTREMENESS)),
                            c.getInt(TAG_QUANTITY),c.getString(TAG_DATE), c.getString(TAG_TIME),
                            isActive));

                    totalPrice = totalPrice + Price.getDouble(c.getString(TAG_PRICE));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<ShoppingItem> adapter = new MyListAdapter();

                    listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);

                    TextView tPrice = (TextView) findViewById(R.id.price);
                    tPrice.setText("$" + String.format("%.2f", totalPrice));
                }
            });
        }

    }
}
