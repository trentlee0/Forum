// Generated by view binder compiler. Do not edit!
package com.example.forum.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.forum.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FooterBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout endLayout;

  @NonNull
  public final LinearLayout errorLayout;

  @NonNull
  public final LinearLayout loadingLayout;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView tvTip;

  private FooterBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout endLayout,
      @NonNull LinearLayout errorLayout, @NonNull LinearLayout loadingLayout,
      @NonNull ProgressBar progressBar, @NonNull TextView tvTip) {
    this.rootView = rootView;
    this.endLayout = endLayout;
    this.errorLayout = errorLayout;
    this.loadingLayout = loadingLayout;
    this.progressBar = progressBar;
    this.tvTip = tvTip;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FooterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FooterBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.footer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FooterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.end_layout;
      LinearLayout endLayout = ViewBindings.findChildViewById(rootView, id);
      if (endLayout == null) {
        break missingId;
      }

      id = R.id.error_layout;
      LinearLayout errorLayout = ViewBindings.findChildViewById(rootView, id);
      if (errorLayout == null) {
        break missingId;
      }

      id = R.id.loading_layout;
      LinearLayout loadingLayout = ViewBindings.findChildViewById(rootView, id);
      if (loadingLayout == null) {
        break missingId;
      }

      id = R.id.progress_bar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.tv_tip;
      TextView tvTip = ViewBindings.findChildViewById(rootView, id);
      if (tvTip == null) {
        break missingId;
      }

      return new FooterBinding((LinearLayout) rootView, endLayout, errorLayout, loadingLayout,
          progressBar, tvTip);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}