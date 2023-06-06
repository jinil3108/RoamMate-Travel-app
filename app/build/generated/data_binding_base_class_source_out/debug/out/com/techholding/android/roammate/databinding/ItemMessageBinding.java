// Generated by view binder compiler. Do not edit!
package com.techholding.android.roammate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.techholding.android.roammate.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemMessageBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView tvbotmessage;

  @NonNull
  public final TextView tvmessage;

  private ItemMessageBinding(@NonNull RelativeLayout rootView, @NonNull TextView tvbotmessage,
      @NonNull TextView tvmessage) {
    this.rootView = rootView;
    this.tvbotmessage = tvbotmessage;
    this.tvmessage = tvmessage;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemMessageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemMessageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_message, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemMessageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tvbotmessage;
      TextView tvbotmessage = ViewBindings.findChildViewById(rootView, id);
      if (tvbotmessage == null) {
        break missingId;
      }

      id = R.id.tvmessage;
      TextView tvmessage = ViewBindings.findChildViewById(rootView, id);
      if (tvmessage == null) {
        break missingId;
      }

      return new ItemMessageBinding((RelativeLayout) rootView, tvbotmessage, tvmessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
