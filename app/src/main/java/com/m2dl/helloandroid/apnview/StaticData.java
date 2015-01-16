package com.m2dl.helloandroid.apnview;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
}
