package com.techholding.android.roammate.ui.login;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.techholding.android.roammate.R;

public class LoginFragmentDirections {
  private LoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections loginToPhone() {
    return new ActionOnlyNavDirections(R.id.login_to_phone);
  }

  @NonNull
  public static NavDirections loginToSignup() {
    return new ActionOnlyNavDirections(R.id.login_to_signup);
  }
}
