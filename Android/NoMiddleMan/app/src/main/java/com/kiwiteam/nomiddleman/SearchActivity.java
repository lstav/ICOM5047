package com.kiwiteam.nomiddleman;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.AsyncTask;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;


public class SearchActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseConnection conn;
    private boolean selectedCategory = false;
    private List<Tour> tourInfo = new ArrayList<>();
    private Menu menu;
    private Spinner sortBy;
    private ArrayAdapter<CharSequence> sAdapter;
    private ListView listView;
    private ProgressDialog pDialog;
    private String query;
    private ImageView picture;
    private Bitmap bitmap;

    private JSONArray backup;

    private static final String TAG_KEY = "key";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_EXTREMENESS = "extremeness";
    private static final String TAG_PHOTO = "photo";


    private static String url_search_categories = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/searchByCategory.php";
    private static String url_search_keyword = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/searchByKeyword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        conn = (DatabaseConnection)getApplicationContext();

        Intent intent = getIntent();

        String category = intent.getStringExtra("searchCategory");

        // Choose search method
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

    /**
     * Search tours by category
     * @param intent
     */
    private void handleCategoryIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        query = intent.getStringExtra("category");
        new LoadByCategory().execute();
    }

    /**
     * Search tours by keyword
     * @param intent
     */
    private void handleIntent(Intent intent) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Integer> indexes;
        TourClass tour;

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            new LoadByKeyword().execute();
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

    /**
     * Starts search manager
     */
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

    /**
     * Calls tourist account activity
     */
    public void account() {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("Index", conn.getT_key());
        startActivity(intent);
    }

    /**
     * Click listener for search results
     */
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

    /**
     * Fills the listview with query results
     */
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
            //int draw = getResources().getIdentifier(currentTour.getPictures().get(0),"drawable",getPackageName());

            picture = (ImageView) itemView.findViewById(R.id.tourPic);
            picture.setImageBitmap(currentTour.getPictures().get(0));
            //String url = currentTour.getPictures().get(0);

            //new LoadImage().execute(url.trim()+"img1.jpg");

            //System.out.println("Photo " + url.trim() + "img1.jpg" + " Name " + currentTour.getName());
            //Drawable img = getResources().getDrawable(draw);

            //picture.setImageDrawable(img);

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

    /**
     * Search database with results by categories
     */
    class LoadByCategory extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Loading results. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                String url;

                List<NameValuePair> categoryName = new ArrayList<>();
                categoryName.add(new BasicNameValuePair("category", query));

                HttpPost httppost = new HttpPost(url_search_categories);

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

                    tourInfo.add(new Tour(c.getString(TAG_NAME), Price.getDouble(c.getString(TAG_PRICE)), new ArrayList<>(Arrays.asList(bitmap)), Integer.parseInt(c.getString(TAG_KEY)), Double.parseDouble(c.getString(TAG_EXTREMENESS))));
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
                    ArrayAdapter<Tour> adapter = new MyListAdapter();

                    listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                }
            });
        }

    }

    /**
     * Search database with results by keyword
     */
    class LoadByKeyword extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Loading results. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                String url;

                List<NameValuePair> categoryName = new ArrayList<>();
                categoryName.add(new BasicNameValuePair("keyword", query));

                HttpPost httppost = new HttpPost(url_search_keyword);

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

                    tourInfo.add(new Tour(c.getString(TAG_NAME), Price.getDouble(c.getString(TAG_PRICE)), new ArrayList<>(Arrays.asList(bitmap)), Integer.parseInt(c.getString(TAG_KEY)), Double.parseDouble(c.getString(TAG_EXTREMENESS))));
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
                    ArrayAdapter<Tour> adapter = new MyListAdapter();

                    listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                }
            });
        }

    }
}
