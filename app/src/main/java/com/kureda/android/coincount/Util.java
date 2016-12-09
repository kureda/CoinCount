package com.kureda.android.coincount;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Serg on 12/9/2016.
 */

public class Util {

    public static void notNull(Object... args) {
        for (Object arg : args) {
            if(arg==null) throw new NullPointerException("Argument should not be null.");
        }
    }

    public static void addFragment (@NonNull FragmentManager fragmentManager,
                                    @NonNull Fragment fragment, int frameId) {
        notNull(fragmentManager, fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

}
