package com.techholding.android.roammate.ui.fragment.plan;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.techholding.android.roammate.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class PlanFragmentDirections {
  private PlanFragmentDirections() {
  }

  @NonNull
  public static PlanToAddPlan planToAddPlan(@NonNull String placeId) {
    return new PlanToAddPlan(placeId);
  }

  public static class PlanToAddPlan implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private PlanToAddPlan(@NonNull String placeId) {
      if (placeId == null) {
        throw new IllegalArgumentException("Argument \"placeId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("placeId", placeId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public PlanToAddPlan setPlaceId(@NonNull String placeId) {
      if (placeId == null) {
        throw new IllegalArgumentException("Argument \"placeId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("placeId", placeId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("placeId")) {
        String placeId = (String) arguments.get("placeId");
        __result.putString("placeId", placeId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.plan_to_addPlan;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getPlaceId() {
      return (String) arguments.get("placeId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      PlanToAddPlan that = (PlanToAddPlan) object;
      if (arguments.containsKey("placeId") != that.arguments.containsKey("placeId")) {
        return false;
      }
      if (getPlaceId() != null ? !getPlaceId().equals(that.getPlaceId()) : that.getPlaceId() != null) {
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
      result = 31 * result + (getPlaceId() != null ? getPlaceId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "PlanToAddPlan(actionId=" + getActionId() + "){"
          + "placeId=" + getPlaceId()
          + "}";
    }
  }
}
