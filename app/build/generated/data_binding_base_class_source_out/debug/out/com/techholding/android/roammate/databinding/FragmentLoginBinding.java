// Generated by view binder compiler. Do not edit!
package com.techholding.android.roammate.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.techholding.android.roammate.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView alternateLogin;

  @NonNull
  public final TextView createAccount;

  @NonNull
  public final EditText emailLogin;

  @NonNull
  public final LinearLayout extraButtons;

  @NonNull
  public final TextView forgotPassword;

  @NonNull
  public final Button googleLogin;

  @NonNull
  public final Button loginButton;

  @NonNull
  public final ConstraintLayout partitionLayout;

  @NonNull
  public final TextInputLayout passwordBox;

  @NonNull
  public final TextInputEditText passwordLogin;

  @NonNull
  public final Button phoneLogin;

  @NonNull
  public final TextView primaryLogo;

  @NonNull
  public final TextView secondaryLogo;

  private FragmentLoginBinding(@NonNull ConstraintLayout rootView, @NonNull TextView alternateLogin,
      @NonNull TextView createAccount, @NonNull EditText emailLogin,
      @NonNull LinearLayout extraButtons, @NonNull TextView forgotPassword,
      @NonNull Button googleLogin, @NonNull Button loginButton,
      @NonNull ConstraintLayout partitionLayout, @NonNull TextInputLayout passwordBox,
      @NonNull TextInputEditText passwordLogin, @NonNull Button phoneLogin,
      @NonNull TextView primaryLogo, @NonNull TextView secondaryLogo) {
    this.rootView = rootView;
    this.alternateLogin = alternateLogin;
    this.createAccount = createAccount;
    this.emailLogin = emailLogin;
    this.extraButtons = extraButtons;
    this.forgotPassword = forgotPassword;
    this.googleLogin = googleLogin;
    this.loginButton = loginButton;
    this.partitionLayout = partitionLayout;
    this.passwordBox = passwordBox;
    this.passwordLogin = passwordLogin;
    this.phoneLogin = phoneLogin;
    this.primaryLogo = primaryLogo;
    this.secondaryLogo = secondaryLogo;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.alternateLogin;
      TextView alternateLogin = ViewBindings.findChildViewById(rootView, id);
      if (alternateLogin == null) {
        break missingId;
      }

      id = R.id.createAccount;
      TextView createAccount = ViewBindings.findChildViewById(rootView, id);
      if (createAccount == null) {
        break missingId;
      }

      id = R.id.emailLogin;
      EditText emailLogin = ViewBindings.findChildViewById(rootView, id);
      if (emailLogin == null) {
        break missingId;
      }

      id = R.id.extraButtons;
      LinearLayout extraButtons = ViewBindings.findChildViewById(rootView, id);
      if (extraButtons == null) {
        break missingId;
      }

      id = R.id.forgotPassword;
      TextView forgotPassword = ViewBindings.findChildViewById(rootView, id);
      if (forgotPassword == null) {
        break missingId;
      }

      id = R.id.googleLogin;
      Button googleLogin = ViewBindings.findChildViewById(rootView, id);
      if (googleLogin == null) {
        break missingId;
      }

      id = R.id.loginButton;
      Button loginButton = ViewBindings.findChildViewById(rootView, id);
      if (loginButton == null) {
        break missingId;
      }

      id = R.id.partitionLayout;
      ConstraintLayout partitionLayout = ViewBindings.findChildViewById(rootView, id);
      if (partitionLayout == null) {
        break missingId;
      }

      id = R.id.passwordBox;
      TextInputLayout passwordBox = ViewBindings.findChildViewById(rootView, id);
      if (passwordBox == null) {
        break missingId;
      }

      id = R.id.passwordLogin;
      TextInputEditText passwordLogin = ViewBindings.findChildViewById(rootView, id);
      if (passwordLogin == null) {
        break missingId;
      }

      id = R.id.phoneLogin;
      Button phoneLogin = ViewBindings.findChildViewById(rootView, id);
      if (phoneLogin == null) {
        break missingId;
      }

      id = R.id.primaryLogo;
      TextView primaryLogo = ViewBindings.findChildViewById(rootView, id);
      if (primaryLogo == null) {
        break missingId;
      }

      id = R.id.secondaryLogo;
      TextView secondaryLogo = ViewBindings.findChildViewById(rootView, id);
      if (secondaryLogo == null) {
        break missingId;
      }

      return new FragmentLoginBinding((ConstraintLayout) rootView, alternateLogin, createAccount,
          emailLogin, extraButtons, forgotPassword, googleLogin, loginButton, partitionLayout,
          passwordBox, passwordLogin, phoneLogin, primaryLogo, secondaryLogo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}