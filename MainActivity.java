package com.freesoft.triota;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener, View.OnDragListener{

    private Presenter presenter;
    private ImageView img_playerGreen;
    private ImageView img_playerRed;
    private View[][] views = new View[3][3];
    private View fromV, toV;
    private int fromI, fromJ, toI, toJ;
    private Player currentPlayer;
    private Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);//initiate

        img_playerRed = (ImageView) findViewById(R.id.img_playerRed);
        img_playerGreen = (ImageView) findViewById(R.id.img_playerGreen);
        views[0][0] = (ImageView) findViewById(R.id.img_11);
        views[0][1] = (ImageView) findViewById(R.id.img_12);
        views[0][2] = (ImageView) findViewById(R.id.img_13);
        views[1][0] = (ImageView) findViewById(R.id.img_21);
        views[1][1] = (ImageView) findViewById(R.id.img_22);
        views[1][2] = (ImageView) findViewById(R.id.img_23);
        views[2][0] = (ImageView) findViewById(R.id.img_31);
        views[2][1] = (ImageView) findViewById(R.id.img_32);
        views[2][2] = (ImageView) findViewById(R.id.img_33);

        //currentPlayer = this.currentPlayer;
        presenter = new Presenter(this);
        for (int i = 0; i < views.length; i++) {
            for (int j = 0; j < views.length; j++) {
                views[i][j].setOnTouchListener(this);
                views[i][j].setOnDragListener(this);
            }
        }
        simpleSwitch.setOnTouchListener(this);

        //-----------------notuse-----------------------------

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Restart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                presenter.FabPressed();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //------ONTOUCHONDRAG-----------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v == simpleSwitch) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                presenter.switchChanged();
                simpleSwitch.setChecked(!presenter.getSwitchStatus());
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                data = ClipData.newPlainText("", "");
            }
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            fromV = (ImageView) v;
            String p = (String) fromV.getTag();
            String[] parts = p.split(",");
            fromI = Integer.parseInt(parts[0]);
            fromJ = Integer.parseInt(parts[1]);
            return true;

        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                //no action
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //no action
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                //no action
                break;
            case DragEvent.ACTION_DROP:

                toV = (ImageView) v;
                String p = (String) toV.getTag();
                String[] parts = p.split(",");
                toI = Integer.parseInt(parts[0]);
                toJ = Integer.parseInt(parts[1]);
                presenter.checkMovement(fromI, fromJ, toI, toJ);

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //no action
                break;
        }
        return true;
    }

    public void updateUI(Player currentPlayer, int fromI, int fromJ, int toI, int toJ) {

        ((ImageView) fromV).setImageResource(R.drawable.player_none);
        if (currentPlayer == Player.RED) {
            ((ImageView) toV).setImageResource(R.drawable.ic_button_blank_red);
            img_playerRed.setVisibility(View.INVISIBLE);
            img_playerGreen.setVisibility(View.VISIBLE);
        } else if (currentPlayer == Player.GREEN) {
            ((ImageView) toV).setImageResource(R.drawable.ic_button_blank_green);
            img_playerRed.setVisibility(View.VISIBLE);
            img_playerGreen.setVisibility(View.INVISIBLE);
        }

    }

    public void reinitialize(Player currentPlayer) {

        simpleSwitch.setChecked(presenter.getSwitchStatus());
        this.currentPlayer = currentPlayer;
        if(this.currentPlayer == Player.GREEN) {
            img_playerRed.setVisibility(View.INVISIBLE);
            img_playerGreen.setVisibility(View.VISIBLE);
        }else{
            img_playerRed.setVisibility(View.VISIBLE);
            img_playerGreen.setVisibility(View.INVISIBLE);
        }
        ((ImageView) views[0][0]).setImageResource(R.drawable.ic_button_blank_green);
        ((ImageView) views[0][1]).setImageResource(R.drawable.ic_button_blank_green);
        ((ImageView) views[0][2]).setImageResource(R.drawable.ic_button_blank_green);
        ((ImageView) views[1][0]).setImageResource(R.drawable.player_none);
        ((ImageView) views[1][1]).setImageResource(R.drawable.player_none);
        ((ImageView) views[1][2]).setImageResource(R.drawable.player_none);
        ((ImageView) views[2][0]).setImageResource(R.drawable.ic_button_blank_red);
        ((ImageView) views[2][1]).setImageResource(R.drawable.ic_button_blank_red);
        ((ImageView) views[2][2]).setImageResource(R.drawable.ic_button_blank_red);


    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void openDialog(Player currentP) {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void dialogClosed() {
        presenter.afterDialogClosed();
    }

}
