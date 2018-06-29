package com.example.infinitylabs.dynamictrackerapp.view.login;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.infinitylabs.dynamictrackerapp.BuildConfig;
import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.fragements.BaseFragment;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.LoginResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.NewLoginRequest;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.LoginResponseUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.MainUtility;
import com.example.infinitylabs.dynamictrackerapp.view.otp_screen.OtpActivity;
import com.example.infinitylabs.dynamictrackerapp.view.task.TaskListActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements Validator.ValidationListener {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();


    Validator validator;
    Unbinder unbinder;
    @BindView(R.id.tv_logo)
    TextView tvLogo;
    @BindView(R.id.et_password)
    @NotEmpty(message = "Please enter password")
    EditText etPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.cb_remember_me)
    CheckBox cbRememberMe;
    @BindView(R.id.cb_show_password)
    CheckBox cbShowPassword;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    Unbinder unbinder1;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private SharedPreferences otpPreferences;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_login, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initializeViews() {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Audiowide.ttf");
        tvLogo.setTypeface(typeFace);

        tvVersion.setText("v"+ BuildConfig.VERSION_NAME);

        otpPreferences = context.getSharedPreferences("otpPrefs", MODE_PRIVATE);

        validator = new Validator(this);
        validator.setValidationListener(this);

        cbShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });


        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPrefsEditor.putBoolean("numberVerified", false);
                loginPrefsEditor.putBoolean("saveLogin", false);
                loginPrefsEditor.putString("password", "");
                loginPrefsEditor.putString("mobileNumber", "");
                loginPrefsEditor.apply();

                OtpActivity.launch(context);
            }
        });


        loginPreferences = context.getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            etPassword.setText(loginPreferences.getString("password", ""));
            cbRememberMe.setChecked(true);
        } else {
            etPassword.setText("");
            cbRememberMe.setChecked(false);
        }

    }

    @Override
    public void initializePresenter() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {

        validator.validate();
    }


    @Override
    public void onValidationSucceeded() {
        showLoadingView(R.id.please_wait);


        if (cbRememberMe.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("password", etPassword.getText().toString());
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        String mobileNumber = otpPreferences.getString("mobileNumber", null);
        NewLoginRequest loginRequest = new NewLoginRequest();
        loginRequest.setMobileNo(mobileNumber);
        loginRequest.setPassword(etPassword.getText().toString());

        callAPI(loginRequest, new APIClientUtils(getActivity()));
    }


    public void callAPI(NewLoginRequest loginRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getLogin(),
                    new APIClientCallback<LoginResponse>() {

                        @Override
                        public void onSuccess(LoginResponse loginResponse) {

                            hideLoadingView();
                            if (loginResponse.getStatus()) {
                                LoginResponseUtility.getInstance().setLoginData(loginResponse.getData());
//                                MainUtility.showMessage(btnSubmit, loginResponse.getMsg());
                                Toast.makeText(getActivity(), loginResponse.getMsg(), Toast.LENGTH_LONG).show();

                                    // TODO: Use the current user's information
                                    // You can call any combination of these three methods
                                    Crashlytics.setUserIdentifier(LoginResponseUtility.getInstance().getUserId());
                                    Crashlytics.setUserEmail(LoginResponseUtility.getInstance().getMobileNumber());
                                    Crashlytics.setUserName(LoginResponseUtility.getInstance().getUserName());


                                TaskListActivity.launch(context);
                            } else {
                                Toast.makeText(getActivity(), "Incorrect credentials", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            Logger.logError(LOG_TAG, "Exception " + e);
                            MainUtility.showMessage(btnSubmit, "Oops, Something went wrong.");
                        }

                        @NonNull
                        @Override
                        public Class<LoginResponse> getClassOfType() {
                            return LoginResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(loginRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }


}
