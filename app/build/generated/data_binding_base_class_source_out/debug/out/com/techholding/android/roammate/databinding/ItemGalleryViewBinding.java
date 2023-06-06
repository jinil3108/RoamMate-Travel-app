// Generated by view binder compiler. Do not edit!
package com.techholding.android.roammate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.techholding.android.roammate.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemGalleryViewBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView recyclerCaption;

  @NonNull
  public final ImageView recyclerImage;

  private ItemGalleryViewBinding(@NonNull CardView rootView, @NonNull TextView recyclerCaption,
      @NonNull ImageView recyclerImage) {
    this.rootView = rootView;
    this.recyclerCaption = recyclerCaption;
    this.recyclerImage = recyclerImage;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemGalleryViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemGalleryViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_gallery_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemGalleryViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recyclerCaption;
      TextView recyclerCaption = ViewBindings.findChildViewById(rootView, id);
      if (recyclerCaption == null) {
        break missingId;
      }

      id = R.id.recyclerImage;
      ImageView recyclerImage = ViewBindings.findChildViewById(rootView, id);
      if (recyclerImage == null) {
        break missingId;
      }

      return new ItemGalleryViewBinding((CardView) rootView, recyclerCaption, recyclerImage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
