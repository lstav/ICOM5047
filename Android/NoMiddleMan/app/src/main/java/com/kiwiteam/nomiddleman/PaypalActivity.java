package com.kiwiteam.nomiddleman;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class PaypalActivity extends ActionBarActivity {

    private DatabaseConnection conn;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        conn = (DatabaseConnection) getApplicationContext();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
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
            case R.id.signout:
                conn.signout();
                recreate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void checkout(View view) {
        EditText pEmail = (EditText) findViewById(R.id.payPalEmail);
        email = pEmail.getText().toString();

        EditText pPass = (EditText) findViewById(R.id.payPalPass);
        password = pPass.getText().toString();

        ArrayList<ShoppingItem> shoppingCart = conn.getShoppingCart(0);

        for(int i =0; i<shoppingCart.size(); i++) {
            conn.addToHistory(shoppingCart.get(i).getDate(),shoppingCart.get(i).getTime(), shoppingCart.get(i).getTourID(),
                    shoppingCart.get(i).getQuantity(), conn.getTourInformation(shoppingCart.get(i).getTourID()));
        }

        conn.clearShoppingCart();

        Intent intent = new Intent(this, PurchaseHistoryActivity.class);
        startActivity(intent);

    }

    public void cancel(View view) {
        finish();
    }
}
