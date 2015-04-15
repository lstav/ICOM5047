package com.kiwiteam.nomiddleman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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


public class LoginActivity extends ActionBarActivity {

    DatabaseConnection conn;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_KEY = "key";
    private ProgressDialog pDialog;
    private static String url_login = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/login.php";
    private JSONArray backup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        conn = (DatabaseConnection)getApplicationContext();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_cart:
                intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                return true;
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        new Login().execute();
    }

    class Login extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            String result = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                String url;

                EditText emailText = (EditText) findViewById(R.id.email);
                EditText passText = (EditText) findViewById(R.id.password);

                String email = emailText.getText().toString();
                String password = passText.getText().toString();

                List<NameValuePair> categoryName = new ArrayList<>();
                categoryName.add(new BasicNameValuePair("t_Email", email));
                categoryName.add(new BasicNameValuePair("t_password", password));

                HttpPost httppost = new HttpPost(url_login);

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

                int success = jObj.getInt(TAG_SUCCESS);

                if(success == 1) {
                    conn.setLogged(true, jObj.getInt(TAG_KEY));
                } else {

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
                    if(conn.isLogged()) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.wrong_login, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


}
