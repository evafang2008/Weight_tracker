package fang.weighttracker;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import fang.weighttracker.model.Weight;
import fang.weighttracker.model.WeightLab;

/**
 * @author Fang Fang
 * Date: 2016/5/13
 *
 */

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //clear all temp weights.
        WeightLab.get(getApplication()).delete_temp_Weight();

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    case R.id.overview:
                       // Toast.makeText(getApplicationContext(),"Summary Selected",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.history:
                        //Toast.makeText(getApplicationContext(),"History Selected",Toast.LENGTH_SHORT).show();
                        Intent history_intent = new Intent(getApplicationContext(), History.class);
                        startActivity(history_intent);


                        return true;
                    case R.id.setting:
                        //Toast.makeText(getApplicationContext(),"Setting Selected",Toast.LENGTH_SHORT).show();
                        Intent setting_intent = new Intent(getApplicationContext(), Settings.class);
                        startActivity(setting_intent);
                        return true;

                    case R.id.chart:
                        Intent chart_intent = new Intent(getApplicationContext(), Chart.class);
                        startActivity(chart_intent);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.bn_new_weight:
                Weight weight = new Weight();
               WeightLab.get(this).addWeight(weight);
                Intent intent = WeightPager
                        .newIntent(this, weight.getId());
                startActivity(intent);
                return true;
            case R.id.bn_chart:
                WeightLab weightLab = WeightLab.get(getApplication());
                List<Weight> weights = weightLab.getWeights();
                if(weights.size() > 0){
                    Intent chart_intent = new Intent(this, Chart.class);
                    startActivity(chart_intent);
                }else{
                    Toast.makeText(getApplication(),
                            "You do not have any weight records now. Please add your first weight entry. Start now!",
                            Toast.LENGTH_LONG)
                            .show();
                }
                return true;
            case R.id.bn_history:
                Intent his_intent = new Intent(this, History.class);
                startActivity(his_intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
