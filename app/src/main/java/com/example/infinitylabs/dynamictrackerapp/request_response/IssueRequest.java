package com.example.infinitylabs.dynamictrackerapp.request_response;

/**
 * Created by infinitylabs on 20/7/17.
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
        "site_id",
        "issue",
        "photo",
        "form_id"
})
public class IssueRequest {

    @JsonProperty("site_id")
    private String orderId;
    @JsonProperty("form_id")
    private String formId;
    @JsonProperty("issue")
    private String issue;
    @JsonProperty("photo")
    private String photo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("site_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("site_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("form_id")
    public String getFormId() {
        return formId;
    }

    @JsonProperty("form_id")
    public void setFormId(String formId) {
        this.formId = formId;
    }

    @JsonProperty("issue")
    public String getIssue() {
        return issue;
    }

    @JsonProperty("issue")
    public void setIssue(String issue) {
        this.issue = issue;
    }

    @JsonProperty("photo")
    public String getPhoto() {
        return photo;
    }

    @JsonProperty("photo")
    public void setPhoto(String photo) {
        this.photo = photo;
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
