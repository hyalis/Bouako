package com.m2dl.helloandroid.apnview.utils;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import java.util.ArrayList;

/**
 * Used to transfer data between activities
 */
public class StaticData {
    public static Bitmap imgBitmap;
    public static String login;
    public static Typeface myTypeface;
    public static ArrayList<String> imageTypesAndSousTypes = new ArrayList<String>();
    public static boolean switchTypeIsSelected = false;
    public static boolean switchSousTypeIsSelected = false;
    public static String lastLocation;

}
