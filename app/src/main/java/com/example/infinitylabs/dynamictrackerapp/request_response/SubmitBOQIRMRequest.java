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
"site_id",
"form_type",
"data"
})
public class SubmitBOQIRMRequest {

@JsonProperty("site_id")
private String siteId;
@JsonProperty("form_type")
private String formType;
@JsonProperty("data")
private List<BOQFormData> data = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("site_id")
public String getSiteId() {
return siteId;
}

@JsonProperty("site_id")
public void setSiteId(String siteId) {
this.siteId = siteId;
}

@JsonProperty("form_type")
public String getFormType() {
return formType;
}

@JsonProperty("form_type")
public void setFormType(String formType) {
this.formType = formType;
}

@JsonProperty("data")
public List<BOQFormData> getData() {
return data;
}

@JsonProperty("data")
public void setData(List<BOQFormData> data) {
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