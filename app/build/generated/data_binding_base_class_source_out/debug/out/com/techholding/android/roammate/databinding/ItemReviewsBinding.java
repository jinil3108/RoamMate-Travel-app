// Generated by view binder compiler. Do not edit!
package com.techholding.android.roammate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.techholding.android.roammate.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemReviewsBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ConstraintLayout cardViewer;

  @NonNull
  public final TextView ratingsViewer;

  @NonNull
  public final CardView reviewsViewer;

  @NonNull
  public final TextView userName;

  @NonNull
  public final TextView viewFeedback;

  private ItemReviewsBinding(@NonNull CardView rootView, @NonNull ConstraintLayout cardViewer,
      @NonNull TextView ratingsViewer, @NonNull CardView reviewsViewer, @NonNull TextView userName,
      @NonNull TextView viewFeedback) {
    this.rootView = rootView;
    this.cardViewer = cardViewer;
    this.ratingsViewer = ratingsViewer;
    this.reviewsViewer = reviewsViewer;
    this.userName = userName;
    this.viewFeedback = viewFeedback;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemReviewsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemReviewsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_reviews, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemReviewsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardViewer;
      ConstraintLayout cardViewer = ViewBindings.findChildViewById(rootView, id);
      if (cardViewer == null) {
        break missingId;
      }

      id = R.id.ratingsViewer;
      TextView ratingsViewer = ViewBindings.findChildViewById(rootView, id);
      if (ratingsViewer == null) {
        break missingId;
      }

      CardView reviewsViewer = (CardView) rootView;

      id = R.id.userName;
      TextView userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      id = R.id.viewFeedback;
      TextView viewFeedback = ViewBindings.findChildViewById(rootView, id);
      if (viewFeedback == null) {
        break missingId;
      }

      return new ItemReviewsBinding((CardView) rootView, cardViewer, ratingsViewer, reviewsViewer,
          userName, viewFeedback);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
