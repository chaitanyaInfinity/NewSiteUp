package com.example.infinitylabs.dynamictrackerapp.request_response;

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
        "name",
        "status",
        "details",
        "access",
        "form_id",
        "is_hardwareat"
})
public class TrackingData {

    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("details")
    private String details;
    @JsonProperty("form_id")
    private String formId;
    @JsonProperty("access")
    private Boolean access;

    public Boolean getHardwareAT() {
        return isHardwareAT;
    }

    public void setHardwareAT(Boolean hardwareAT) {
        isHardwareAT = hardwareAT;
    }

    @JsonProperty("is_hardwareat")
    private Boolean isHardwareAT;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    @JsonProperty("details")
    public void setDetails(String details) {
        this.details = details;
    }


    @JsonProperty("form_id")
    public String getFormId() {
        return formId;
    }

    @JsonProperty("form_id")
    public void setFormId(String formId) {
        this.formId = formId;
    }


    @JsonProperty("access")
    public Boolean getAccess() {
        return access;
    }

    @JsonProperty("access")
    public void setAccess(Boolean access) {
        this.access = access;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "TrackingData{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", details='" + details + '\'' +
                ", access=" + access +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}