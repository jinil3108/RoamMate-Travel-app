package com.techholding.android.roammate.ui.login;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.techholding.android.roammate.R;

public class RegisterFragmentDirections {
  private RegisterFragmentDirections() {
  }

  @NonNull
  public static NavDirections signupToPhone() {
    return new ActionOnlyNavDirections(R.id.signup_to_phone);
  }

  @NonNull
  public static NavDirections signupToLogin() {
    return new ActionOnlyNavDirections(R.id.signup_to_login);
  }
}
