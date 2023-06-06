package com.techholding.android.roammate.ui.singleplace;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import com.techholding.android.roammate.model.Place;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SinglePlaceFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private SinglePlaceFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private SinglePlaceFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static SinglePlaceFragmentArgs fromBundle(@NonNull Bundle bundle) {
    SinglePlaceFragmentArgs __result = new SinglePlaceFragmentArgs();
    bundle.setClassLoader(SinglePlaceFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("place")) {
      Place place;
      if (Parcelable.class.isAssignableFrom(Place.class) || Serializable.class.isAssignableFrom(Place.class)) {
        place = (Place) bundle.get("place");
      } else {
        throw new UnsupportedOperationException(Place.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (place == null) {
        throw new IllegalArgumentException("Argument \"place\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("place", place);
    } else {
      throw new IllegalArgumentException("Required argument \"place\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SinglePlaceFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    SinglePlaceFragmentArgs __result = new SinglePlaceFragmentArgs();
    if (savedStateHandle.contains("place")) {
      Place place;
      place = savedStateHandle.get("place");
      if (place == null) {
        throw new IllegalArgumentException("Argument \"place\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("place", place);
    } else {
      throw new IllegalArgumentException("Required argument \"place\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Place getPlace() {
    return (Place) arguments.get("place");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("place")) {
      Place place = (Place) arguments.get("place");
      if (Parcelable.class.isAssignableFrom(Place.class) || place == null) {
        __result.set("place", Parcelable.class.cast(place));
      } else if (Serializable.class.isAssignableFrom(Place.class)) {
        __result.set("place", Serializable.class.cast(place));
      } else {
        throw new UnsupportedOperationException(Place.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    SinglePlaceFragmentArgs that = (SinglePlaceFragmentArgs) object;
    if (arguments.containsKey("place") != that.arguments.containsKey("place")) {
      return false;
    }
    if (getPlace() != null ? !getPlace().equals(that.getPlace()) : that.getPlace() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getPlace() != null ? getPlace().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SinglePlaceFragmentArgs{"
        + "place=" + getPlace()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull SinglePlaceFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull Place place) {
      if (place == null) {
        throw new IllegalArgumentException("Argument \"place\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("place", place);
    }

    @NonNull
    public SinglePlaceFragmentArgs build() {
      SinglePlaceFragmentArgs result = new SinglePlaceFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPlace(@NonNull Place place) {
      if (place == null) {
        throw new IllegalArgumentException("Argument \"place\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("place", place);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public Place getPlace() {
      return (Place) arguments.get("place");
    }
  }
}
