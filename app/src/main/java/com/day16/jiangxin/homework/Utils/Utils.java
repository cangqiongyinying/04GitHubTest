package com.day16.jiangxin.homework.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by my on 2016/7/28.
 */
public class Utils {
    public static Bitmap getBitmapFromUrl(String url) {
        Bitmap bm = null;
        InputStream is = null;
        try {
            URL urlRequest = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlRequest
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5 * 1000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                bm = BitmapFactory.decodeStream(is);
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return bm;
    }

    public static InputStream getStreamFromUrl(String url) {
        InputStream is = null;
        try {
            URL urlRequest = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlRequest
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5 * 1000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return is;
    }

    public static String getTextFromUrl(String url) {
        String result = "";
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        try {
            URL urlReq = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlReq
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5 * 1000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int readLen = 0;
                while ((readLen = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, readLen);
                }
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        result = baos.toString();
        return result;
    }
}
