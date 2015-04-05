package com.kiwiteam.nomiddleman;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class RegisterActivity extends ActionBarActivity {

    private String userEmail;
    private String password;
    private String userName;
    private String userLName;

    DatabaseConnection conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    public void register(View view) {
        EditText uEmail = (EditText) findViewById(R.id.email);
        userEmail = uEmail.getText().toString();

        if(validateEmail(userEmail)) {
            EditText pass = (EditText) findViewById(R.id.password);
            password = pass.getText().toString();
            EditText uName = (EditText) findViewById(R.id.firstText);
            userName = uName.getText().toString();
            EditText uLName = (EditText) findViewById(R.id.lastText);
            userLName = uLName.getText().toString();
            conn.register(userEmail, password, userName, userLName);
            finish();
        } else {
            Toast.makeText(this, "No valid email", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }


}
