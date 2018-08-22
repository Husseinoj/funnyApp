package com.funnyApp.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.funnyApp.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Hussein on 12/7/2016.
 * I use this class to manage view
 */
public class UIHelper {

    //use NextFocuse  Method to jump to next EditText authomatically at a specific size
    // to use it, first set mEdSize property and then use the method
    private static EditText mEditText1;
    private static EditText mEditText2;
    private static int mEdSize;
    private static int mDialogState = Tags.RESULT_NONE;
    private static Dialog mDialog;

    public static void nextFocous(int et1, int et2, int size, View view) {
        mEdSize = size;

        mEditText1 = (EditText) view.findViewById(et1);
        mEditText2 = (EditText) view.findViewById(et2);

        mEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int char_count = s.length();
                Log.d("char1", String.valueOf(char_count));
                if (char_count == mEdSize) {

                    mEditText2.requestFocus();

                    Log.d("char1", "if executed");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void nextFocous(int et1, int et2, int size, Dialog dialog) {
        mEdSize = size;

        mEditText1 = (EditText) dialog.findViewById(et1);
        mEditText2 = (EditText) dialog.findViewById(et2);

        mEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("textLogIs", String.valueOf(mEdSize));
                if (mEditText1.getText().toString().length() == mEdSize)
                    mEditText2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void nextFocous(int et1, int et2, int size, Activity activity) {
        mEdSize = size;

        mEditText1 = (EditText) activity.findViewById(et1);
        mEditText2 = (EditText) activity.findViewById(et2);

        mEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("textLogIs", String.valueOf(mEdSize));
                if (s.length() == mEdSize)
                    mEditText2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //check whether the GPS is enabled or not
    public static boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    //Take list of EditText and check whether they are empty or not
    public static int checkEmptyEd(ArrayList<Integer> Controls, Activity activity) {
        int status = 0;
        for (int control : Controls) {
            EditText text = (EditText) activity.findViewById(control);
            String txtControl = text.getText().toString().trim();
            if (txtControl.isEmpty()) {
                status = control;
                return status;
            }
        }
        return status;
    }
    //checking the empty field in dialog
    public static int checkEmptyEd(ArrayList<Integer> Controls, Dialog dialog) {
        int status = 0;
        for (int control : Controls) {
            TextView text = (TextView) dialog.findViewById(control);
            String txtControl = text.getText().toString().trim();
            if (txtControl.isEmpty()) {
                status = control;
                return status;
            }
        }
        return status;
    }

    public static boolean compareTwoEd(Activity activity, int tv1, int tv2,
                                       String Message, int drawable, Dialog dialog) {
        TextView text1, text2;
        if (activity != null) {
            text1 = (TextView) activity.findViewById(tv1);
            text2 = (TextView) activity.findViewById(tv2);
        } else {
            text1 = (TextView) dialog.findViewById(tv1);
            text2 = (TextView) dialog.findViewById(tv2);
        }
        if (text1.getText().toString().trim().equals(text2.getText().toString().trim()))
            return true;
        else {

            simpleDialogBox(Message, activity, drawable, "");

            return false;

        }
    }

    public static boolean compareTwoEd(Activity activity, int tv1, int tv2, int textError, String msgError, Dialog dialog) {
        EditText text1, text2;

        text1 = (EditText) dialog.findViewById(tv1);
        text2 = (EditText) dialog.findViewById(tv2);

        if (text1.getText().toString().trim().equals(text2.getText().toString().trim()))
            return true;
        else {

            setTvText(dialog, textError, msgError);
            return false;

        }
    }

    public static boolean checktextSize(Dialog dialog, int control, String msgError, int textError) {
        TextView text1 = (TextView) dialog.findViewById(control);
        String length = text1.getText().toString();
        if (text1.getText().length() <= 6) {

            setTvText(dialog, textError, msgError);

            return false;
        } else

            return true;
    }

    public static void setTvText(Activity activity, int id, String text) {
        TextView tv = (TextView) activity.findViewById(id);
        tv.setText(text);
    }

    public static void setTvText(Dialog dialog, int id, String text) {
        TextView tv = (TextView) dialog.findViewById(id);
        tv.setText(text);
    }

    public static void setTvText(int id, String text, View v) {
        TextView tv = (TextView) v.findViewById(id);
        tv.setText(text);

    }
    public static String getEditText(Dialog dialog, int id) {
        EditText editText = (EditText) dialog.findViewById(id);
        return  editText.getText().toString();
    }
    public static boolean checktextSize(Activity activity, int tv1, String Message, int drawable) {
        EditText text1 = (EditText) activity.findViewById(tv1);

        if (text1.length() <= 6) {

            simpleDialogBox(Message, activity, drawable, "");
            return false;
        }
        return true;
    }
    //simple dialog to ask question
    public static void simpleDialogBox(String message, Activity activity, int drawable, String msTitle) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        dialog.setContentView(drawable);
        try {
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.draw_all_round_corner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getWindow().setLayout((int) (Helper.getScreenWidth(activity) * .9), ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView dialogText = (TextView) dialog.findViewById(R.id.tvDescription);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        if (msTitle.isEmpty())
            msTitle = "اخطار";
        dialogTitle.setText(msTitle);
        dialogText.setText(message);

        Button btnYes = (Button) dialog.findViewById(R.id.btnyes);

        // if draw_button is clicked, close the custom mDialog
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    public static boolean validateEmail(String EmailAddress) {
        boolean check = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(EmailAddress).matches();
        if (!check)
            return false;
        else
            return true;
    }

    //checking screen size for some of small and larg phone
    public static boolean checkScreenSize(Activity activity, int screen) {
        if ((activity.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == screen)
            return true;
        return false;
    }



    public static int AskQuestion(String title, String msg, Activity activity) {

        mDialog = new Dialog(activity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.setContentView(R.layout.dialog_ask_v1);
        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.draw_radius_nocolor);
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.90);

        TextView tvTitle = (TextView) mDialog.findViewById(R.id.tvTitle);
        TextView tvMessage = (TextView) mDialog.findViewById(R.id.tvDescription);
        tvTitle.setText(title);
        tvMessage.setText(msg);
        mDialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btnno = (Button) mDialog.findViewById(R.id.btnno);
        Button btnyes = (Button) mDialog.findViewById(R.id.btnyes);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogState = Tags.RESULT_TRUE;
                mDialog.dismiss();
            }
        });
        // if draw_button is clicked, close the custom mDialog
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogState = Tags.RESULT_FALSE;
                mDialog.dismiss();

            }
        });

        mDialog.show();
// custom mDialog
        return mDialogState;
    }



    public static void unLockScreen(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void lockScreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }


}
