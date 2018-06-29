package com.example.infinitylabs.dynamictrackerapp.view.site_approval;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.fragements.BaseFragment;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.LoginResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.MainResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.NewLoginRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.SiteStatusRequest;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.LoginResponseUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.example.infinitylabs.dynamictrackerapp.view.section.SectionListActivity;
import com.example.infinitylabs.dynamictrackerapp.view.task.TaskListActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteApprovalFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String LOG_TAG = SiteApprovalFragment.class.getSimpleName();


    @BindView(R.id.et_reason_site_approval)
    @NotEmpty(message = "Please fill the reason")
    EditText etReasonSiteApproval;
    @BindView(R.id.tv_site_approval)
    TextView tvSiteApproval;
    @BindView(R.id.ll_tv_header)
    LinearLayout llTvHeader;
    @BindView(R.id.til_reason_site_approval)
    LinearLayout tilReasonSiteApproval;
    @BindView(R.id.tv_engg_name)
    TextView tvEnggName;
    @BindView(R.id.tv_contact_number)
    TextView tvContactNumber;
    @BindView(R.id.tv_map_link)
    TextView tvMapLink;
    Unbinder unbinder;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_reject)
    Button btnReject;
    Unbinder unbinder1;
    @BindView(R.id.mapview)
    MapView mapview;
    Unbinder unbinder2;
    GoogleMap map;


    public SiteApprovalFragment() {
        // Required empty public constructor
    }

    public static SiteApprovalFragment newInstance() {

        Bundle args = new Bundle();

        SiteApprovalFragment fragment = new SiteApprovalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_site_approval, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initializeViews() {
        tvEnggName.setText(SelectedTaskUtility.getInstance().getEnggName());
        tvContactNumber.setText(SelectedTaskUtility.getInstance().getEnggContactNumber());

    }

    @Override
    public void initializePresenter() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_map_link})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_map_link:
                String map = "http://maps.google.com/maps?daddr=" + SelectedTaskUtility.getInstance().getLatitude() +
                        SelectedTaskUtility.getInstance().getLongitude() + "Integration Site";
                Intent siteMap = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                siteMap.setPackage("com.google.android.apps.maps");
                startActivity(siteMap);
                break;
        }
    }


    @OnClick({R.id.btn_accept, R.id.btn_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                showLoadingView(R.id.please_wait);

                SiteStatusRequest siteStatusRequest1 = new SiteStatusRequest();
                siteStatusRequest1.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                siteStatusRequest1.setStatus("accepted");

                callAPI(siteStatusRequest1, new APIClientUtils(context));
                SectionListActivity.launch(getActivity());
                break;
            case R.id.btn_reject:
                showLoadingView(R.id.please_wait);
                SiteStatusRequest siteStatusRequest2 = new SiteStatusRequest();
                siteStatusRequest2.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                siteStatusRequest2.setStatus("rejected");
                callAPI(siteStatusRequest2, new APIClientUtils(context));
                TaskListActivity.launch(getActivity());
                break;
        }
    }


    public void callAPI(SiteStatusRequest siteStatusRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getSiteApproval(),
                    new APIClientCallback<MainResponse>() {

                        @Override
                        public void onSuccess(MainResponse mainResponse) {
                            hideLoadingView();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            Logger.logError(LOG_TAG, "Exception " + e);
                        }

                        @NonNull
                        @Override
                        public Class<MainResponse> getClassOfType() {
                            return MainResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(siteStatusRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
