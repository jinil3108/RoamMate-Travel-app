package com.techholding.android.roammate.ui.fragment.explore;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.techholding.android.roammate.R;
import com.techholding.android.roammate.model.Place;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ExploreFragmentDirections {
  private ExploreFragmentDirections() {
  }

  @NonNull
  public static ExploreToSinglePlace exploreToSinglePlace(@NonNull Place place) {
    return new ExploreToSinglePlace(place);
  }

  @NonNull
  public static NavDirections exploreToChat() {
    return new ActionOnlyNavDirections(R.id.explore_to_chat);
  }

  public static class ExploreToSinglePlace implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ExploreToSinglePlace(@NonNull Place place) {
      if (place == null) {
        throw new IllegalArgumentException("Argument \"place\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("place", place);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ExploreToSinglePlace setPlace(@NonNull Place place) {
      if (place == null) {
        throw new IllegalArgumentException("Argument \"place\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("place", place);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("place")) {
        Place place = (Place) arguments.get("place");
        if (Parcelable.class.isAssignableFrom(Place.class) || place == null) {
          __result.putParcelable("place", Parcelable.class.cast(place));
        } else if (Serializable.class.isAssignableFrom(Place.class)) {
          __result.putSerializable("place", Serializable.class.cast(place));
        } else {
          throw new UnsupportedOperationException(Place.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.explore_to_single_place;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Place getPlace() {
      return (Place) arguments.get("place");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ExploreToSinglePlace that = (ExploreToSinglePlace) object;
      if (arguments.containsKey("place") != that.arguments.containsKey("place")) {
        return false;
      }
      if (getPlace() != null ? !getPlace().equals(that.getPlace()) : that.getPlace() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getPlace() != null ? getPlace().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ExploreToSinglePlace(actionId=" + getActionId() + "){"
          + "place=" + getPlace()
          + "}";
    }
  }
}
