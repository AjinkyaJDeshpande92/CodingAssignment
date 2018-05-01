package com.ajinkyad.codingtest.utilities;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;

import com.ajinkyad.codingtest.modules.common.DialogListener;

public class Utilities {

    private static AlertDialog.Builder alertDialogBuilder;
    private static AlertDialog alertDialog;

    public static String getFormattedName(String firstName, String lastName) {

        if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty())) {
            return "Name not available";

        } else if (firstName == null || firstName.trim().isEmpty()) {
            return lastName;

        } else if (lastName == null || lastName.trim().isEmpty()) {
            return firstName;

        } else {
            return String.format("%s, %s", lastName, firstName);
        }

    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    public static void setRepeatingAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
            alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), 900000, alarmIntent);
        }

    }

    /**
     * This function is used to display the Alert Dialog Box (Multiple Option) to the User.
     * <p>
     * This function also handles the operations users can perform on the Alert Dialog.
     *
     * @param context        - The Context
     * @param dialogID       - The Unique Dialog ID for current screen.
     * @param dialogListener - The Dialog Event Listener
     * @param params         - The Alert Dialog Display Parameters (Heading,Detail Message,Action Texts)
     */
    public static void showAlertDialogMultipleOptions(Context context, final int dialogID, final DialogListener dialogListener, String... params) {


        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(params[0]);
        alertDialogBuilder.setMessage(params[1]);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(params[2], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onPositiveAction(dialogID, null);
                }
            }
        });
        alertDialogBuilder.setNegativeButton(params[3], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onNegativeAction(dialogID, null);
                }
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    /**
     * This function is used to display the Alert Dialog Box (Single Option) to the User.
     * <p>
     * This function also handles the operations users can perform on the Alert Dialog.
     *
     * @param context        - The Context
     * @param dialogID       - The Unique Dialog ID for current screen.
     * @param dialogListener - The Dialog Event Listener
     * @param params         - The Alert Dialog Display Parameters (Heading,Detail Message,Action Text)
     */
    public static void showAlertDialogSingleOption(Context context, final int dialogID, final DialogListener dialogListener, String... params) {
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(params[0]);
        alertDialogBuilder.setMessage(params[1]);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(params[2], new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onPositiveAction(dialogID, null);
                }
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
