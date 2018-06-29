package com.example.infinitylabs.dynamictrackerapp.view.create_password;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.fragements.BaseFragment;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.CreatePasswordRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.CreatePasswordResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.LoginResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.MainUtility;
import com.example.infinitylabs.dynamictrackerapp.view.login.LoginActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
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
public class CreatePasswordFragment extends BaseFragment implements Validator.ValidationListener {

    private static final String LOG_TAG = CreatePasswordFragment.class.getSimpleName();


    @BindView(R.id.tv_create_password)
    TextView tvCreatePassword;
    @BindView(R.id.tv_new_password)
    TextView tvNewPassword;
    @BindView(R.id.et_new_password)
    @NotEmpty(message = "Please enter new password")
    EditText etNewPassword;
    @BindView(R.id.tv_reenter_new_password)
    TextView tvReenterNewPassword;
    @BindView(R.id.et_reenter_new_password)
    @NotEmpty(message = "Please re-enter new password")
    EditText etReenterNewPassword;
    @BindView(R.id.btn_create_password)
    AppCompatButton btnCreatePassword;
    Unbinder unbinder;
    @BindView(R.id.cb_show_password)
    CheckBox cbShowPassword;
    Validator validator;

    private SharedPreferences otpPreferences;
    private SharedPreferences.Editor otpPrefsEditor;
//    CreatePasswordPresenter createPasswordPresenter;

    public CreatePasswordFragment() {
        // Required empty public constructor
    }

    public static CreatePasswordFragment newInstance() {

        Bundle args = new Bundle();

        CreatePasswordFragment fragment = new CreatePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_create_password, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initializeViews() {

        otpPreferences = context.getSharedPreferences("otpPrefs", MODE_PRIVATE);

        validator = new Validator(this);
        validator.setValidationListener(this);

        cbShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etReenterNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etReenterNewPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    @Override
    public void initializePresenter() {
//        createPasswordPresenter = new CreatePasswordPresenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onValidationSucceeded() {

        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etReenterNewPassword.getText().toString().trim();
        String mobileNumber = otpPreferences.getString("mobileNumber", null);

        if (confirmPassword.equals(newPassword)) {
            showLoadingView(R.id.please_wait);
            CreatePasswordRequest createPasswordRequest = new CreatePasswordRequest();
            createPasswordRequest.setMobileNo(mobileNumber);
            createPasswordRequest.setPassword(confirmPassword);

            callCreatePasswordAPI(createPasswordRequest, new APIClientUtils(context));

        } else {
            Snackbar.make(etReenterNewPassword,
                    "Your new password does not match",
                    Snackbar.LENGTH_LONG).show();
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


    @OnClick(R.id.btn_create_password)
    public void onViewClicked() {
        validator.validate();
    }

//    @Override
//    public void onAPISuccess(String msg) {
//        hideLoadingView();
//        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//        otpPrefsEditor = otpPreferences.edit();
//        otpPrefsEditor.putBoolean("numberVerified", true);
//        otpPrefsEditor.apply();
//        LoginActivity.launch(context);
//    }
//
//    @Override
//    public void onAPIFailure(String s) {
//        hideLoadingView();
//        MainUtility.showMessage(btnCreatePassword,s);
//    }


    public void callCreatePasswordAPI(CreatePasswordRequest createPasswordRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getCreateNewPassword(),
                    new APIClientCallback<CreatePasswordResponse>() {

                        @Override
                        public void onSuccess(CreatePasswordResponse createPasswordResponse) {


                            if (createPasswordResponse.getStatus()) {

                                hideLoadingView();
                                Toast.makeText(context, createPasswordResponse.getMsg(), Toast.LENGTH_LONG).show();
                                otpPrefsEditor = otpPreferences.edit();
                                otpPrefsEditor.putBoolean("numberVerified", true);
                                otpPrefsEditor.apply();
                                LoginActivity.launch(context);

                            } else {
                                hideLoadingView();
                                MainUtility.showMessage(btnCreatePassword, createPasswordResponse.getMsg());
                            }
                        }


                        @Override
                        public void onFailure(Exception e) {
                            Logger.logError(LOG_TAG, "Exception  " + e.toString());
                            hideLoadingView();
                            MainUtility.showMessage(btnCreatePassword, "Oops, Something went wrong.");
                        }

                        @NonNull
                        @Override
                        public Class<CreatePasswordResponse> getClassOfType() {
                            return CreatePasswordResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(createPasswordRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


}
