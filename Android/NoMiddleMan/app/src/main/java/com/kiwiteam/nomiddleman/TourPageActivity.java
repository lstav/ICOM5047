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
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.util.List;


public class TourPageActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseConnection conn;
    private TourClass tour;
    private int tourID;
    private int quantity = 1;
    private String date = new String();
    private String time = new String();
    private Spinner tDay;
    private int query;
    private Spinner tTime;
    private ArrayAdapter<String> tAdapter;
    private ArrayAdapter<String> dAdapter;
    private ArrayAdapter<RatingClass> rAdapter;
    private ListView listView;
    private List<RatingClass> ratings = new ArrayList<>();

    private Bitmap bitmap;

    private JSONArray response;

    private ProgressDialog pDialog;
    private static String url_get_tourpage = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/getTour.php";

    private static final String TAG_KEY = "tour_key";
    private static final String TAG_NAME = "tour_Name";
    private static final String TAG_DESC = "tour_Desc";
    private static final String TAG_EXTREMENESS = "extremeness";
    private static final String TAG_PHOTO = "tour_photo";
    private static final String TAG_FACEBOOK = "Facebook";
    private static final String TAG_YOUTUBE = "Youtube";
    private static final String TAG_INSTAGRAM = "Instagram";
    private static final String TAG_TWITTER = "Twitter";
    private static final String TAG_DURATION = "Duration";
    private static final String TAG_PRICE = "Price";
    private static final String TAG_QUANTITY = "tour_quantity";
    private static final String TAG_TIME = "s_Time";
    private static final String TAG_AVAILBILITY = "Availability";



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
            query = tourID;
            //tour = conn.getTourInformation(tourID);
        }

        /*ratings = tour.getAllRatings();

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
*/
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
            final RatingClass currentRating = ratings.get(position);

            // fill the view
            RatingBar tRating = (RatingBar) itemView.findViewById(R.id.tourRating);
            tRating.setRating((float) currentRating.getRating());

            TextView tReview = (TextView) itemView.findViewById(R.id.review);
            tReview.setText(currentRating.getReview());

            itemView.findViewById(R.id.review).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    intent.putExtra("Rating", currentRating.getRating());
                    intent.putExtra("Review", currentRating.getReview());
                    startActivity(intent);
                }
            });

            return itemView;

        }
    }

    class GetTourPage extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TourPageActivity.this);
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
                categoryName.add(new BasicNameValuePair("tour_key", Integer.toString(query)));

                String paramString = URLEncodedUtils.format(categoryName, "utf-8");
                url = url_get_tourpage + "?" + paramString;

                HttpGet httpGet = new HttpGet(url);

                HttpResponse response = httpClient.execute(httpGet);

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
                response = jObj.getJSONArray("tour");

                for (int i=0; i<response.length(); i++) {
                    JSONObject c = response.getJSONObject(i);
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(c.getString(TAG_PHOTO).trim() + "img1.jpg").getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //tour = new TourClass(c.getInt(TAG_KEY),c.getString(TAG_NAME),c.getString())
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
                    ArrayAdapter<RatingClass> adapter = new MyListAdapter();

                    listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                }
            });
        }
    }
}
