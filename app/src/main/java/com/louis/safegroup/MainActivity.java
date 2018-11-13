package com.louis.safegroup;

import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ThreeButtons mainFragment;
    private ProblemQuad problemQuad;
    private DangerQuad dangerQuad;
    private SafeQuad safeQuad;
    private GroupQuad groupQuad;
    private NotificationRecap notificationRecap;
    //private SectionStatePageAdapter mSectionStatePageAdapter;
    private int localState;
    private int localStatePrecision;
    private int localGroup;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.configureAndShowMainFragment();
    }

    private void configureAndShowMainFragment(){

        mainFragment = (ThreeButtons) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        problemQuad= new ProblemQuad();
        dangerQuad = new DangerQuad();
        safeQuad = new SafeQuad();
        groupQuad = new GroupQuad();
        notificationRecap = new NotificationRecap();
        if (mainFragment == null) {
            mainFragment = new ThreeButtons();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    public void setState(int state){ localState = state; }

    public void setStatePrecision(int statePresision){
        localStatePrecision=statePresision;
    }

    public void setGroup(int group){localGroup=group;}

    public void setLocalStateColor(){
        ConstraintLayout rl = (ConstraintLayout) findViewById(R.id.container);
        int[] stateColor = getResources().getIntArray(R.array.state_color);
        rl.setBackgroundColor(stateColor[localState]);
    }

    public void setFragment(int fragId){
        switch (fragId) {
            case 0:
                    getSupportFragmentManager().popBackStack("begin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case 1:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,dangerQuad).addToBackStack("begin").commit();
                break;
            case 2:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,problemQuad).addToBackStack("begin").commit();
                break;
            case 3:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,safeQuad).addToBackStack("begin").commit();
                break;
            case 4:  getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,groupQuad).addToBackStack(null).commit();
                break;
            case 5:
                getSupportFragmentManager().popBackStack("begin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,notificationRecap).addToBackStack("begin").commit();
                break;
            default:
                break;
        }
    }


    public void setNotificationDetail(){
        Resources res = getResources();
        String[] state = res.getStringArray(R.array.state);
        String[] preciseState = res.getStringArray(R.array.precise_state);
        String[] group = res.getStringArray(R.array.group);
        String detail = String.format(res.getString(R.string.notification_detail_message),state[localState], preciseState[localStatePrecision],group[localGroup]);
        TextView detailText = (TextView) findViewById(R.id.detail_notification);
        detailText.setText(detail);
    }


    public void chooseGroup(){
        //TODO -- Choisir les groupes à partir de la base de donnée
    }
}
