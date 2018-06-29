package com.example.infinitylabs.dynamictrackerapp.request_response;

/**
 * Created by infinitylabs on 6/7/17.
 */

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "task_id",
        "site_id",
        "answer_id",
        "value",
        "latitude",
        "longitude",
        "is_pass",
        "output",
        "is_harwareat",
        "comment"
})
public class AnswerRequest {

    @JsonProperty("task_id")
    private String taskId;
    @JsonProperty("site_id")
    private String orderId;
    @JsonProperty("answer_id")
    private String answerId;
    @JsonProperty("value")
    private String value;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("is_pass")
    private String isPAss;

    @JsonProperty("output")
    private String output;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("is_harwareat")
    private Boolean isHardwareAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("task_id")
    public String getTaskId() {
        return taskId;
    }

    @JsonProperty("task_id")
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @JsonProperty("site_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("site_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("answer_id")
    public String getAnswerId() {
        return answerId;
    }

    @JsonProperty("answer_id")
    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonProperty("is_pass")
    public String getIsPAss() {
        return isPAss;
    }

    @JsonProperty("is_pass")
    public void setIsPAss(String isPAss) {
        this.isPAss = isPAss;
    }

    @JsonProperty("output")
    public String getOutput() {
        return output;
    }

    @JsonProperty("output")
    public void setOutput(String output) {
        this.output = output;
    }

    @JsonProperty("is_harwareat")
    public Boolean getHardwareAt() {
        return isHardwareAt;
    }

    @JsonProperty("is_harwareat")
    public void setHardwareAt(Boolean hardwareAt) {
        isHardwareAt = hardwareAt;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }
}
