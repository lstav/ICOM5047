package com.kiwiteam.nomiddleman;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class AccountActivity extends ActionBarActivity {

    private DatabaseConnection conn;
    private String userEmail;
    private int index;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        conn = (DatabaseConnection)getApplicationContext();
        intent = getIntent();

        fillText();

    }

    private void fillText() {
        index = intent.getIntExtra("Index",-1);
        String[] touristInfo = conn.getTouristInfo(index);

        TextView fName = (TextView) findViewById(R.id.userName);
        fName.setText(touristInfo[1]);

        TextView lName = (TextView) findViewById(R.id.userLName);
        lName.setText(touristInfo[2]);

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(touristInfo[0]);
    }

    public void onResume() {
        super.onResume();
        fillText();
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
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void purchaseHistory(View view) {
        Intent intent = new Intent(this, PurchaseHistoryActivity.class);
        startActivity(intent);
    }

    public void edit(View view) {
        Intent intent = new Intent(this, EditAccountActivity.class);
        startActivity(intent);
    }


    public void changePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
