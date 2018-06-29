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
"status",
"msg",
"data"
})
public class OtpResponse {

@JsonProperty("status")
private Boolean status;
@JsonProperty("msg")
private String msg;
@JsonProperty("data")
private OtpData data;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("status")
public Boolean getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(Boolean status) {
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
public OtpData getData() {
return data;
}

@JsonProperty("data")
public void setData(OtpData data) {
this.data = data;
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