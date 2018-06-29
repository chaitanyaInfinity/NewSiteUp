package com.example.infinitylabs.dynamictrackerapp.view.question;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.AnswerRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.MainResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionListData;
import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionListRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionListResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.MainUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.ScannerActivity;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedQuestionUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedSectionUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.TrackGPS;
import com.example.infinitylabs.dynamictrackerapp.view.section.SectionListActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.ProlificSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class QuestionActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = QuestionActivity.class.getSimpleName();

    static final int REQUEST_IMAGE_CAPTURE = 1001;
    static final int REQUEST_BARCODE_CAPTURE = 1002;
    private static final String IMAGE_DIRECTORY_NAME = "IP-RAN";


    @BindView(R.id.rv_assigned_sites)
    RecyclerView rvAssignedSites;
    @BindView(R.id.view_main_question)
    RelativeLayout viewMainQuestion;
    @BindView(R.id.blank)
    View blank;
    @BindView(R.id.tv_box_lat)
    TextView tvBoxLat;
    @BindView(R.id.tv_box_long)
    TextView tvBoxLong;
    @BindView(R.id.btnSubmitLocation)
    Button btnSubmitLocation;
    @BindView(R.id.view_location)
    RelativeLayout viewLocation;
    @BindView(R.id.blank1)
    View blank1;
    @BindView(R.id.btnSubmitComment)
    Button btnSubmitComment;
    @BindView(R.id.view_comment)
    RelativeLayout viewComment;
    @BindView(R.id.blank2)
    View blank2;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.ll_to_swipe)
    LinearLayout llToSwipe;
    @BindView(R.id.btnSubmitQuestion)
    Button btnSubmitQuestion;
    @BindView(R.id.view_question)
    RelativeLayout viewQuestion;
    @BindView(R.id.main)
    FrameLayout main;
    @BindView(R.id.tv_question_camera)
    TextView tvQuestionCamera;
    @BindView(R.id.img_camera_photo)
    ImageView imgCameraPhoto;
    @BindView(R.id.btnSubmitCamera)
    Button btnSubmitCamera;
    @BindView(R.id.view_camera)
    RelativeLayout viewCamera;
    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.tv_question_comment)
    TextView tvQuestionComment;
    @BindView(R.id.edt_comment)
    EditText edtComment;
    @BindView(R.id.blank3)
    View blank3;
    @BindView(R.id.tv_question_barcode)
    TextView tvQuestionBarcode;
    @BindView(R.id.et_router_code)
    EditText etRouterCode;
    @BindView(R.id.btn_router_barcode)
    Button btnRouterBarcode;
    @BindView(R.id.btnSubmitBarcode)
    Button btnSubmitBarcode;
    @BindView(R.id.view_barcode)
    RelativeLayout viewBarcode;
    @BindView(R.id.radioButton1)
    RadioButton radioButton1;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.radioGroup)
    SegmentedGroup radioGroup;
    @BindView(R.id.blank4)
    View blank4;
    @BindView(R.id.tv_loading_message)
    TextView tvLoadingMessage;
    @BindView(R.id.ll_progress_main)
    LinearLayout llProgressMain;
    @BindView(R.id.blank5)
    View blank5;
    @BindView(R.id.tv_question_signature)
    TextView tvQuestionSignature;
    SignaturePad signaturePad;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btnSubmitSignature)
    Button btnSubmitSignature;
    @BindView(R.id.view_signature)
    RelativeLayout viewSignature;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.spinner_question)
    Spinner spinnerQuestion;
    @BindView(R.id.edt_question_comment)
    EditText edtQuestionComment;
    @BindView(R.id.blank6)
    View blank6;
    @BindView(R.id.tv_question_router_config)
    TextView tvQuestionRouterConfig;
    @BindView(R.id.btn_configure)
    Button btnConfigure;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.sv_demoScroller)
    ScrollView svDemoScroller;
    @BindView(R.id.tv_input_command)
    EditText tvInputCommand;
    @BindView(R.id.btn_submit)
    ImageButton btnSubmit;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.btnSubmitRouterConfig)
    Button btnSubmitRouterConfig;
    @BindView(R.id.view_router_configuration)
    RelativeLayout viewRouterConfiguration;


    private static UsbSerialPort sPort = null;
    private static UsbManager mUsbManager;
    private static UsbDeviceConnection connection;
    String popupString;
    @BindView(R.id.btn_enable)
    Button btnEnable;
    @BindView(R.id.btn_password)
    Button btnPassword;
    @BindView(R.id.edt_router_command)
    TextView edtRouterCommand;
    @BindView(R.id.et_comment_photo)
    EditText etCommentPhoto;
    @BindView(R.id.rbPassedRouter)
    RadioButton rbPassedRouter;
    @BindView(R.id.rbFailedRouter)
    RadioButton rbFailedRouter;
    @BindView(R.id.rg_router_config)
    RadioGroup rgRouterConfig;
    @BindView(R.id.rbPassedCam)
    RadioButton rbPassedCam;
    @BindView(R.id.rbFailedCam)
    RadioButton rbFailedCam;
    @BindView(R.id.rg_camera)
    RadioGroup rgCamera;
    @BindView(R.id.rb_pass_location)
    RadioButton rbPassLocation;
    @BindView(R.id.rb_fail_location)
    RadioButton rbFailLocation;
    @BindView(R.id.rg_location)
    RadioGroup rgLocation;
    @BindView(R.id.rb_pass_comment)
    RadioButton rbPassComment;
    @BindView(R.id.rb_fail_comment)
    RadioButton rbFailComment;
    @BindView(R.id.rg_comment)
    RadioGroup rgComment;
    @BindView(R.id.ib_info_comment)
    ImageButton ibInfoComment;
    @BindView(R.id.ib_info_router_config)
    ImageButton ibInfoRouterConfig;
    @BindView(R.id.ib_info_question)
    ImageButton ibInfoQuestion;
    @BindView(R.id.ib_info_camera)
    ImageButton ibInfoCamera;
    @BindView(R.id.ib_info_barcode)
    ImageButton ibInfoBarcode;
    @BindView(R.id.ib_info_signature)
    ImageButton ibInfoSignature;
    @BindView(R.id.rb_pass_question)
    RadioButton rbPassQuestion;
    @BindView(R.id.rb_fail_question)
    RadioButton rbFailQuestion;
    @BindView(R.id.rg_question)
    RadioGroup rgQuestion;
    @BindView(R.id.rb_pass_barcode)
    RadioButton rbPassBarcode;
    @BindView(R.id.rb_fail_barcode)
    RadioButton rbFailBarcode;
    @BindView(R.id.rg_barcode)
    RadioGroup rgBarcode;
    @BindView(R.id.rb_pass_signature)
    RadioButton rbPassSignature;
    @BindView(R.id.rb_fail_signature)
    RadioButton rbFailSignature;
    @BindView(R.id.rg_signature)
    RadioGroup rgSignature;
    @BindView(R.id.srl_question_list)
    SwipeRefreshLayout srlQuestionList;
    @BindView(R.id.ib_setting)
    ImageButton ibSetting;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.btn_username)
    Button btnUsername;

    private List<QuestionListData> questionList = new ArrayList<>();
    private QuestionAdapter questionAdapter;
    private String stringOfImage;
    String latitude;
    String longitude;
    Marker marker;
    TrackGPS gps;
    private Uri fileUri; // file url to store image/video
    GoogleApiClient mGoogleApiClient;

    double currentLatitude;
    double currentLongitude;
    Button closePopup;
    TextView tvPopupText;
    String passwordEditTextValue = "";
    String userNameEditTextValue = "";

    private int boundRate;
    private int dataBits;
    private int parity;
    private int stopBits;

    int boundRateValue = 0;
    int dataBitsValue = 0;
    int parityvalue = 0;
    int stopBitsValue = 0;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private boolean btnSaveClicked = false;
    private Bitmap imgSignature;
    private Bitmap imgWithTimeStamp;
    public String command = "\n";

    public static void launch(Context context) {
        context.startActivity(new Intent(context, QuestionActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        preferences = this.getSharedPreferences("settingPreference", MODE_PRIVATE);

        showLoadingView(R.id.please_wait);
        tvTask.setText(SelectedTaskUtility.getInstance().getSiteName() + " > " + SelectedSectionUtility.getInstance().getSectionName());

        gps = new TrackGPS(this);

        if (gps.canGetLocation()) {

            currentLatitude = gps.getLatitude();
            currentLongitude = gps.getLongitude();

            tvBoxLat.setText(String.format("%.4f", currentLatitude));
            tvBoxLong.setText(String.format("%.4f", currentLongitude));

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        try {
            // Loading map
            initializeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

        setUpAdapter();
        setUpCamera();
        setUpSignaturePad();
        setupRouterConfig();

    }

    public String getLastnCharacters(String inputString,
                                     int subStringLength) {
        int length = inputString.length();
        if (length <= subStringLength) {
            return inputString;
        }
        int startIndex = length - subStringLength;
        return inputString.substring(startIndex);
    }


//    private boolean setupSetting() {
//
//        if (preferences.getString("boundRate", null) != null) {
//
//
//            if (preferences.getString("dataBits", null) != null) {
//
//
//                if (preferences.getString("parity", null) != null) {
//
//
//                    if (preferences.getString("stopBits", null) != null) {
//
//
//                        return true;
//
//                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                "Please select Stop bits from settings", Toast.LENGTH_SHORT).show();
//
//                        return false;
//                    }
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "Please select Parity bits from settings", Toast.LENGTH_SHORT).show();
//
//                    return false;
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(),
//                        "Please select Data bits from settings", Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//
//        } else {
//            Toast.makeText(getApplicationContext(),
//                    "Please select Bound rate from settings", Toast.LENGTH_SHORT).show();
//
//            return false;
//        }
//
//    }

    private void setupRouterConfig() {


        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getLastnCharacters(tvResult.getText().toString(), 10).contains("Username:")) {
                    showUserNameEditTextPopup();
                } else {
                    Toast.makeText(QuestionActivity.this, "Only valid for Username.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = "exit\n";


                byte[] bytes = password.getBytes();

                if (sPort != null) {

                    try {
                        sPort.write(bytes, 1000);
                        Log.e(LOG_TAG, "inside write");
//                    mExecutor.submit(mSerialIoManager);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Exception " + e);
                    }
                } else {
                    Log.e(LOG_TAG, "UsbSerialPort is null");
                }

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (setupSetting()) {

                String command = tvInputCommand.getText().toString()
                        + "\r\n";
//                if(inputCommand.getText().toString().equals("")){
//                    command = "\n";
//                }
                tvInputCommand.setText("");
//                mDumpTextView.append(command  + "\n");
                Log.e(LOG_TAG, "Sent Data " + command);
                View view = QuestionActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }


                byte[] bytes = command.getBytes();

                if (sPort != null) {

                    try {
                        sPort.write(bytes, 1000);
                        Log.e(LOG_TAG, "inside write");
//                    mExecutor.submit(mSerialIoManager);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Exception " + e);
                    }
                } else {
                    Log.e(LOG_TAG, "UsbSerialPort is null");
                }

            }


//            }
        });


//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                //intent.setType("*/*");      //all files
//                intent.setType("txt/TXT");   //XML file only
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                try {
//                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 1993);
//                } catch (ActivityNotFoundException ex) {
//                    // Potentially direct the user to the Market with a Dialog
//                    Toast.makeText(QuestionActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        btnConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String command = "\n" +
//                        "en\n" +
//                        "conf t\n" +
//                        "hostname Infinity_switch\n" +
//                        "int vlan100\n" +
//                        "ip address 10.10.10.10 255.255.255.0\n" +
//                        "no shut\n" +
//                        "end\n" +
//                        "copy run start\n" +
//                        "exit";
                byte[] bytes = command.getBytes();

                if (sPort != null) {

                    try {
                        sPort.write(bytes, 100000);
                        Log.e(LOG_TAG, "inside write");
//                    mExecutor.submit(mSerialIoManager);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Exception " + e);
                    }
                } else {
                    Log.e(LOG_TAG, "UsbSerialPort is null");
                }
            }
        });


        btnEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (setupSetting()) {

                String enable = "\n" +
                        "en\n";


                byte[] bytes = enable.getBytes();

                if (sPort != null) {

                    try {
                        sPort.write(bytes, 1000);
                        Log.e(LOG_TAG, "inside write");
//                    mExecutor.submit(mSerialIoManager);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Exception " + e);
                    }
                } else {
                    Log.e(LOG_TAG, "UsbSerialPort is null");
                }
            }
