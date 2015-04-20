package com.kiwiteam.nomiddleman;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
    private Spinner tQty;
    private ArrayAdapter<String> tAdapter;
    private ArrayAdapter<String> dAdapter;
    private ArrayAdapter<Integer> qAdapter;
    private ArrayAdapter<RatingClass> rAdapter;
    private ListView listView;
    private List<RatingClass> ratings = new ArrayList<>();
    private ImageView picture;
    ArrayList<RatingClass> tourRatingsA = new ArrayList<>();
    ArrayList<TourSession> tourSessionsA = new ArrayList<>();

    private Bitmap bitmap;

    private JSONArray tourResponse;
    private JSONArray tourSessions;
    private JSONArray tourReviews;

    private ProgressDialog pDialog;
    private static String url_get_tourpage = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/getTour.php";
    private static String url_add_to_cart = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/addToCart.php";

    private static final String TAG_SUCCESS = "success";

    private static final String TAG_KEY = "key";
    private static final String TAG_NAME = "name";
    private static final String TAG_DESC = "description";
    private static final String TAG_FACEBOOK = "facebook";
    private static final String TAG_YOUTUBE = "youtube";
    private static final String TAG_INSTAGRAM = "instagram";
    private static final String TAG_TWITTER = "twitter";
    private static final String TAG_PRICE = "price";
    private static final String TAG_EXTREMENESS = "extremeness";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_EMAIL = "gemail";
    private static final String TAG_GNAME = "gname";
    private static final String TAG_LICENSE = "license";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_TELEPHONE = "telephone";
    private static final String TAG_AVGRATE = "averagerate";
    private static final String TAG_RATECOUNT = "ratecount";

    private static final String TAG_TSKEY = "tskey";
    private static final String TAG_TIME = "time";
    private static final String TAG_DATE = "date";
    private static final String TAG_AVAILABILITY = "availability";

    private static final String TAG_TKEY = "tkey";
    private static final String TAG_RATING = "rating";
    private static final String TAG_REVIEW = "review";

    private int success;

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
            new GetTourPage().execute();
            //tour = conn.getTourInformation(tourID);
        }
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
        intent.putExtra("Index", conn.getT_key());
        startActivity(intent);
    }

    public void addToCart(View view) {
        Spinner qty = (Spinner) findViewById(R.id.quantity);
        this.quantity = Integer.parseInt(qty.getSelectedItem().toString());
        this.date = tDay.getSelectedItem().toString();
        this.time = tTime.getSelectedItem().toString();

        if(quantity > 0) {
            //Toast.makeText(this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
            new AddToCart().execute();
            //conn.putToursToShoppingCart(tourID, quantity, date, time);
        } else {
            Toast.makeText(this, R.string.no_quantity, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int vId = parent.getId();
        switch (vId) {
            case R.id.day:
                String day = parent.getItemAtPosition(position).toString();
                ArrayList<String> times = tour.getTourSessionsTime(day);

                tTime = (Spinner) findViewById(R.id.time);
                tAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
                tAdapter.notifyDataSetChanged();

                tTime.setOnItemSelectedListener(this);
                tAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tTime.setAdapter(tAdapter);
                break;
            case R.id.time:
                String time = parent.getItemAtPosition(position).toString();
                ArrayList<Integer> quantities = tour.getTourSessionAvailability(tDay.getSelectedItem().toString(), time);

                tQty = (Spinner) findViewById(R.id.quantity);
                qAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantities);
                qAdapter.notifyDataSetChanged();

                qAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tQty.setAdapter(qAdapter);
                break;
        }
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
                categoryName.add(new BasicNameValuePair("tour_key", Integer.toString(query)));

                HttpPost httpPost = new HttpPost(url_get_tourpage);

                httpPost.setEntity(new UrlEncodedFormEntity(categoryName));

                HttpResponse response = httpClient.execute(httpPost);

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
                tourResponse = jObj.getJSONArray("tour");
                tourSessions = jObj.getJSONArray("sessions");
                tourReviews = jObj.getJSONArray("reviews");

                for(int i=0; i<tourResponse.length(); i++) {
                    JSONObject c = tourResponse.getJSONObject(i);
                    try {
                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(c.getString(TAG_PHOTO).trim() + "img1.jpg").getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    for (int j = 0; j < tourSessions.length(); j++) {
                        JSONObject d = tourSessions.getJSONObject(j);

                        tourSessionsA.add(new TourSession(d.getString(TAG_DATE), d.getString(TAG_TIME), d.getInt(TAG_TSKEY), d.getInt(TAG_AVAILABILITY)));
                    }

                    for (int j = 0; j < tourReviews.length(); j++) {
                        JSONObject d = tourReviews.getJSONObject(j);

                        tourRatingsA.add(new RatingClass(d.getDouble(TAG_RATING), d.getString(TAG_REVIEW)));
                    }

                    tour = new TourClass(c.getInt(TAG_KEY), c.getString(TAG_NAME), c.getString(TAG_DESC), c.getString(TAG_FACEBOOK), c.getString(TAG_YOUTUBE),
                            c.getString(TAG_INSTAGRAM), c.getString(TAG_TWITTER), Price.getDouble(c.getString(TAG_PRICE)), c.getDouble(TAG_EXTREMENESS), new ArrayList<>(Arrays.asList(bitmap)),
                            c.getString(TAG_ADDRESS), c.getString(TAG_EMAIL), c.getString(TAG_GNAME), c.getString(TAG_LICENSE), c.getString(TAG_COMPANY),
                            c.getString(TAG_TELEPHONE), c.getDouble(TAG_AVGRATE), c.getInt(TAG_RATECOUNT), tourRatingsA, tourSessionsA);

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
                    ratings = tourRatingsA;

                    TextView tName = (TextView) findViewById(R.id.tourName);
                    tName.setText(tour.getTourName());

                    RatingBar tRating = (RatingBar) findViewById(R.id.tourRating);
                    tRating.setRating((float) tour.getAverageRating());

                    tRating = (RatingBar) findViewById(R.id.ratingBar);
                    tRating.setRating((float) tour.getAverageRating());

                    RatingBar extremeBar = (RatingBar) findViewById(R.id.extremeness);
                    extremeBar.setRating((float) tour.getExtremeness());

                    TextView ratingNumber = (TextView) findViewById(R.id.review_number);
                    ratingNumber.setText("(" +tour.getRateCount() + ")");

                    TextView totalReviews = (TextView) findViewById(R.id.totalReviews);
                    totalReviews.setText(String.format("%.1f", tour.getAverageRating()) + " of 5.0");

                    ImageView tPicture = (ImageView) findViewById(R.id.tourPicture);
                    tPicture.setImageBitmap(tour.getTourPictures().get(0));

                    TextView tPrice = (TextView) findViewById(R.id.tourPrice);
                    tPrice.setText("$" + String.format("%.2f", tour.getTourPrice()));

                    tDay = (Spinner) findViewById(R.id.day);

                    dAdapter = new ArrayAdapter<>(TourPageActivity.this, android.R.layout.simple_spinner_item,tour.getTourSessionsDate());
                    dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    tDay.setOnItemSelectedListener(TourPageActivity.this);

                    tDay.setAdapter(dAdapter);

                    TextView tDescription = (TextView) findViewById(R.id.description);
                    tDescription.setText(tour.getTourDescription());

                    TextView tAddress = (TextView) findViewById(R.id.address);
                    tAddress.setText(tour.getTourAddress());

                    TextView gMail = (TextView) findViewById(R.id.gEmail);
                    gMail.setText(tour.getGuideEmail());

                    TextView gName = (TextView) findViewById(R.id.gName);
                    gName.setText(tour.getGuideName());

                    TextView license = (TextView) findViewById(R.id.license);
                    license.setText(tour.getGuideLicense());

                    TextView company = (TextView) findViewById(R.id.company);
                    gName.setText(tour.getCompany());

                    TextView telephone = (TextView) findViewById(R.id.telephone);
                    license.setText(tour.getTelephone());

                    rAdapter = new MyListAdapter();

                    ListView reviewList = (ListView) findViewById(R.id.reviewList);
                    reviewList.setAdapter(rAdapter);
                    setListViewHeightBasedOnChildren(reviewList);

                    /*ArrayAdapter<RatingClass> adapter = new MyListAdapter();

                    listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);*/
                }
            });
        }
    }
    class AddToCart extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TourPageActivity.this);
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
                categoryName.add(new BasicNameValuePair("ts_key", Integer.toString(tour.getTourSessionID(tDay.getSelectedItem().toString(),tTime.getSelectedItem().toString()))));
                categoryName.add(new BasicNameValuePair("quantity", tQty.getSelectedItem().toString()));

                HttpPost httpPost = new HttpPost(url_add_to_cart);

                httpPost.setEntity(new UrlEncodedFormEntity(categoryName));

                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();
                InputStream webs = entity.getContent();

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    webs.close();
                    result = sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jObj = new JSONObject(result);

                success = jObj.getInt(TAG_SUCCESS);

                System.out.println("Success = " + success);
                System.out.println("Message = " + jObj.getString("message"));

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
                    if(success == 1) {
                        Toast.makeText(TourPageActivity.this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
