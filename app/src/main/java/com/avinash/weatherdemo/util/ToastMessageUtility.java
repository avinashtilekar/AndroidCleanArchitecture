package com.avinash.weatherdemo.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avinash.weatherdemo.R;

public class ToastMessageUtility {
    private static ToastMessageUtility appUtility;
    private static Context mContext;

    private ToastMessageUtility(Context context) {
        mContext = context;
    }



    public static void showToastMessage(Activity activity, String message) {
        showToastMessage(activity, message, Toast.LENGTH_LONG);
    }

    public static void showToastMessage(Activity activity, String message, int duration) {
        AppUtility.hideSoftKeyboard(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_message,
                (ViewGroup) activity.findViewById(R.id.toast_layout_root));
        TextView tv_Message = (TextView) layout.findViewById(R.id.tvMessage);
        tv_Message.setText(message);
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

    public static void showErrorToastMessage(Activity activity, String message) {
        if (message != null && message.length() > 0) {
            showToastMessage(activity, message, Toast.LENGTH_LONG);
        }
    }

}

