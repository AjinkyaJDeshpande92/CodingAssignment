package com.ajinkyad.codingtest.application;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.ajinkyad.codingtest.utilities.DataBus;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.otto.ThreadEnforcer;

public class CodingTestApplication extends Application {

    public static final String TAG = CodingTestApplication.class.getSimpleName();
    private static CodingTestApplication mInstance;
    private final int MAX_REQUEST_TIMEOUT_MS = 120000;
    private RequestQueue mRequestQueue;
    private static DataBus eventBus;

    public static synchronized CodingTestApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(
                new DefaultRetryPolicy(MAX_REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);

    }


    public static DataBus getBusInstance() {
        if (eventBus == null) {
            eventBus = new DataBus(ThreadEnforcer.ANY);
        }
        return eventBus;
    }


}
