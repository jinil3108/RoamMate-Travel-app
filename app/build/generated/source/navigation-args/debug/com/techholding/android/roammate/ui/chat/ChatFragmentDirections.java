package com.techholding.android.roammate.ui.chat;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.techholding.android.roammate.R;

public class ChatFragmentDirections {
  private ChatFragmentDirections() {
  }

  @NonNull
  public static NavDirections chatToExplore() {
    return new ActionOnlyNavDirections(R.id.chat_to_explore);
  }

  @NonNull
  public static NavDirections chatToPlan() {
    return new ActionOnlyNavDirections(R.id.chat_to_plan);
  }

  @NonNull
  public static NavDirections chatToSettings() {
    return new ActionOnlyNavDirections(R.id.chat_to_settings);
  }
}
