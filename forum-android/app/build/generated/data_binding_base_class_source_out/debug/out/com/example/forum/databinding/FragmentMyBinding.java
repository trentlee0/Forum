// Generated by view binder compiler. Do not edit!
package com.example.forum.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.forum.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMyBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView btnMyPlate;

  @NonNull
  public final TextView btnMyPost;

  @NonNull
  public final TextView btnToLogin;

  @NonNull
  public final TextView btnToSetting;

  @NonNull
  public final ImageView ivAvatar;

  @NonNull
  public final LinearLayout llUserInfo;

  @NonNull
  public final TextView tvBirthday;

  @NonNull
  public final TextView tvGender;

  @NonNull
  public final TextView tvGrade;

  @NonNull
  public final TextView tvUsername;

  private FragmentMyBinding(@NonNull LinearLayout rootView, @NonNull TextView btnMyPlate,
      @NonNull TextView btnMyPost, @NonNull TextView btnToLogin, @NonNull TextView btnToSetting,
      @NonNull ImageView ivAvatar, @NonNull LinearLayout llUserInfo, @NonNull TextView tvBirthday,
      @NonNull TextView tvGender, @NonNull TextView tvGrade, @NonNull TextView tvUsername) {
    this.rootView = rootView;
    this.btnMyPlate = btnMyPlate;
    this.btnMyPost = btnMyPost;
    this.btnToLogin = btnToLogin;
    this.btnToSetting = btnToSetting;
    this.ivAvatar = ivAvatar;
    this.llUserInfo = llUserInfo;
    this.tvBirthday = tvBirthday;
    this.tvGender = tvGender;
    this.tvGrade = tvGrade;
    this.tvUsername = tvUsername;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_my, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_my_plate;
      TextView btnMyPlate = ViewBindings.findChildViewById(rootView, id);
      if (btnMyPlate == null) {
        break missingId;
      }

      id = R.id.btn_my_post;
      TextView btnMyPost = ViewBindings.findChildViewById(rootView, id);
      if (btnMyPost == null) {
        break missingId;
      }

      id = R.id.btn_to_login;
      TextView btnToLogin = ViewBindings.findChildViewById(rootView, id);
      if (btnToLogin == null) {
        break missingId;
      }

      id = R.id.btn_to_setting;
      TextView btnToSetting = ViewBindings.findChildViewById(rootView, id);
      if (btnToSetting == null) {
        break missingId;
      }

      id = R.id.iv_avatar;
      ImageView ivAvatar = ViewBindings.findChildViewById(rootView, id);
      if (ivAvatar == null) {
        break missingId;
      }

      id = R.id.ll_user_info;
      LinearLayout llUserInfo = ViewBindings.findChildViewById(rootView, id);
      if (llUserInfo == null) {
        break missingId;
      }

      id = R.id.tv_birthday;
      TextView tvBirthday = ViewBindings.findChildViewById(rootView, id);
      if (tvBirthday == null) {
        break missingId;
      }

      id = R.id.tv_gender;
      TextView tvGender = ViewBindings.findChildViewById(rootView, id);
      if (tvGender == null) {
        break missingId;
      }

      id = R.id.tv_grade;
      TextView tvGrade = ViewBindings.findChildViewById(rootView, id);
      if (tvGrade == null) {
        break missingId;
      }

      id = R.id.tv_username;
      TextView tvUsername = ViewBindings.findChildViewById(rootView, id);
      if (tvUsername == null) {
        break missingId;
      }

      return new FragmentMyBinding((LinearLayout) rootView, btnMyPlate, btnMyPost, btnToLogin,
          btnToSetting, ivAvatar, llUserInfo, tvBirthday, tvGender, tvGrade, tvUsername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