//            }
        });


        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (getLastnCharacters(tvResult.getText().toString(), 10).contains("Password:")) {
                    showPasswordEditTextPopup();
                } else {
                    Toast.makeText(QuestionActivity.this, "Only valid for Password.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void resetValues() {
        edtComment.setText("");
        imgCameraPhoto.setImageResource(R.drawable.camera);
        signaturePad.clear();
        edtQuestionComment.setText("");
        etRouterCode.setText("");
        tvResult.setText("");
        signaturePad.setEnabled(true);
        etCommentPhoto.setText("");
        popupString = "";

    }

    public void setUpSignaturePad() {

        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                btnClear.setEnabled(true);
                btnSave.setEnabled(true);
            }

            @Override
            public void onClear() {
                btnClear.setEnabled(false);
                btnSave.setEnabled(false);
                btnSaveClicked = false;
            }
        });
    }


    private void setUpCamera() {
        imgCameraPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });


        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }


    public boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void captureImage() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // successfully captured the image
            // display it in image view
            previewCapturedImage();
        } else if (resultCode == RESULT_CANCELED) {
            // user cancelled Image capture
            Toast.makeText(getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();
        } else {
            // failed to capture image
            Toast.makeText(getApplicationContext(),
                    "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                    .show();
        }


        if (requestCode == REQUEST_BARCODE_CAPTURE && resultCode == RESULT_OK) {


            Log.e(LOG_TAG, "intent.getStringExtra(\"SCAN_RESULT\")   " + data.getStringExtra("SCAN_RESULT"));

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            String contents = data.getStringExtra(MainUtility.getCONTENT());
            if (etRouterCode != null) {
                if (contents != null)
                    etRouterCode.setText(contents);
//                scannerCode = contents;
            }
        }
    }


    private void previewCapturedImage() {
        try {
            // hide video preview

            imgCameraPhoto.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            Bitmap bitmapTimeStamp = MainUtility.getBitmapWithTimeStamp(bitmap);

            imgCameraPhoto.setImageBitmap(bitmapTimeStamp);
            stringOfImage = "data:image/png;base64," + MainUtility.getBase64Image(bitmapTimeStamp);
            Logger.logError(LOG_TAG, "String of image" + stringOfImage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }


    private void setUpAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvAssignedSites.setLayoutManager(mLayoutManager);
        rvAssignedSites.setItemAnimator(new DefaultItemAnimator());

        final QuestionListRequest questionListRequest = new QuestionListRequest();
        questionListRequest.setSectionId(SelectedSectionUtility.getInstance().getSectionID());
        questionListRequest.setOrderId(SelectedTaskUtility.getInstance().getSiteID());

        callAPI(questionListRequest, new APIClientUtils(this));


        srlQuestionList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callAPI(questionListRequest, new APIClientUtils(QuestionActivity.this));

            }
        });


    }

    public void callAPI(QuestionListRequest questionListRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getAllQuestionList(),
                    new APIClientCallback<QuestionListResponse>() {

                        @Override
                        public void onSuccess(QuestionListResponse questionListResponse) {

                            hideLoadingView();
                            srlQuestionList.setRefreshing(false);


                            questionList = questionListResponse.getData();

                            if (questionList != null) {
                                questionAdapter = new QuestionAdapter(questionList, QuestionActivity.this);
                                rvAssignedSites.setAdapter(questionAdapter);
                            } else {
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            srlQuestionList.setRefreshing(false);
                            Logger.logError(LOG_TAG, "Exception " + e);
                        }

                        @NonNull
                        @Override
                        public Class<QuestionListResponse> getClassOfType() {
                            return QuestionListResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(questionListRequest)));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }


    }


    private void initializeMap() {

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        buildGoogleApiClient();
        mapFragment.getMapAsync(this);

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        System.out.println("ABC buildGoogleApiClient map was invoked: ");
    }


    @Override
    public void onMapReady(final GoogleMap map) {
//        getCurrentLatLong();
//        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(false);


        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            LatLng latLng = new LatLng(latitude, longitude);

            float zoomLevel = (float) 16.0; //This goes up to 21
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

            marker = map.addMarker(new MarkerOptions().position(latLng));

        }


        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onCameraMove() {
                LatLng center = map.getCameraPosition().target;
                marker.setPosition(center);
                tvBoxLat.setText(String.format("%.4f", center.latitude));
                latitude = String.format("%.4f", center.latitude);
                tvBoxLong.setText(String.format("%.4f", center.longitude));
                longitude = String.format("%.4f", center.longitude);
            }
        });
    }


    public void callSubmitQuestionAPI(AnswerRequest answerRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getSubmitAnswer(),
                    new APIClientCallback<MainResponse>() {

                        @Override
                        public void onSuccess(MainResponse MainResponse) {
                            adjustView();
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

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(answerRequest)));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    private void adjustView() {

        String answerType = SelectedQuestionUtility.getInstance().getType();

        switch (answerType) {
            case "Radio":

                Animation slide_down1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                viewQuestion.startAnimation(slide_down1);
                viewQuestion.setVisibility(View.GONE);
                viewMainQuestion.setVisibility(View.VISIBLE);
                resetValues();


                break;
            case "Photo":

                Animation slide_down2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                viewCamera.startAnimation(slide_down2);
                viewCamera.setVisibility(View.GONE);
                viewMainQuestion.setVisibility(View.VISIBLE);
                deleteRecursive(new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME));
                resetValues();


                break;

            case "Location":

                Animation slide_down3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                viewLocation.startAnimation(slide_down3);
                viewLocation.setVisibility(View.GONE);
                viewMainQuestion.setVisibility(View.VISIBLE);
                resetValues();

                break;

            case "TextInput":

                Animation slide_down4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                viewComment.startAnimation(slide_down4);
                viewComment.setVisibility(View.GONE);
                viewMainQuestion.setVisibility(View.VISIBLE);
                resetValues();


                break;

            case "Scanner":

                Animation slide_down5 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                viewBarcode.startAnimation(slide_down5);
                viewBarcode.setVisibility(View.GONE);
                viewMainQuestion.setVisibility(View.VISIBLE);
                resetValues();

                break;

            case "Signature":

                Animation slide_down6 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                viewSignature.startAnimation(slide_down6);
                viewSignature.setVisibility(View.GONE);
                viewMainQuestion.setVisibility(View.VISIBLE);
                resetValues();

                break;

            case "RouterConfiguration":

                Animation slide_down7 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                viewRouterConfiguration.startAnimation(slide_down7);
                viewRouterConfiguration.setVisibility(View.GONE);
                viewMainQuestion.setVisibility(View.VISIBLE);
                resetValues();


        }

        QuestionListRequest questionListRequest = new QuestionListRequest();
        questionListRequest.setSectionId(SelectedSectionUtility.getInstance().getSectionID());
        questionListRequest.setOrderId(SelectedTaskUtility.getInstance().getSiteID());

        callAPI(questionListRequest, new APIClientUtils(this));

    }


    @Override
    public void onBackPressed() {

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        if (viewQuestion.getVisibility() == View.VISIBLE) {
            viewQuestion.startAnimation(slide_down);
            viewQuestion.setVisibility(View.GONE);
            viewMainQuestion.setVisibility(View.VISIBLE);
            resetValues();
        } else if (viewCamera.getVisibility() == View.VISIBLE) {
            viewCamera.startAnimation(slide_down);
            viewCamera.setVisibility(View.GONE);
            viewMainQuestion.setVisibility(View.VISIBLE);
            resetValues();
        } else if (viewLocation.getVisibility() == View.VISIBLE) {
            viewLocation.startAnimation(slide_down);
            viewLocation.setVisibility(View.GONE);
            viewMainQuestion.setVisibility(View.VISIBLE);
            resetValues();
        } else if (viewComment.getVisibility() == View.VISIBLE) {
            viewComment.startAnimation(slide_down);
            viewComment.setVisibility(View.GONE);
            viewMainQuestion.setVisibility(View.VISIBLE);
            resetValues();
        } else if (viewBarcode.getVisibility() == View.VISIBLE) {
            viewBarcode.startAnimation(slide_down);
            viewBarcode.setVisibility(View.GONE);
            viewMainQuestion.setVisibility(View.VISIBLE);
            resetValues();
        } else if (viewSignature.getVisibility() == View.VISIBLE) {
            viewSignature.startAnimation(slide_down);
            viewSignature.setVisibility(View.GONE);
            viewMainQuestion.setVisibility(View.VISIBLE);
            resetValues();
        } else if (viewRouterConfiguration.getVisibility() == View.VISIBLE) {
            viewRouterConfiguration.startAnimation(slide_down);
            viewRouterConfiguration.setVisibility(View.GONE);
            viewMainQuestion.setVisibility(View.VISIBLE);
            resetValues();
        } else {
            super.onBackPressed();
            SectionListActivity.launch(this);
        }
    }

    @OnClick({R.id.btnSubmitLocation, R.id.btnSubmitComment, R.id.btnSubmitQuestion, R.id.btnSubmitCamera,
            R.id.btn_router_barcode, R.id.btnSubmitBarcode, R.id.btn_clear, R.id.btn_save, R.id.btnSubmitSignature
            , R.id.btn_configure, R.id.btn_submit, R.id.btn_upload, R.id.btnSubmitRouterConfig, R.id.ib_info_barcode,
            R.id.ib_info_camera, R.id.ib_info_comment, R.id.ib_info_question, R.id.ib_info_router_config, R.id.ib_info_signature})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.btnSubmitLocation:

                showLoadingView(R.id.please_wait);

                gps.getLocation();
                currentLongitude = gps.getLongitude();
                currentLatitude = gps.getLatitude();

                AnswerRequest answerRequestLocation = new AnswerRequest();
                answerRequestLocation.setTaskId(SelectedQuestionUtility.getInstance().getTaskId());
                answerRequestLocation.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                answerRequestLocation.setAnswerId(SelectedQuestionUtility.getInstance().getFirstAnswerId());
                answerRequestLocation.setValue("");
                answerRequestLocation.setLatitude(tvBoxLat.getText().toString());
                answerRequestLocation.setLongitude(tvBoxLong.getText().toString());
                answerRequestLocation.setHardwareAt(SelectedQuestionUtility.getInstance().isHardwareAt());


                if (rgLocation.getVisibility() == View.VISIBLE) {

                    int radioButtonID = rgLocation.getCheckedRadioButtonId();
                    View radioButton = rgLocation.findViewById(radioButtonID);
                    int idx = rgLocation.indexOfChild(radioButton);

                    RadioButton r = (RadioButton) rgLocation.getChildAt(idx);
                    String selectedtext = r.getText().toString();
                    answerRequestLocation.setIsPAss(selectedtext);
                }

                callSubmitQuestionAPI(answerRequestLocation, new APIClientUtils(this));

                break;
            case R.id.btnSubmitComment:

                showLoadingView(R.id.please_wait);

                gps.getLocation();
                currentLongitude = gps.getLongitude();
                currentLatitude = gps.getLatitude();

                AnswerRequest answerRequestComment = new AnswerRequest();
                answerRequestComment.setTaskId(SelectedQuestionUtility.getInstance().getTaskId());
                answerRequestComment.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                answerRequestComment.setValue(edtComment.getText().toString());
                answerRequestComment.setAnswerId(SelectedQuestionUtility.getInstance().getFirstAnswerId());
                answerRequestComment.setLatitude(String.valueOf(currentLatitude));
                answerRequestComment.setLongitude(String.valueOf(currentLongitude));
                answerRequestComment.setHardwareAt(SelectedQuestionUtility.getInstance().isHardwareAt());

                if (rgComment.getVisibility() == View.VISIBLE) {

                    int radioButtonID = rgComment.getCheckedRadioButtonId();
                    View radioButton = rgComment.findViewById(radioButtonID);
                    int idx = rgComment.indexOfChild(radioButton);

                    RadioButton r = (RadioButton) rgComment.getChildAt(idx);
                    String selectedtext = r.getText().toString();
                    answerRequestComment.setIsPAss(selectedtext);
                }

                callSubmitQuestionAPI(answerRequestComment, new APIClientUtils(this));

                break;
            case R.id.btnSubmitQuestion:
                showLoadingView(R.id.please_wait);

                gps.getLocation();
                currentLongitude = gps.getLongitude();
                currentLatitude = gps.getLatitude();

                AnswerRequest answerRequestQuestion = new AnswerRequest();
                answerRequestQuestion.setTaskId(SelectedQuestionUtility.getInstance().getTaskId());
                answerRequestQuestion.setOrderId(SelectedTaskUtility.getInstance().getSiteID());

                if (SelectedQuestionUtility.getInstance().getAnswerlist().size() != 0) {
                    int selectedPosition = spinnerQuestion.getSelectedItemPosition();

                    Logger.logError(LOG_TAG, "selected position -> " + selectedPosition);

                    answerRequestQuestion.setAnswerId(SelectedQuestionUtility.getInstance().getSelectedAnswerId(selectedPosition));
                }

                answerRequestQuestion.setValue(edtQuestionComment.getText().toString());
                answerRequestQuestion.setLatitude(String.valueOf(currentLatitude));
                answerRequestQuestion.setLongitude(String.valueOf(currentLongitude));
                answerRequestQuestion.setHardwareAt(SelectedQuestionUtility.getInstance().isHardwareAt());

                if (rgQuestion.getVisibility() == View.VISIBLE) {

                    int radioButtonID = rgQuestion.getCheckedRadioButtonId();
                    View radioButton = rgQuestion.findViewById(radioButtonID);
                    int idx = rgQuestion.indexOfChild(radioButton);

                    RadioButton r = (RadioButton) rgQuestion.getChildAt(idx);
                    String selectedtext = r.getText().toString();
                    answerRequestQuestion.setIsPAss(selectedtext);
                }

                callSubmitQuestionAPI(answerRequestQuestion, new APIClientUtils(this));

                break;
            case R.id.btnSubmitCamera:

                showLoadingView(R.id.please_wait);

                gps.getLocation();
                currentLongitude = gps.getLongitude();
                currentLatitude = gps.getLatitude();

                AnswerRequest answerRequestCamera = new AnswerRequest();
                answerRequestCamera.setTaskId(SelectedQuestionUtility.getInstance().getTaskId());
                answerRequestCamera.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                answerRequestCamera.setValue(stringOfImage);
                answerRequestCamera.setAnswerId(SelectedQuestionUtility.getInstance().getFirstAnswerId());
                answerRequestCamera.setLatitude(String.valueOf(currentLatitude));
                answerRequestCamera.setLongitude(String.valueOf(currentLongitude));
                answerRequestCamera.setComment(etCommentPhoto.getText().toString());
                answerRequestCamera.setHardwareAt(SelectedQuestionUtility.getInstance().isHardwareAt());

                if (rgCamera.getVisibility() == View.VISIBLE) {

                    int radioButtonID = rgCamera.getCheckedRadioButtonId();
                    View radioButton = rgCamera.findViewById(radioButtonID);
                    int idx = rgCamera.indexOfChild(radioButton);

                    RadioButton r = (RadioButton) rgCamera.getChildAt(idx);
                    String selectedtext = r.getText().toString();
                    answerRequestCamera.setIsPAss(selectedtext);
                }


                callSubmitQuestionAPI(answerRequestCamera, new APIClientUtils(this));


                break;
            case R.id.btn_router_barcode:

                Intent intentRouter = new Intent(this, ScannerActivity.class);
                startActivityForResult(intentRouter, REQUEST_BARCODE_CAPTURE);

                break;
            case R.id.btnSubmitBarcode:

                showLoadingView(R.id.please_wait);

                gps.getLocation();
                currentLongitude = gps.getLongitude();
                currentLatitude = gps.getLatitude();

                AnswerRequest answerRequestBarcode = new AnswerRequest();
                answerRequestBarcode.setTaskId(SelectedQuestionUtility.getInstance().getTaskId());
                answerRequestBarcode.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                answerRequestBarcode.setValue(etRouterCode.getText().toString());
                answerRequestBarcode.setAnswerId(SelectedQuestionUtility.getInstance().getFirstAnswerId());
                answerRequestBarcode.setLatitude(String.valueOf(currentLatitude));
                answerRequestBarcode.setLongitude(String.valueOf(currentLongitude));
                answerRequestBarcode.setHardwareAt(SelectedQuestionUtility.getInstance().isHardwareAt());

                if (rgBarcode.getVisibility() == View.VISIBLE) {

                    int radioButtonID = rgBarcode.getCheckedRadioButtonId();
                    View radioButton = rgBarcode.findViewById(radioButtonID);
                    int idx = rgBarcode.indexOfChild(radioButton);

                    RadioButton r = (RadioButton) rgBarcode.getChildAt(idx);
                    String selectedtext = r.getText().toString();
                    answerRequestBarcode.setIsPAss(selectedtext);
                }

                callSubmitQuestionAPI(answerRequestBarcode, new APIClientUtils(this));
                break;
            case R.id.btn_clear:
                signaturePad.clear();
                signaturePad.setEnabled(true);
                break;
            case R.id.btn_save:
                btnSaveClicked = true;
                signaturePad.setEnabled(false);
                imgSignature = signaturePad.getSignatureBitmap();
                break;
            case R.id.btnSubmitSignature:

                if (btnSaveClicked) {

                    showLoadingView(R.id.please_wait);

                    gps.getLocation();
                    currentLongitude = gps.getLongitude();
                    currentLatitude = gps.getLatitude();

                    imgWithTimeStamp = MainUtility.getBitmapWithTimeStamp(imgSignature);
                    String base64image = "data:image/png;base64," + MainUtility.getBase64Image(imgSignature);

                    AnswerRequest answerRequestSignature = new AnswerRequest();
                    answerRequestSignature.setTaskId(SelectedQuestionUtility.getInstance().getTaskId());
                    answerRequestSignature.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                    answerRequestSignature.setValue(base64image);
                    answerRequestSignature.setAnswerId(SelectedQuestionUtility.getInstance().getFirstAnswerId());
                    answerRequestSignature.setLatitude(String.valueOf(currentLatitude));
                    answerRequestSignature.setLongitude(String.valueOf(currentLongitude));
                    answerRequestSignature.setHardwareAt(SelectedQuestionUtility.getInstance().isHardwareAt());

                    if (rgSignature.getVisibility() == View.VISIBLE) {

                        int radioButtonID = rgSignature.getCheckedRadioButtonId();
                        View radioButton = rgSignature.findViewById(radioButtonID);
                        int idx = rgSignature.indexOfChild(radioButton);

                        RadioButton r = (RadioButton) rgSignature.getChildAt(idx);
                        String selectedtext = r.getText().toString();
                        answerRequestSignature.setIsPAss(selectedtext);
                    }

                    callSubmitQuestionAPI(answerRequestSignature, new APIClientUtils(this));


                } else {
                    MainUtility.showMessage(btnSubmitSignature, "Please save the image");
                }
                break;

            case R.id.btn_configure:
                break;
            case R.id.btn_submit:
                break;
            case R.id.btn_upload:
                break;
            case R.id.btnSubmitRouterConfig:

                showLoadingView(R.id.please_wait);

                gps.getLocation();
                currentLongitude = gps.getLongitude();
                currentLatitude = gps.getLatitude();

                AnswerRequest answerRequestRouterConfig = new AnswerRequest();
                answerRequestRouterConfig.setTaskId(SelectedQuestionUtility.getInstance().getTaskId());
                answerRequestRouterConfig.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
                answerRequestRouterConfig.setAnswerId(SelectedQuestionUtility.getInstance().getFirstAnswerId());
                answerRequestRouterConfig.setLatitude(String.valueOf(currentLatitude));
                answerRequestRouterConfig.setLongitude(String.valueOf(currentLongitude));
                answerRequestRouterConfig.setComment(edtRouterCommand.getText().toString());
                answerRequestRouterConfig.setHardwareAt(SelectedQuestionUtility.getInstance().isHardwareAt());
                answerRequestRouterConfig.setOutput(tvResult.getText().toString());

                if (rgRouterConfig.getVisibility() == View.VISIBLE) {

                    int radioButtonID = rgRouterConfig.getCheckedRadioButtonId();
                    View radioButton = rgRouterConfig.findViewById(radioButtonID);
                    int idx = rgRouterConfig.indexOfChild(radioButton);

                    RadioButton r = (RadioButton) rgRouterConfig.getChildAt(idx);
                    String selectedtext = r.getText().toString();
                    answerRequestRouterConfig.setIsPAss(selectedtext);
                }


                callSubmitQuestionAPI(answerRequestRouterConfig, new APIClientUtils(this));


                break;

            case R.id.ib_info_barcode:
                initiatePopupWindow();
                break;

            case R.id.ib_info_camera:
                initiatePopupWindow();
                break;
            case R.id.ib_info_comment:
                initiatePopupWindow();
                break;
            case R.id.ib_info_question:
                initiatePopupWindow();
                break;

            case R.id.ib_info_router_config:
                initiatePopupWindow();

                break;
            case R.id.ib_info_signature:
                initiatePopupWindow();
                break;

        }
    }


    private PopupWindow pwindo;

    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            closePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            closePopup.setOnClickListener(cancel_button_click_listener);

            tvPopupText = (TextView) layout.findViewById(R.id.txtViewPopup);
            tvPopupText.setText(popupString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };


    public void showLoadingView(int message) {
        llProgressMain.setVisibility(View.VISIBLE);
        tvLoadingMessage.setText(MainUtility.getStringFromXml(this, message));
    }

    public void hideLoadingView() {
        llProgressMain.setVisibility(View.GONE);
    }


    public void openUsbDevice() {
        //before open usb device
        //should try to get usb permission
        tryGetUsbPermission();
    }

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private void tryGetUsbPermission() {
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbPermissionActionReceiver, filter);

        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        //here do emulation to ask all connected usb device for permission
        for (final UsbDevice usbDevice : mUsbManager.getDeviceList().values()) {
            //add some conditional check if necessary
            //if(isWeCaredUsbDevice(usbDevice)){
            if (mUsbManager.hasPermission(usbDevice)) {
                //if has already got permission, just goto connect it
                //that means: user has choose yes for your previously popup window asking for grant perssion for this usb device
                //and also choose option: not ask again
                afterGetUsbPermission(usbDevice);
            } else {
                //this line will let android popup window, ask user whether to allow this app to have permission to operate this usb device
                mUsbManager.requestPermission(usbDevice, mPermissionIntent);
            }
            //}
        }
    }


    private void afterGetUsbPermission(UsbDevice usbDevice) {
        //call method to set up device communication
//        Toast.makeText(this, String.valueOf("Got permission for usb device: " + usbDevice), Toast.LENGTH_LONG).show();
//        Log.e(LOG_TAG, "Got permission for usb device: " + usbDevice);
//        Toast.makeText(this, String.valueOf("Found USB device: VID=" + usbDevice.getVendorId() + " PID=" + usbDevice.getProductId()), Toast.LENGTH_LONG).show();

        Log.e(LOG_TAG, "Got permission for usb device: " + usbDevice);
        boolean hasPermision = mUsbManager.hasPermission(usbDevice);
        Log.e(LOG_TAG, "has Permission " + hasPermision);

        Log.e(LOG_TAG, "Found USB device: VID=" + usbDevice.getVendorId() + " PID=" + usbDevice.getProductId());
//        Toast.makeText(this, String.valueOf(hasPermission), Toast.LENGTH_LONG).show();
        doYourOpenUsbDevice(usbDevice);
    }

    private void doYourOpenUsbDevice(UsbDevice usbDevice) {
        //now follow line will NOT show: User has not given permission to device UsbDevice
        connection = mUsbManager.openDevice(usbDevice);
        //add your operation code here

        if (connection == null) {
            Log.e(LOG_TAG, "UsbDeviceConnection null");
        }

        ProbeTable customTable = new ProbeTable();
        customTable.addProduct(0x067B, 0x2303, ProlificSerialDriver.class);
        customTable.addProduct(0x04E2, 0x1410, CdcAcmSerialDriver.class);

        UsbSerialProber prober = new UsbSerialProber(customTable);

        List<UsbSerialDriver> availableDrivers = prober.findAllDrivers(mUsbManager);
        if (availableDrivers.isEmpty()) {

            Log.e(LOG_TAG, "No available drivers");

            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
//        mUsbManager.requestPermission(driver.getDevice(), mPermissionIntent);
        boolean hasPermission = mUsbManager.hasPermission(driver.getDevice());

        Log.e(LOG_TAG, "is permission " + hasPermission);

        if (connection == null) {

            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            return;
        }


        sPort = driver.getPorts().get(0);


        boundRate = Integer.parseInt(preferences.getString("boundRate", "9600"));

        dataBits = Integer.parseInt(preferences.getString("dataBits", "8"));

        String parityStr = preferences.getString("parity", "NONE");


        if (parityStr.equalsIgnoreCase("ODD")) {
            parity = UsbSerialPort.PARITY_ODD;
        } else if (parityStr.equalsIgnoreCase("EVEN")) {
            parity = UsbSerialPort.PARITY_EVEN;
        } else if (parityStr.equalsIgnoreCase("NONE")) {
            parity = UsbSerialPort.PARITY_NONE;
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please select valid parity from settings", Toast.LENGTH_SHORT).show();
        }


        String stopBitsStr = preferences.getString("stopBits", "1");

        if (stopBitsStr.equalsIgnoreCase("1")) {
            stopBits = UsbSerialPort.STOPBITS_1;
        } else if (stopBitsStr.equalsIgnoreCase("1.5")) {
            stopBits = UsbSerialPort.STOPBITS_1_5;
        } else if (stopBitsStr.equalsIgnoreCase("2")) {
            stopBits = UsbSerialPort.STOPBITS_2;
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please select valid stop bits from settings", Toast.LENGTH_SHORT).show();
        }


        try {
            sPort.open(connection);
            sPort.setParameters(boundRate, dataBits, stopBits, parity);
            sPort.setRTS(true);
            sPort.setDTR(true);

            tvResult.append("Connected to device.\n");
            svDemoScroller.smoothScrollTo(0, tvResult.getBottom());

//            showStatus(mDumpTextView, "CD  - Carrier Detect", sPort.getCD());
//            showStatus(mDumpTextView, "CTS - Clear To Send", sPort.getCTS());
//            showStatus(mDumpTextView, "DSR - Data Set Ready", sPort.getDSR());
//            showStatus(mDumpTextView, "DTR - Data Terminal Ready", sPort.getDTR());
//            showStatus(mDumpTextView, "DSR - Data Set Ready", sPort.getDSR());
//            showStatus(mDumpTextView, "RI  - Ring Indicator", sPort.getRI());
//            showStatus(mDumpTextView, "RTS - Request To Send", sPort.getRTS());

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error setting up device: " + e.getMessage(), e);
            tvResult.append("Error opening device: " + e.getMessage());
            svDemoScroller.smoothScrollTo(0, tvResult.getBottom());
            try {
                sPort.close();
            } catch (IOException e2) {
                // Ignore.
            }
            sPort = null;
            return;
        }
        tvResult.append("Serial device: " + sPort.getClass().getSimpleName());
        svDemoScroller.smoothScrollTo(0, tvResult.getBottom());

        onDeviceStateChange();

        Log.e(LOG_TAG, "Setting: " + boundRate + " " + dataBits + " " + parity + " " + stopBits);

//        if (preferences.getString("boundRate", null) != null) {
//
//            boundRate = Integer.parseInt(preferences.getString("boundRate", null));
//
//
//            if (preferences.getString("dataBits", null) != null) {
//
//                dataBits = Integer.parseInt(preferences.getString("dataBits", null));
//
//
//                if (preferences.getString("parity", null) != null) {
//
//                    String parityStr = preferences.getString("parity", null);
//
//                    if (parityStr.equalsIgnoreCase("ODD")) {
//                        parity = UsbSerialPort.PARITY_ODD;
//                    } else if (parityStr.equalsIgnoreCase("EVEN")) {
//                        parity = UsbSerialPort.PARITY_EVEN;
//                    } else if (parityStr.equalsIgnoreCase("NONE")) {
//                        parity = UsbSerialPort.PARITY_NONE;
//                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                "Please select valid parity from settings", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                    if (preferences.getString("stopBits", null) != null) {
//
//                        String stopBitsStr = preferences.getString("stopBits", null);
//
//                        if (stopBitsStr.equalsIgnoreCase("1")) {
//                            stopBits = UsbSerialPort.STOPBITS_1;
//                        } else if (stopBitsStr.equalsIgnoreCase("1.5")) {
//                            stopBits = UsbSerialPort.STOPBITS_1_5;
//                        } else if (stopBitsStr.equalsIgnoreCase("2")) {
//                            stopBits = UsbSerialPort.STOPBITS_2;
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "Please select valid stop bits from settings", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                        try {
//                            sPort.open(connection);
//                            sPort.setParameters(boundRate, dataBits, stopBits, parity);
//                            sPort.setRTS(true);
//                            sPort.setDTR(true);
//
//                            tvResult.append("Connected to device.\n");
//                            svDemoScroller.smoothScrollTo(0, tvResult.getBottom());
//
////            showStatus(mDumpTextView, "CD  - Carrier Detect", sPort.getCD());
////            showStatus(mDumpTextView, "CTS - Clear To Send", sPort.getCTS());
////            showStatus(mDumpTextView, "DSR - Data Set Ready", sPort.getDSR());
////            showStatus(mDumpTextView, "DTR - Data Terminal Ready", sPort.getDTR());
////            showStatus(mDumpTextView, "DSR - Data Set Ready", sPort.getDSR());
////            showStatus(mDumpTextView, "RI  - Ring Indicator", sPort.getRI());
////            showStatus(mDumpTextView, "RTS - Request To Send", sPort.getRTS());
//
//                        } catch (IOException e) {
//                            Log.e(LOG_TAG, "Error setting up device: " + e.getMessage(), e);
//                            tvResult.append("Error opening device: " + e.getMessage());
//                            svDemoScroller.smoothScrollTo(0, tvResult.getBottom());
//                            try {
//                                sPort.close();
//                            } catch (IOException e2) {
//                                // Ignore.
//                            }
//                            sPort = null;
//                            return;
//                        }
//                        tvResult.append("Serial device: " + sPort.getClass().getSimpleName());
//                        svDemoScroller.smoothScrollTo(0, tvResult.getBottom());
//
//                        onDeviceStateChange();
//
//                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                "Please select Stop bits from settings", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "Please select Parity bits from settings", Toast.LENGTH_SHORT).show();
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(),
//                        "Please select Data bits from settings", Toast.LENGTH_SHORT).show();
//            }
//
//        } else {
//            Toast.makeText(getApplicationContext(),
//                    "Please select Bound rate from settings", Toast.LENGTH_SHORT).show();
//        }
//
//        Log.e(LOG_TAG, "Setting: " + boundRate + " " + dataBits + " " + parity + " " + stopBits);
    }


    private void stopIoManager() {
        if (mSerialIoManager != null) {
            Log.e(LOG_TAG, "Stopping io manager ..");
            mSerialIoManager.stop();
            mSerialIoManager = null;
        }
    }

    private void startIoManager() {
        if (sPort != null) {
            Log.e(LOG_TAG, "Starting io manager ..");
            mSerialIoManager = new SerialInputOutputManager(sPort, mListener);
            mExecutor.submit(mSerialIoManager);
        }
    }

    private void onDeviceStateChange() {
        stopIoManager();
        startIoManager();
    }

    private void updateReceivedData(byte[] data) {

        final String decodedData = new String(data);  // Create new String Object and assign byte[] to it
//        Log.e(LOG_TAG, "Text Decrypted : " + decodedData);

        tvResult.append(decodedData);
        svDemoScroller.smoothScrollTo(0, tvResult.getBottom());
    }


    private final BroadcastReceiver mUsbPermissionActionReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        //user choose YES for your previously popup window asking for grant permission for this usb device
                        if (null != usbDevice) {
                            afterGetUsbPermission(usbDevice);
                        }
                    } else {
                        //user choose NO for your previously popup window asking for grant permission for this usb device
                        //Toast.makeText(context, String.valueOf("Permission denied for device" + usbDevice), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private SerialInputOutputManager mSerialIoManager;

    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.e(LOG_TAG, "Runner stopped." + e.toString());
                }

                @Override
                public void onNewData(final byte[] data) {
                    QuestionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            QuestionActivity.this.updateReceivedData(data);
                        }
                    });
                }
            };


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.setting_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getTitle().toString().equalsIgnoreCase("Baud Rate")) {

                    showBondRatePopup();

                } else if (menuItem.getTitle().toString().equalsIgnoreCase("Data Bits")) {

                    showDataBitsPopup();

                } else if (menuItem.getTitle().toString().equalsIgnoreCase("Parity")) {

                    showParityPopup();

                } else if (menuItem.getTitle().toString().equalsIgnoreCase("Stop Bits")) {
                    showStopBitsPopup();
                }


                return true;
            }
        });
        popup.show();
    }

    private void showStopBitsPopup() {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle("Select Stop Bits");


        final String[] myStringArray = {"1", "1.5", "2"};


        String stopBitsStr = preferences.getString("stopBits", "1");

        if (stopBitsStr.equalsIgnoreCase("1")) {
            stopBitsValue = 0;
        } else if (stopBitsStr.equalsIgnoreCase("1.5")) {
            stopBitsValue = 1;
        } else if (stopBitsStr.equalsIgnoreCase("2")) {
            stopBitsValue = 2;
        }


        alt_bld.setSingleChoiceItems(myStringArray, stopBitsValue, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                editor = preferences.edit();
                editor.putString("stopBits", myStringArray[item]);
                editor.apply();

                dialog.dismiss();// dismiss the alertbox after chose option
                openUsbDevice();

            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();

    }

    private void showParityPopup() {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle("Select Parity Bits");


        final String[] myStringArray = {"EVEN", "ODD", "NONE"};


        String parityStr = preferences.getString("parity", "NONE");

        if (parityStr.equalsIgnoreCase("EVEN")) {
            parityvalue = 0;
        } else if (parityStr.equalsIgnoreCase("ODD")) {
            parityvalue = 1;
        } else if (parityStr.equalsIgnoreCase("NONE")) {
            parityvalue = 2;
        }


        alt_bld.setSingleChoiceItems(myStringArray, parityvalue, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                editor = preferences.edit();
                editor.putString("parity", myStringArray[item]);
                editor.apply();

                dialog.dismiss();// dismiss the alertbox after chose option
                openUsbDevice();

            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();

    }

    private void showDataBitsPopup() {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle("Select Data Bits");


        final String[] myStringArray = {"5", "6", "7", "8"};


        String dataBitsStr = preferences.getString("dataBits", "8");


        if (dataBitsStr.equalsIgnoreCase("5")) {
            dataBitsValue = 0;
        } else if (dataBitsStr.equalsIgnoreCase("6")) {
            dataBitsValue = 1;
        } else if (dataBitsStr.equalsIgnoreCase("7")) {
            dataBitsValue = 2;
        } else if (dataBitsStr.equalsIgnoreCase("8")) {
            dataBitsValue = 3;
        }


        alt_bld.setSingleChoiceItems(myStringArray, dataBitsValue, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                editor = preferences.edit();
                editor.putString("dataBits", myStringArray[item]);
                editor.apply();

                dialog.dismiss();// dismiss the alertbox after chose option
                openUsbDevice();

            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();

    }

    private void showBondRatePopup() {


        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle("Select Baud Rate");


        final String[] myStringArray = {"2400", "9600", "19200", "38400", "57600", "115200"};


        String boundRateStr = preferences.getString("boundRate", "9600");

        if (boundRateStr.equalsIgnoreCase("2400")) {
            boundRateValue = 0;
        } else if (boundRateStr.equalsIgnoreCase("9600")) {
            boundRateValue = 1;
        } else if (boundRateStr.equalsIgnoreCase("19200")) {
            boundRateValue = 2;
        } else if (boundRateStr.equalsIgnoreCase("38400")) {
            boundRateValue = 3;
        } else if (boundRateStr.equalsIgnoreCase("57600")) {
            boundRateValue = 4;
        } else if (boundRateStr.equalsIgnoreCase("115200")) {
            boundRateValue = 5;
        }


        alt_bld.setSingleChoiceItems(myStringArray, boundRateValue, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                editor = preferences.edit();
                editor.putString("boundRate", myStringArray[item]);
                editor.apply();

                dialog.dismiss();// dismiss the alertbox after chose option
                openUsbDevice();

            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();

    }


    public String showUserNameEditTextPopup() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Please enter your username");

        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
//                Editable passwordEditTextValue = edittext.getText();
                //OR
                userNameEditTextValue = edittext.getText().toString() + "\n";

                byte[] bytes = userNameEditTextValue.getBytes();

                if (sPort != null) {

                    try {
                        sPort.write(bytes, 1000);
                        Log.e(LOG_TAG, "inside write");
//                    mExecutor.submit(mSerialIoManager);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Exception " + e);
                    }
                } else {
                    Log.e(LOG_TAG, "UsbSerialPort is null");
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();

        return userNameEditTextValue;

    }


    public String showPasswordEditTextPopup() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Please enter your password");

        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
//                Editable passwordEditTextValue = edittext.getText();
                //OR
                passwordEditTextValue = edittext.getText().toString() + "\n";

                byte[] bytes = passwordEditTextValue.getBytes();

                if (sPort != null) {

                    try {
                        sPort.write(bytes, 1000);
                        Log.e(LOG_TAG, "inside write");
//                    mExecutor.submit(mSerialIoManager);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Exception " + e);
                    }
                } else {
                    Log.e(LOG_TAG, "UsbSerialPort is null");
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.dismiss();
            }
        });

        alert.show();

        return passwordEditTextValue;

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
