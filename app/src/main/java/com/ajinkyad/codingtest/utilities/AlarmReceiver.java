package com.ajinkyad.codingtest.utilities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ajinkyad.codingtest.data.DatabaseHelper;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //This clears all the reservations
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.deleteReservations();
    }
}
