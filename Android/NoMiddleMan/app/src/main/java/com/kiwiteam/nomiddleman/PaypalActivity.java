package com.kiwiteam.nomiddleman;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    public void checkout(View view) {
        EditText pEmail = (EditText) findViewById(R.id.payPalEmail);
        email = pEmail.getText().toString();

        EditText pPass = (EditText) findViewById(R.id.payPalPass);
        password = pPass.getText().toString();

        /*if(conn.payPalLogin(email, password)) {
            ArrayList<ShoppingItem> shoppingCart = conn.getShoppingCart(0);

            System.out.println("Shopping Size " +shoppingCart.size());
            for(int i =0; i<shoppingCart.size(); i++) {
                conn.addToHistory(shoppingCart.get(i).getDate(),shoppingCart.get(i).getTime(),
                        conn.getSessionID(shoppingCart.get(i).getDate(), shoppingCart.get(i).getTime(), shoppingCart.get(i).getTourID()),
                        shoppingCart.get(i).getQuantity(), conn.getTourInformation(shoppingCart.get(i).getTourID()));
            }

            conn.clearShoppingCart();

            Intent intent = new Intent(this, PurchaseHistoryActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
        }*/

    }

    public void cancel(View view) {
        finish();
    }
}
