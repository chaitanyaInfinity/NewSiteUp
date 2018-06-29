package com.example.infinitylabs.dynamictrackerapp.view.otp_screen;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.fragements.BaseFragment;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.OtpRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.OtpResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.MainUtility;
import com.example.infinitylabs.dynamictrackerapp.view.create_password.CreatePasswordActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

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
public class OtpFragment extends BaseFragment implements Validator.ValidationListener {

    private static final String LOG_TAG = OtpFragment.class.getSimpleName();


    @BindView(R.id.tv_qustion_phone_number)
    TextView tvQuestionPhoneNumber;
    @BindView(R.id.et_mobile_number)
    @Length(min = 10, max = 10, message = "Mobile number must of 10 digits")
    EditText etMobileNumber;
    @BindView(R.id.et_otp_digit1)
    EditText etOtpDigit1;
    @BindView(R.id.et_otp_digit2)
    EditText etOtpDigit2;
    @BindView(R.id.et_otp_digit3)
    EditText etOtpDigit3;
    @BindView(R.id.et_otp_digit4)
    EditText etOtpDigit4;
    @BindView(R.id.et_otp_digit5)
    EditText etOtpDigit5;
    @BindView(R.id.et_otp_digit6)
    EditText etOtpDigit6;
    @BindView(R.id.ll_otp_edt)
    LinearLayout llOtpEdt;
    Unbinder unbinder;
    @BindView(R.id.btn_submit_otp)
    AppCompatButton btnSubmitOtp;
    @BindView(R.id.btn_get_otp_otp)
    AppCompatButton btnGetOtpOtp;
    Validator validator;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_logo)
    TextView tvLogo;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    Unbinder unbinder1;
    private StringBuilder otp;
    Integer receivedOtp;


    private SharedPreferences otpPreferences;
    private SharedPreferences.Editor otpPrefsEditor;

    public OtpFragment() {
        // Required empty public constructor
    }

    public static OtpFragment newInstance() {

        Bundle args = new Bundle();

        OtpFragment fragment = new OtpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_otp, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                //Here set your edittext
                etOtpDigit1.setText(Character.toString(message.charAt(0)));
                etOtpDigit2.setText(Character.toString(message.charAt(1)));
                etOtpDigit3.setText(Character.toString(message.charAt(2)));
                etOtpDigit4.setText(Character.toString(message.charAt(3)));
                etOtpDigit5.setText(Character.toString(message.charAt(4)));
                etOtpDigit6.setText(Character.toString(message.charAt(5)));
                MainUtility.hideSoftkeyboard(etOtpDigit6, context);
//                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
//                youredittext.settext(message);
            }
        }
    };

    @Override
    public void initializeViews() {

        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/Audiowide.ttf");
        tvLogo.setTypeface(typeFace);


//        tvVersion.setText("v" + BuildConfig.VERSION_NAME);

        otpPreferences = context.getSharedPreferences("otpPrefs", MODE_PRIVATE);

        otpPrefsEditor = otpPreferences.edit();
        otpPrefsEditor.putBoolean("numberVerified", false);
        otpPrefsEditor.putBoolean("saveLogin", false);
        otpPrefsEditor.putString("password", "");
        otpPrefsEditor.putString("mobileNumber", "");
        otpPrefsEditor.apply();

        Logger.logError(LOG_TAG, "pass " + otpPreferences.getString("password", "noval"));
        Logger.logError(LOG_TAG, "mob no " + otpPreferences.getString("mobileNumber", "noval"));
        Logger.logError(LOG_TAG, "saveLogin " + otpPreferences.getBoolean("saveLogin", false));


        validator = new Validator(this);
        validator.setValidationListener(this);

        etOtpDigit1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etOtpDigit1.getText().length();

                if (textlength1 == 1) {
                    etOtpDigit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });

        etOtpDigit2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etOtpDigit2.getText().length();

                if (textlength1 == 1) {
                    etOtpDigit3.requestFocus();
                } else {
                    etOtpDigit1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });

        etOtpDigit3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etOtpDigit3.getText().length();

                if (textlength1 == 1) {
                    etOtpDigit4.requestFocus();
                } else {
                    etOtpDigit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });

        etOtpDigit4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etOtpDigit4.getText().length();

                if (textlength1 == 1) {
                    etOtpDigit5.requestFocus();
                } else {
                    etOtpDigit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });

        etOtpDigit5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etOtpDigit5.getText().length();

                if (textlength1 == 1) {
                    etOtpDigit6.requestFocus();
                } else {
                    etOtpDigit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });

        etOtpDigit6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etOtpDigit6.getText().length();


                if (textlength1 == 1) {
                    MainUtility.hideSoftkeyboard(etOtpDigit6, context);
                } else {
                    etOtpDigit5.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });

    }

    @Override
    public void initializePresenter() {
//        otpPresenter = new OtpPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onValidationSucceeded() {
        showLoadingView(R.id.please_wait);
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setMobileNo(etMobileNumber.getText().toString().trim());

        callOtpAPI(otpRequest, new APIClientUtils(context));


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


    @OnClick({R.id.btn_submit_otp, R.id.btn_get_otp_otp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_otp:
                createOTP();
                break;
            case R.id.btn_get_otp_otp:
                validator.validate();
                break;
        }
    }


    private void createOTP() {
        otp = new StringBuilder();

        if (TextUtils.isEmpty(etOtpDigit1.getText().toString().trim())) {
            setErrorToEdt(etOtpDigit1);
            return;
        }
        if (TextUtils.isEmpty(etOtpDigit2.getText().toString().trim())) {
            setErrorToEdt(etOtpDigit2);
            return;
        }
        if (TextUtils.isEmpty(etOtpDigit3.getText().toString().trim())) {
            setErrorToEdt(etOtpDigit3);
            return;
        }
        if (TextUtils.isEmpty(etOtpDigit4.getText().toString().trim())) {
            setErrorToEdt(etOtpDigit4);
            return;
        }
        if (TextUtils.isEmpty(etOtpDigit5.getText().toString().trim())) {
            setErrorToEdt(etOtpDigit5);
            return;
        }
        if (TextUtils.isEmpty(etOtpDigit6.getText().toString().trim())) {
            setErrorToEdt(etOtpDigit6);
            return;
        }

        otp.append(etOtpDigit1.getText().toString().trim());
        otp.append(etOtpDigit2.getText().toString().trim());
        otp.append(etOtpDigit3.getText().toString().trim());
        otp.append(etOtpDigit4.getText().toString().trim());
        otp.append(etOtpDigit5.getText().toString().trim());
        otp.append(etOtpDigit6.getText().toString().trim());

        if ((String.valueOf(receivedOtp)).equals(otp.toString())) {
            otpPrefsEditor = otpPreferences.edit();
            otpPrefsEditor.putString("mobileNumber", etMobileNumber.getText().toString());
            otpPrefsEditor.apply();
            Toast.makeText(context, "OTP verified successfully",
                    Toast.LENGTH_LONG).show();
            CreatePasswordActivity.launch(context);
        } else {
            MainUtility.showMessage(btnSubmitOtp, "OTP verification failed");
        }

        CreatePasswordActivity.launch(context);

    }

    private void setErrorToEdt(EditText editText) {
        editText.setError("Please enter field");
        editText.requestFocus();
    }

//    @Override
//    public void onMobileNumberVerified(LoginResponse loginServiceResponse) {
//        hideLoadingView();
//        btnGetOtpOtp.setVisibility(View.INVISIBLE);
//        btnSubmitOtp.setVisibility(View.VISIBLE);
//        llOtpEdt.setVisibility(View.VISIBLE);
//        etMobileNumber.setEnabled(false);
//        etOtpDigit1.requestFocus();
//        MainUtility.showMessage(btnSubmitOtp, loginServiceResponse.getMsg());
//        receivedOtp = loginServiceResponse.getData().getOtp();
//
//    }
//
//    @Override
//    public void onMobileNumberNotVerified(String msg) {
//        hideLoadingView();
//        MainUtility.showMessage(btnSubmitOtp, msg);
//    }


    public void callOtpAPI(OtpRequest otpRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getGetOTP(),
                    new APIClientCallback<OtpResponse>() {

                        @Override
                        public void onSuccess(OtpResponse otpResponse) {


                            if (otpResponse.getStatus()) {

                                hideLoadingView();
                                btnGetOtpOtp.setVisibility(View.INVISIBLE);
                                btnSubmitOtp.setVisibility(View.VISIBLE);
                                llOtpEdt.setVisibility(View.VISIBLE);
                                etMobileNumber.setEnabled(false);
                                etOtpDigit1.requestFocus();
                                MainUtility.showMessage(btnSubmitOtp, otpResponse.getMsg());
                                receivedOtp = otpResponse.getData().getOTP();


                            } else {

                                hideLoadingView();
                                MainUtility.showMessage(btnSubmitOtp, otpResponse.getMsg());
                            }
                        }


                        @Override
                        public void onFailure(Exception e) {
                            Logger.logError(LOG_TAG, "Exception  " + e.toString());
                            hideLoadingView();
                            MainUtility.showMessage(btnSubmitOtp, "Oops, Something went wrong.");
                        }

                        @NonNull
                        @Override
                        public Class<OtpResponse> getClassOfType() {
                            return OtpResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(otpRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }
}
