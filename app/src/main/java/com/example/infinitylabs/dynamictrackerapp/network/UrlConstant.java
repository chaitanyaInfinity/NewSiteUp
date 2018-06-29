package com.example.infinitylabs.dynamictrackerapp.network;


public class UrlConstant {

    //    private static String baseUrl = BuildConfig.BASE_URL;
//    private static String baseUrl = "http://192.168.1.33/ip-ran/ip-ran/web/index.php/taskapi/";
    private static String baseUrl = "https://120.138.116.45:8443/idea-ip-ran/web/index.php/taskapi/";

    private static final String LOG_TAG = "UrlConstant";
    private static final String getOTP = "user/mobile-authentication";
    private static final String createNewPassword = "user/create-new-password";
    private static final String login = "user/login";
    private static final String tracking = "servey/site-progress-status";
    private static final String submitSurveyForm = "servey/submit-site-survey";
    private static final String getBOQform = "servey/get-boq-form";
    private static final String getIRMform = "servey/get-irm-form";
    private static final String submitBOQIRMform = "servey/save-open-case-inspection-details";
    private static final String getNipFile = "servey/get-nip-file";
    private static final String submitNipResult = "servey/save-nip-output";


    private static final String siteApproval = "task-details/update-site-status";
    private static final String sectionList = "task-details/get-all-sections";
    private static final String dummySitesList = "task-details/get-engg-sites";
    private static final String allQuestionList = "task-details/get-section-tasks";
    private static final String getQuestionforId = "task-details/get-task";
    private static final String submitAnswer = "task-details/save-answer";
    private static final String submitIssue = "user/report-issue";
    private static final String taskPreview = "sitepreview/survey-preview";

    public static String getTaskPreview() {
        return baseUrl + taskPreview;
    }

    public static String getSectionList() {
        return baseUrl + sectionList;
    }

    public static String getDummySitesList() {
        return baseUrl + dummySitesList;
    }

    public static String getAllQuestionList() {
        return baseUrl + allQuestionList;
    }

    public static String getGetQuestionforId() {
        return baseUrl + getQuestionforId;
    }

    public static String getSubmitAnswer() {
        return baseUrl + submitAnswer;
    }

    public static String getLogin() {
        return baseUrl + login;
    }

    public static String getSiteApproval() {
        return baseUrl + siteApproval;
    }

    public static String getSubmitIssue() {
        return baseUrl + submitIssue;
    }


    public static String getGetOTP() {
        return baseUrl + getOTP;
    }

    public static String getCreateNewPassword() {
        return baseUrl + createNewPassword;
    }

    public static String getTracking() {
        return baseUrl + tracking;
    }

    public static String getSubmitSurveyForm() {
        return baseUrl + submitSurveyForm;
    }

    public static String getGetBOQform() {
        return baseUrl + getBOQform;
    }

    public static String getGetIRMform() {
        return baseUrl + getIRMform;
    }

    public static String getSubmitBOQIRMform() {
        return baseUrl + submitBOQIRMform;
    }

    public static String getGetNipFile() {
        return baseUrl + getNipFile;
    }

    public static String getSubmitNipResult() {
        return baseUrl + submitNipResult;
    }
}
