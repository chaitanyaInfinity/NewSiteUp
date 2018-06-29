package com.example.infinitylabs.dynamictrackerapp.view.nip_upload_form;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.NipResultRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.NipResultResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.NipUploadRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.NipUploadResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.MainUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.example.infinitylabs.dynamictrackerapp.view.tracking.TrackingActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.ProlificSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NipUploadActivity extends AppCompatActivity {

    private static final String LOG_TAG = NipUploadActivity.class.getSimpleName();

    public static void launch(Context context) {
        context.startActivity(new Intent(context, NipUploadActivity.class));
    }


    @BindView(R.id.tv_question_router_config)
    TextView tvQuestionRouterConfig;
    @BindView(R.id.ib_info_router_config)
    ImageButton ibInfoRouterConfig;
    @BindView(R.id.ib_setting)
    ImageButton ibSetting;
    @BindView(R.id.btn_enable)
    Button btnEnable;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.btn_configure)
    Button btnConfigure;
    @BindView(R.id.btn_submit)
    ImageButton btnSubmit;
    @BindView(R.id.btn_username)
    Button btnUsername;
    @BindView(R.id.btn_password)
    Button btnPassword;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.sv_demoScroller)
    ScrollView svDemoScroller;
    @BindView(R.id.tv_input_command)
    EditText tvInputCommand;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.edt_router_command)
    EditText edtRouterCommand;
    @BindView(R.id.rbPassedRouter)
    RadioButton rbPassedRouter;
    @BindView(R.id.rbFailedRouter)
    RadioButton rbFailedRouter;
    @BindView(R.id.rg_router_config)
    RadioGroup rgRouterConfig;
    @BindView(R.id.btnSubmitRouterConfig)
    Button btnSubmitRouterConfig;


    private static UsbSerialPort sPort = null;
    private static UsbManager mUsbManager;
    private static UsbDeviceConnection connection;
    @BindView(R.id.tv_loading_message)
    TextView tvLoadingMessage;
    @BindView(R.id.ll_progress_main)
    LinearLayout llProgressMain;


    private int boundRate;
    private int dataBits;
    private int parity;
    private int stopBits;

    int boundRateValue = 0;
    int dataBitsValue = 0;
    int parityvalue = 0;
    int stopBitsValue = 0;
    public String command = "\n";
    String passwordEditTextValue = "";
    String userNameEditTextValue = "";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nip_upload);
        ButterKnife.bind(this);

        preferences = this.getSharedPreferences("settingPreference", MODE_PRIVATE);
        setupRouterConfig();

        NipUploadRequest nipUploadRequest = new NipUploadRequest();
        nipUploadRequest.setSiteId(SelectedTaskUtility.getInstance().getSiteID());
        getNipFileToUpload(nipUploadRequest, new APIClientUtils(this));

    }


    public void showLoadingView(int message) {
        llProgressMain.setVisibility(View.VISIBLE);
        tvLoadingMessage.setText(MainUtility.getStringFromXml(this, message));
    }

    public void hideLoadingView() {
        llProgressMain.setVisibility(View.GONE);
    }


    public void getNipFileToUpload(final NipUploadRequest nipUploadRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getGetNipFile(),
                    new APIClientCallback<NipUploadResponse>() {

                        @Override
                        public void onSuccess(NipUploadResponse nipUploadResponse) {


                            if (nipUploadResponse.getStatus()) {

                                hideLoadingView();
//                                SelectedTaskUtility.getInstance().setNipToUpload(nipUploadResponse.getData());
                                command = "\n" + nipUploadResponse.getData() + "\n";
                                openUsbDevice();

                            } else {
                                hideLoadingView();
                                MainUtility.showMessage(btnSubmit, nipUploadResponse.getMsg());
                            }
                        }


                        @Override
                        public void onFailure(Exception e) {
                            Logger.logError(LOG_TAG, "Exception  " + e.toString());
                            hideLoadingView();
                            MainUtility.showMessage(btnSubmit, "Oops, Something went wrong.");
                        }

                        @NonNull
                        @Override
                        public Class<NipUploadResponse> getClassOfType() {
                            return NipUploadResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(nipUploadRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    public void submitNipResult(final NipResultRequest nipResultRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getSubmitNipResult(),
                    new APIClientCallback<NipResultResponse>() {

                        @Override
                        public void onSuccess(NipResultResponse nipResultResponse) {


                            if (nipResultResponse.getStatus()) {

                                hideLoadingView();
                                MainUtility.showMessage(btnSubmit, nipResultResponse.getMsg());
                                TrackingActivity.launch(NipUploadActivity.this);
//

                            } else {
                                hideLoadingView();
                                MainUtility.showMessage(btnSubmit, nipResultResponse.getMsg());
                            }
                        }


                        @Override
                        public void onFailure(Exception e) {
                            Logger.logError(LOG_TAG, "Exception  " + e.toString());
                            hideLoadingView();
                            MainUtility.showMessage(btnSubmit, "Oops, Something went wrong.");
                        }

                        @NonNull
                        @Override
                        public Class<NipResultResponse> getClassOfType() {
                            return NipResultResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(nipResultRequest)));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }

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


    private void setupRouterConfig() {


        btnUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getLastnCharacters(tvResult.getText().toString(), 10).contains("Username:")) {
                    showUserNameEditTextPopup();
                } else {
                    Toast.makeText(NipUploadActivity.this, "Only valid for Username.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnSubmitRouterConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NipResultRequest nipResultRequest = new NipResultRequest();
                nipResultRequest.setSiteId(SelectedTaskUtility.getInstance().getSiteID());
                nipResultRequest.setOutput(tvResult.getText().toString());
                submitNipResult(nipResultRequest, new APIClientUtils(NipUploadActivity.this));

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
                View view = NipUploadActivity.this.getCurrentFocus();
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
                    Toast.makeText(NipUploadActivity.this, "Only valid for Password.", Toast.LENGTH_SHORT).show();
                }


            }
        });


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
                    NipUploadActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NipUploadActivity.this.updateReceivedData(data);
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
}
