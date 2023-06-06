package com.techholding.android.roammate.ui.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.techholding.android.roammate.R;

public class SettingsFragmentDirections {
  private SettingsFragmentDirections() {
  }

  @NonNull
  public static NavDirections settingsToEditProfile() {
    return new ActionOnlyNavDirections(R.id.settings_to_editProfile);
  }
}
