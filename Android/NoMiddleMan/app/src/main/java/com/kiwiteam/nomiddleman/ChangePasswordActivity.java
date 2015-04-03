package com.kiwiteam.nomiddleman;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ChangePasswordActivity extends ActionBarActivity {

    private DatabaseConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        conn = (DatabaseConnection) getApplicationContext();
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
        EditText oPass = (EditText) findViewById(R.id.oldPass);
        EditText nPass = (EditText) findViewById(R.id.newPass);
        EditText cNPass = (EditText) findViewById(R.id.cNewPass);

        String dbPass = conn.getPassword(0);

        if(dbPass.equals(oPass.getText().toString())) {
            if(nPass.getText().toString().equals(cNPass.getText().toString())) {
                conn.setPassword(0, nPass.getText().toString());
                finish();
            } else {
                Toast.makeText(this, R.string.password_not_match, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.incorrect_password, Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        finish();
    }
}
