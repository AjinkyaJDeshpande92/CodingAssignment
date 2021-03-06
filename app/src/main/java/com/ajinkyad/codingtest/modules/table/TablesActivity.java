package com.ajinkyad.codingtest.modules.table;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajinkyad.codingtest.R;
import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.modules.common.FragmentInstanceHandler;
import com.ajinkyad.codingtest.modules.common.NetworkListener;
import com.ajinkyad.codingtest.modules.common.NetworkStateChangeMonitor;
import com.ajinkyad.codingtest.modules.table.selection.TablesFragment;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TablesActivity extends AppCompatActivity implements FragmentInstanceHandler, NetworkListener {

    @BindView(R.id.linearLayoutInternetStatusColor)
    LinearLayout linearLayoutInternetStatusColor;
    @BindView(R.id.textViewInternetError)
    TextView textViewInternetError;
    @BindView(R.id.expLayNetworkConnection)
    ExpandableLayout expLayNetworkConnection;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    NetworkStateChangeMonitor networkStateChangeMonitor;
    Unbinder unbinder;

    public CustomerDetailsResponse customerDetailsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        if (getIntent().getExtras().containsKey("data")) {
            customerDetailsResponse = (CustomerDetailsResponse) getIntent().getExtras().get("data");
        }
        initialiseComponents();
        changeFragment(new TablesFragment());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void changeFragment(Fragment currentFragment) {
        try {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fragmentHolder, currentFragment, "" + currentFragment);
            fragmentTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initialiseComponents() {
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setTitle(getString(R.string.sel_table));
            toolbar.setSubtitle(String.format("for %s %s", customerDetailsResponse.getCustomerLastName(), customerDetailsResponse.getCustomerFirstName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        networkStateChangeMonitor = new NetworkStateChangeMonitor();
        networkStateChangeMonitor.addListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.registerReceiver(networkStateChangeMonitor, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(networkStateChangeMonitor);
    }

    @Override
    public void onConnecting() {
        linearLayoutInternetStatusColor.setBackgroundColor(this.getResources().getColor(R.color.colorYellow));
        textViewInternetError.setText(this.getResources().getString(R.string.connecting));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayoutInternetStatusColor.setBackgroundColor(getApplication().getResources().getColor(R.color.colorGreen));
                textViewInternetError.setText(getApplication().getResources().getString(R.string.connected));
            }
        }, 500);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                expLayNetworkConnection.collapse(true);
            }
        }, 1000);
    }

    @Override
    public void onConnected() {
        linearLayoutInternetStatusColor.setBackgroundColor(this.getResources().getColor(R.color.colorGreen));
        textViewInternetError.setText(this.getResources().getString(R.string.connected));
        expLayNetworkConnection.collapse(true);

    }

    @Override
    public void onDisconnected() {
        linearLayoutInternetStatusColor.setBackgroundColor(this.getResources().getColor(R.color.colorRed));
        textViewInternetError.setText(this.getResources().getString(R.string.no_internet_connection));
        expLayNetworkConnection.expand(true);
    }
}
