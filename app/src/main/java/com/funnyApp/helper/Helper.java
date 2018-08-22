package com.funnyApp.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hussein on 6/4/17.
 */

public class Helper {

    public static void clearFragmentStack(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }

    //Get Bitmap and save it
    public static File saveBitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + "testimage.jpg");
        file.createNewFile();
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(bytes.toByteArray());
        fo.close();
        return file;
    }
    public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }
    public static int getJsonData(String json, String object, int i) {
        // i variable is notthing just fill it with zero when u call it

        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getInt(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getJsonData(String json, String object) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return " ";
    }
}
