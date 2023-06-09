// Generated by view binder compiler. Do not edit!
package com.techholding.android.roammate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.techholding.android.roammate.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentChatBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnsend;

  @NonNull
  public final View darkDivider;

  @NonNull
  public final EditText etmessage;

  @NonNull
  public final LinearLayout llLayoutBar;

  @NonNull
  public final RecyclerView rvmessages;

  private FragmentChatBinding(@NonNull RelativeLayout rootView, @NonNull Button btnsend,
      @NonNull View darkDivider, @NonNull EditText etmessage, @NonNull LinearLayout llLayoutBar,
      @NonNull RecyclerView rvmessages) {
    this.rootView = rootView;
    this.btnsend = btnsend;
    this.darkDivider = darkDivider;
    this.etmessage = etmessage;
    this.llLayoutBar = llLayoutBar;
    this.rvmessages = rvmessages;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_chat, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentChatBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnsend;
      Button btnsend = ViewBindings.findChildViewById(rootView, id);
      if (btnsend == null) {
        break missingId;
      }

      id = R.id.dark_divider;
      View darkDivider = ViewBindings.findChildViewById(rootView, id);
      if (darkDivider == null) {
        break missingId;
      }

      id = R.id.etmessage;
      EditText etmessage = ViewBindings.findChildViewById(rootView, id);
      if (etmessage == null) {
        break missingId;
      }

      id = R.id.ll_layout_bar;
      LinearLayout llLayoutBar = ViewBindings.findChildViewById(rootView, id);
      if (llLayoutBar == null) {
        break missingId;
      }

      id = R.id.rvmessages;
      RecyclerView rvmessages = ViewBindings.findChildViewById(rootView, id);
      if (rvmessages == null) {
        break missingId;
      }

      return new FragmentChatBinding((RelativeLayout) rootView, btnsend, darkDivider, etmessage,
          llLayoutBar, rvmessages);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
