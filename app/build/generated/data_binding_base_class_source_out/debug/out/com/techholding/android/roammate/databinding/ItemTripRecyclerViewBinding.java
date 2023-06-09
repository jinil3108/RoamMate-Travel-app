// Generated by view binder compiler. Do not edit!
package com.techholding.android.roammate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ItemTripRecyclerViewBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CardView placesViewer;

  @NonNull
  public final TextView titleTrip;

  @NonNull
  public final TextView tripPlaces;

  private ItemTripRecyclerViewBinding(@NonNull CardView rootView, @NonNull CardView placesViewer,
      @NonNull TextView titleTrip, @NonNull TextView tripPlaces) {
    this.rootView = rootView;
    this.placesViewer = placesViewer;
    this.titleTrip = titleTrip;
    this.tripPlaces = tripPlaces;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemTripRecyclerViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemTripRecyclerViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_trip_recycler_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemTripRecyclerViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      CardView placesViewer = (CardView) rootView;

      id = R.id.title_trip;
      TextView titleTrip = ViewBindings.findChildViewById(rootView, id);
      if (titleTrip == null) {
        break missingId;
      }

      id = R.id.trip_places;
      TextView tripPlaces = ViewBindings.findChildViewById(rootView, id);
      if (tripPlaces == null) {
        break missingId;
      }

      return new ItemTripRecyclerViewBinding((CardView) rootView, placesViewer, titleTrip,
          tripPlaces);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
