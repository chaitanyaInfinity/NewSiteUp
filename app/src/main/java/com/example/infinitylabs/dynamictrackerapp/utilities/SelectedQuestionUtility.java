package com.example.infinitylabs.dynamictrackerapp.utilities;

import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionData;

import java.util.ArrayList;

/**
 * Created by infinitylabs on 6/7/17.
 */


public class SelectedQuestionUtility {
    public static final String LOG_TAG = "SelectedQuestionUtility";
    private static SelectedQuestionUtility instance;


    ArrayList<String> answerlist;
    QuestionData selectedQuestionData;


    private SelectedQuestionUtility() {
        selectedQuestionData = new QuestionData();
    }

    public static SelectedQuestionUtility getInstance() {
        if (instance == null) {
            instance = new SelectedQuestionUtility();
        }
        return instance;
    }

    public void setSelectedQuestionData(QuestionData selectedQuestionData) {
        this.selectedQuestionData = selectedQuestionData;
    }

    public String getTaskId() {
        return selectedQuestionData.getTaskId();
    }

    public String getTask() {
        return selectedQuestionData.getTask();
    }

    public String getType() {
        return selectedQuestionData.getType();
    }


    public String getFirstAnswerId() {
        if (selectedQuestionData.getAnswer().get(0) != null) {
            return selectedQuestionData.getAnswer().get(0).getId();
        } else {
            return "";
        }
    }

    public String getDescription(){
        Logger.logError("aaaaaaaaaaaaaaaaaaa",""+selectedQuestionData.getDescription());
        if(selectedQuestionData.getDescription()!=null){
            return selectedQuestionData.getDescription();
        }else {
            return "";
        }
    }


    public String getFirstAnswer() {
        if (selectedQuestionData.getAnswer().get(0) != null) {
            return selectedQuestionData.getAnswer().get(0).getAnswer();
        } else {
            return "";
        }
    }


    public String getSelectedAnswerId(int selectedPosition) {
        if (selectedQuestionData.getAnswer().get(selectedPosition) != null) {
            return selectedQuestionData.getAnswer().get(selectedPosition).getId();
        } else {
            return "";
        }
    }


    public Boolean isHardwareAt() {
        return selectedQuestionData.getHarwareAt();
    }

    public ArrayList getAnswerlist() {

        answerlist = new ArrayList<>();

        for (int i = 0; i < selectedQuestionData.getAnswer().size(); i++) {
            if (selectedQuestionData.getAnswer().get(i).getAnswer() != null) {
                answerlist.add(selectedQuestionData.getAnswer().get(i).getAnswer());
            }
        }

        Logger.logError(LOG_TAG, "AnswerList " + answerlist.toString());

        return answerlist;

    }


}




