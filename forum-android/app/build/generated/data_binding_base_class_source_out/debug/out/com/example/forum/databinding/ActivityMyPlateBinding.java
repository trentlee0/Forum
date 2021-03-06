// Generated by view binder compiler. Do not edit!
package com.example.forum.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.forum.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMyPlateBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ListView lvPlate;

  @NonNull
  public final Toolbar toolbarHead;

  private ActivityMyPlateBinding(@NonNull LinearLayout rootView, @NonNull ListView lvPlate,
      @NonNull Toolbar toolbarHead) {
    this.rootView = rootView;
    this.lvPlate = lvPlate;
    this.toolbarHead = toolbarHead;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMyPlateBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMyPlateBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_my_plate, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMyPlateBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.lv_plate;
      ListView lvPlate = ViewBindings.findChildViewById(rootView, id);
      if (lvPlate == null) {
        break missingId;
      }

      id = R.id.toolbar_head;
      Toolbar toolbarHead = ViewBindings.findChildViewById(rootView, id);
      if (toolbarHead == null) {
        break missingId;
      }

      return new ActivityMyPlateBinding((LinearLayout) rootView, lvPlate, toolbarHead);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
