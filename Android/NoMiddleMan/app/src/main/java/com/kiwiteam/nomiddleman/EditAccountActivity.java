package com.kiwiteam.nomiddleman;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditAccountActivity extends ActionBarActivity {

    private DatabaseConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        conn = (DatabaseConnection) getApplicationContext();

        fillText();
    }

    private void fillText() {
        String[] tourist = conn.getTouristInfo(0);
        EditText fName = (EditText) findViewById(R.id.userName);
        fName.setText(tourist[1]);
        EditText lName = (EditText) findViewById(R.id.userLName);
        lName.setText(tourist[2]);
        EditText email = (EditText) findViewById(R.id.email);
        email.setText(tourist[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public void confirm(View view) {
        EditText fName = (EditText) findViewById(R.id.userName);

        EditText lName = (EditText) findViewById(R.id.userLName);

        EditText email = (EditText) findViewById(R.id.email);

        String firstName = fName.getText().toString();
        String lastName = lName.getText().toString();
        String uEmail = email.getText().toString();

        conn.setTouristFName(0, firstName);
        conn.setTouristLName(0, lastName);
        conn.setTouristEmail(0, uEmail);

        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
