package com.abt.recycler.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.abt.recycler.LiveApplication;
import com.abt.recycler.R;

/**
 * @描述：     @DiscreteScrollViewOptions
 * @作者：     @黄卫旗
 * @创建时间： @2018-03-31
 */
public class DiscreteScrollViewOptions {

    private static DiscreteScrollViewOptions instance;
    private final String KEY_TRANSITION_TIME;

    public static void init(Context context) {
        instance = new DiscreteScrollViewOptions(context);
    }

    private DiscreteScrollViewOptions(Context context) {
        KEY_TRANSITION_TIME = context.getString(R.string.pref_key_transition_time);
    }

    public static int getTransitionTime() {
        return defaultPrefs().getInt(instance.KEY_TRANSITION_TIME, 100);
    }

    private static SharedPreferences defaultPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(LiveApplication.getInstance());
    }


}
