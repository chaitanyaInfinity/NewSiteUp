package com.example.infinitylabs.dynamictrackerapp.request_response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "msg",
        "data",
        "isLogin",
        "message"
})
public class TrackingResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private List<TrackingData> data = null;
    @JsonProperty("isLogin")
    private Boolean isLogin;
    @JsonProperty("message")
    private String message;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("data")
    public List<TrackingData> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<TrackingData> data) {
        this.data = data;
    }

    @JsonProperty("isLogin")
    public Boolean getIsLogin() {
        return isLogin;
    }

    @JsonProperty("isLogin")
    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}