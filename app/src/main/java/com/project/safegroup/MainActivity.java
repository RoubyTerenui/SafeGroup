package com.project.safegroup;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.safegroup.NotificationRecap;
import com.project.safegroup.ProblemQuad;
import com.project.safegroup.R;
import com.project.safegroup.SafeQuad;
import com.project.safegroup.SectionStatePageAdapter;
import com.project.safegroup.ThreeButtons;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private SectionStatePageAdapter mSectionStatePageAdapter;
    private int localState;
    private int localStatePrecision;
    private int localGroup;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    setViewPager(0);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
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
        mSectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.fragmentContainer);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
    SectionStatePageAdapter adapter = new SectionStatePageAdapter(getSupportFragmentManager());
    adapter.addFragment(new ThreeButtons(),"com.louis.safegroup.ThreeButtons");
    adapter.addFragment(new DangerQuad(),"com.louis.safegroup.DangerQuad");
    adapter.addFragment(new ProblemQuad(),"com.louis.safegroup.ProblemQuad");
    adapter.addFragment(new SafeQuad(),"com.louis.safegroup.SafeQuad");
    adapter.addFragment(new GroupQuad(),"com.louis.safegroup.GroupQuad");
    adapter.addFragment(new NotificationRecap(),"com.louis.safegroup.NotificationRecap");
    viewPager.setAdapter(adapter);
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


    public void setViewPager(int fragId){
        mViewPager.setCurrentItem((fragId));
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
