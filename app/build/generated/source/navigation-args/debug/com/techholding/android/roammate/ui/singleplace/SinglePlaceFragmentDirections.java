package com.techholding.android.roammate.ui.singleplace;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.techholding.android.roammate.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SinglePlaceFragmentDirections {
  private SinglePlaceFragmentDirections() {
  }

  @NonNull
  public static SinglePlaceToUploadActivity singlePlaceToUploadActivity(@NonNull String title) {
    return new SinglePlaceToUploadActivity(title);
  }

  @NonNull
  public static NavDirections singlePlaceToTranslationActivity() {
    return new ActionOnlyNavDirections(R.id.singlePlace_to_translationActivity);
  }

  public static class SinglePlaceToUploadActivity implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private SinglePlaceToUploadActivity(@NonNull String title) {
      if (title == null) {
        throw new IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("title", title);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public SinglePlaceToUploadActivity setTitle(@NonNull String title) {
      if (title == null) {
        throw new IllegalArgumentException("Argument \"title\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("title", title);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("title")) {
        String title = (String) arguments.get("title");
        __result.putString("title", title);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.singlePlace_to_uploadActivity;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getTitle() {
      return (String) arguments.get("title");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      SinglePlaceToUploadActivity that = (SinglePlaceToUploadActivity) object;
      if (arguments.containsKey("title") != that.arguments.containsKey("title")) {
        return false;
      }
      if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) {
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
      result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "SinglePlaceToUploadActivity(actionId=" + getActionId() + "){"
          + "title=" + getTitle()
          + "}";
    }
  }
}
