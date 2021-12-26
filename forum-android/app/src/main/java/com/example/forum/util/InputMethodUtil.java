package com.example.forum.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtil {
    public static void hide(View view) {
        InputMethodManager manager = ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void show(View view) {
        InputMethodManager manager = ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            manager.showSoftInput(view, 0);
        }
    }
}
