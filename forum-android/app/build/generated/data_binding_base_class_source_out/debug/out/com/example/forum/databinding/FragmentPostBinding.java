// Generated by view binder compiler. Do not edit!
package com.example.forum.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.forum.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPostBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final RecyclerView rvPlatePost;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshLayout;

  private FragmentPostBinding(@NonNull LinearLayout rootView, @NonNull RecyclerView rvPlatePost,
      @NonNull SwipeRefreshLayout swipeRefreshLayout) {
    this.rootView = rootView;
    this.rvPlatePost = rvPlatePost;
    this.swipeRefreshLayout = swipeRefreshLayout;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPostBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPostBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_post, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPostBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.rv_plate_post;
      RecyclerView rvPlatePost = ViewBindings.findChildViewById(rootView, id);
      if (rvPlatePost == null) {
        break missingId;
      }

      id = R.id.swipe_refresh_layout;
      SwipeRefreshLayout swipeRefreshLayout = ViewBindings.findChildViewById(rootView, id);
      if (swipeRefreshLayout == null) {
        break missingId;
      }

      return new FragmentPostBinding((LinearLayout) rootView, rvPlatePost, swipeRefreshLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
