package com.kiwiteam.nomiddleman;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;


public class RegisterActivity extends ActionBarActivity {

    private String userEmail;
    private String password;
    private String userName;
    private String userLName;
    private String telephoneNumber;

    public int success;

    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    private static String url_register = "http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/register.php";
    DatabaseConnection conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        conn = (DatabaseConnection)getApplicationContext();

        TextView terms = (TextView) findViewById(R.id.textView33);
        terms.setLinkTextColor(Color.BLUE);
        terms.setLinksClickable(true);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://kiwiteam.ece.uprm.edu/NoMiddleMan/Terms%20and%20Conditions.html"));
                startActivity(intent);
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    /**
     * Calls register class to send to php
     * @param view
     */
    public void register(View view) {
        EditText uEmail = (EditText) findViewById(R.id.email);
        userEmail = uEmail.getText().toString();
        EditText uPhone = (EditText) findViewById(R.id.telephone);
        telephoneNumber = uPhone.getText().toString();

        if(validateEmail(userEmail)) {

            if(validatePhone(telephoneNumber)) {
                new Register().execute();
            } else {
                Toast.makeText(this, "No valid telephone number", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            Toast.makeText(this, "No valid email", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Validates email with regular expression
     * @param email
     * @return
     */
    private boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    private boolean validatePhone(String phone) {
        String PHONE_REGEX = "\\\\d{10}";
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        return pattern.matcher(phone).matches();
    }



    class Register extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Registering. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            String result = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();

                EditText uEmail = (EditText) findViewById(R.id.email);
                userEmail = uEmail.getText().toString();
                EditText pass = (EditText) findViewById(R.id.password);
                password = pass.getText().toString();
                EditText uName = (EditText) findViewById(R.id.firstText);
                userName = uName.getText().toString();
                EditText uLName = (EditText) findViewById(R.id.lastText);
                userLName = uLName.getText().toString();
                EditText telephone = (EditText) findViewById(R.id.telephone);
                telephoneNumber = telephone.getText().toString();


                List<NameValuePair> categoryName = new ArrayList<>();
                categoryName.add(new BasicNameValuePair("t_Email", userEmail));
                categoryName.add(new BasicNameValuePair("t_password", password));
                categoryName.add(new BasicNameValuePair("t_FName", userName));
                categoryName.add(new BasicNameValuePair("t_LName", userLName));
                categoryName.add(new BasicNameValuePair("t_telephone", telephoneNumber));

                //////////////////////////////////

                HttpPost httppost = new HttpPost(url_register);

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

                success = jObj.getInt(TAG_SUCCESS);

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
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not register", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
