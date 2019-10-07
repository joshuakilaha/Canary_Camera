package com.example.myapplication;

import android.hardware.Camera;

import java.util.Comparator;

public class PictureSizeComparator implements Comparator<Camera.Size> {
    public int compare(Camera.Size a, Camera.Size b) {
        return (b.height * b.width) - (a.height * a.width);
    }
}
