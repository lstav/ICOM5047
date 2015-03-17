package com.kiwiteam.nomiddleman;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void register(View view) {
        EditText uEmail = (EditText) findViewById(R.id.email);
        userEmail = uEmail.getText().toString();
        EditText pass = (EditText) findViewById(R.id.password);
        password = pass.getText().toString();
        EditText uName = (EditText) findViewById(R.id.firstText);
        userName = uName.getText().toString();
        EditText uLName = (EditText) findViewById(R.id.lastText);
        userLName = uLName.getText().toString();

        conn.register(userEmail, password, userName, userLName);

        finish();
    }


}
