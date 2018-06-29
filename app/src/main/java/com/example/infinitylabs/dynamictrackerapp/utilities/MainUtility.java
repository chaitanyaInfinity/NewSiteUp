package com.example.infinitylabs.dynamictrackerapp.utilities;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

/**
 * Created by nileshjarad on 28/12/16.
 */

public class MainUtility {


    /***
     *
     * @param view
     * @param context
     */
    public static String FORMAT = "format";
    public static String CONTENT = "content";

    public static void setCONTENT(String content) {
        CONTENT = content;
    }

    public static String getCONTENT() {
        return CONTENT;
    }


    public static void hideSoftkeyboard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String SHA1(String text) {
        MessageDigest md = null;


        try {
            md = MessageDigest.getInstance("SHA-1");

            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            byte[] sha1hash = md.digest();
            return convertToHex(sha1hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }


    /***
     * This function read the asset folder file  'features.properties'.
     * If file does not found it will throw {@link IOException}
     * @param key the key which you want to read
     * @param context context used to read file form asset folder
     * @return it will return the value for the key in properties file
     */
    private static String getProperty(String key, Context context) {
        Properties properties = new Properties();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("features.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);

    }

    /***
     * This function is used to check visibility of feature.
     * Function internally calls the {@link MainUtility#getProperty(String, Context)} function and parse the string into boolean
     * @param key the key which you want to read
     * @param context context used to read file form asset folder
     * @return boolean value for if feature is visible on not
     */
    public static boolean getFeatureVisibility(String key, Context context) {
        return Boolean.parseBoolean(getProperty(key, context));
    }


    public static String getSpaceTrimTextFromET(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getStringFromXml(Context context, int message) {
        return context.getResources().getString(message);
    }

    public static int getColorFromXml(Context context, int color) {
        return ContextCompat.getColor(context, color);
    }


    public static String getVersionName(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        return version;
    }

    public static void disableTabClick(TabLayout tabLayout) {
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    /**
     * This function check if string is empty the returns 0
     * If string number is not empty then convert string number to Double and returns it.
     *
     * @param number string number to convert into int
     * @return converted string to int
     */
    public static double getDoubleIfTextIsNullThenZero(String number) {
        return StringUtilities.isEmpty(number) ? 0 : Double.parseDouble(number);
    }

    /**
     * This function check if string is empty the returns 0
     * If string number is not empty then convert string number to Double and returns it.
     *
     * @param number string number to convert into int
     * @return converted string to int
     */
    public static int getIntIfTextIsNullThenZero(String number) {
        return StringUtilities.isEmpty(number) ? 0 : Integer.parseInt(number);
    }

    public static void showMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder(6);
        String tempChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 6; i++) {
//            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar.charAt(generator.nextInt(tempChar.length())));
        }
        return randomStringBuilder.toString();
    }

    public static String getBase64Image(Bitmap bm) {
        if (bm != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] byteArrayImage = baos.toByteArray();
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        }
        return "";
    }


    public static Bitmap getBitmapgetBitmapWithTimeStampWithTimeStamp(Bitmap imgToProcess) {
        Bitmap src = imgToProcess; // the original file is cuty.jpg i added in resources
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system

        Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
//        tPaint.setTextSize(15);
        tPaint.setColor(Color.BLUE);
//        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setStrokeWidth(10); // Text Size
        tPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        cs.drawBitmap(src, 0f, 0f, null);
        float height = tPaint.measureText("yY");
        cs.drawText(dateTime, 5f, height + 5f, tPaint);
        return dest;
    }

    public static Bitmap getScaledImage(Bitmap img) {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return Bitmap.createScaledBitmap(b, img.getWidth(), img.getHeight(), false);


//        int width = b.getWidth();
//        int height = b.getHeight();
//
//
//
//            float ratio = (float) width / 1200;
//            width = 1200;
//            height = (int) (height / ratio);
//
//            Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//
//            float ratioX = width / (float) b.getWidth();
//            float ratioY = height / (float) b.getHeight();
//            float middleX = width / 2.0f;
//            float middleY = height / 2.0f;
//
//            Matrix scaleMatrix = new Matrix();
//            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
//
//            Canvas canvas = new Canvas(scaledBitmap);
//            canvas.setMatrix(scaleMatrix);
//            canvas.drawBitmap(b, middleX - b.getWidth() / 2, middleY - b.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
//
//            return scaledBitmap;
//
//
//
////        return Bitmap.createScaledBitmap(img, width, height, true);
    }




}
