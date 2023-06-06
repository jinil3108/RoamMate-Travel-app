package com.techholding.android.roammate.ui.login;

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

public class PhoneFragmentDirections {
  private PhoneFragmentDirections() {
  }

  @NonNull
  public static PhoneToOtp phoneToOtp(@NonNull String phoneNumber) {
    return new PhoneToOtp(phoneNumber);
  }

  public static class PhoneToOtp implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private PhoneToOtp(@NonNull String phoneNumber) {
      if (phoneNumber == null) {
        throw new IllegalArgumentException("Argument \"phone_number\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("phone_number", phoneNumber);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public PhoneToOtp setPhoneNumber(@NonNull String phoneNumber) {
      if (phoneNumber == null) {
        throw new IllegalArgumentException("Argument \"phone_number\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("phone_number", phoneNumber);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("phone_number")) {
        String phoneNumber = (String) arguments.get("phone_number");
        __result.putString("phone_number", phoneNumber);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.phone_to_otp;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getPhoneNumber() {
      return (String) arguments.get("phone_number");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      PhoneToOtp that = (PhoneToOtp) object;
      if (arguments.containsKey("phone_number") != that.arguments.containsKey("phone_number")) {
        return false;
      }
      if (getPhoneNumber() != null ? !getPhoneNumber().equals(that.getPhoneNumber()) : that.getPhoneNumber() != null) {
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
      result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "PhoneToOtp(actionId=" + getActionId() + "){"
          + "phoneNumber=" + getPhoneNumber()
          + "}";
    }
  }
}
