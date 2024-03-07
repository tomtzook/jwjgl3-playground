package com.github.tomtzook.util;

import java.util.List;

public class Unboxer {

    public static float[] unboxFloat(List<Float> list) {
        Float[] arr = list.toArray(new Float[0]);
        return unboxFloat(arr);
    }

    public static float[] unboxFloat(Float[] arr) {
        float[] arr2 = new float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }

        return arr2;
    }

    public static int[] unboxInt(List<Integer> list) {
        Integer[] arr = list.toArray(new Integer[0]);
        return unboxInt(arr);
    }

    public static int[] unboxInt(Integer[] arr) {
        int[] arr2 = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }

        return arr2;
    }
}
