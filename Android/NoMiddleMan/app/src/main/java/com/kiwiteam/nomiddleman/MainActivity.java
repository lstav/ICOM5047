package com.kiwiteam.nomiddleman;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public Intent intent;
    private Menu menu;
    public int index = -1;


    DatabaseConnection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMainView();
        initSearchView();

    }

    private void initMainView() {
        conn = (DatabaseConnection)getApplicationContext();

        if(conn.isLogged()) {
            // Login
            findViewById(R.id.login_description).setVisibility(View.GONE);
            findViewById(R.id.login_button).setVisibility(View.GONE);

            // Register
            findViewById(R.id.register_description).setVisibility(View.GONE);
            findViewById(R.id.register_button).setVisibility(View.GONE);

            // Account
            findViewById(R.id.account_button).setVisibility(View.VISIBLE);

            //Categories
            findViewById(R.id.categories_button).setVisibility(View.VISIBLE);
            findViewById(R.id.categories_row).setVisibility(View.GONE);
        } else {
            // Account
            findViewById(R.id.account_button).setVisibility(View.GONE);

            //Categories
            findViewById(R.id.categories_button).setVisibility(View.GONE);
        }
    }

    private void initSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
    }

    protected void onResume() {
        super.onResume();

        if(conn.isLogged()) {
            this.index = conn.getIndex();
            // Login
            findViewById(R.id.login_description).setVisibility(View.GONE);
            findViewById(R.id.login_button).setVisibility(View.GONE);

            // Register
            findViewById(R.id.register_description).setVisibility(View.GONE);
            findViewById(R.id.register_button).setVisibility(View.GONE);

            // Account
            findViewById(R.id.account_button).setVisibility(View.VISIBLE);

            //Categories
            findViewById(R.id.categories_button).setVisibility(View.VISIBLE);
            findViewById(R.id.categories_row).setVisibility(View.GONE);

            menu.findItem(R.id.account).setVisible(true);
            menu.findItem(R.id.signout).setVisible(true);
        }
    }

    private void getDrawablesList() {
        String[] tourName = conn.getTourNames();

        /*drawables = new ArrayList<Drawable>();
        for (int i=0;i<2;i++) {
            drawables.add(getResources().getDrawable(getResources().getIdentifier(tourName[i],"drawable",getPackageName())));
            //drawables.add(getResources().getDrawable(R.drawable.img1));
        }*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        if (conn.isLogged())
        {
            menu.findItem(R.id.account).setVisible(true);
            menu.findItem(R.id.signout).setVisible(true);
        } else {
            menu.findItem(R.id.account).setVisible(false);
            menu.findItem(R.id.signout).setVisible(false);
        }

        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_search) {
            return true;
        }*/

        if (item.getItemId() == R.id.action_cart) {
            Toast.makeText(this, "Shopping Cart", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(item.getItemId() == R.id.account) {
            account();
        }

        if (item.getItemId() == R.id.signout) {
            conn.signout();
            recreate();
        }

        return super.onOptionsItemSelected(item);
    }

    public void account() {
        intent = new Intent(this, AccountActivity.class);
        intent.putExtra("Index", conn.getIndex());
        startActivity(intent);
    }

    public void account(View view) {
        intent = new Intent(this, AccountActivity.class);
        intent.putExtra("Index", conn.getIndex());
        startActivity(intent);
    }

    public void login(View view) {
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void searchCat(View view) {
        intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }


    public void itemSelected(View view) {
        intent = new Intent(this, TourPageActivity.class);
        startActivity(intent);

        //Toast.makeText(this, "Ola Surf", Toast.LENGTH_SHORT).show();
    }
}
