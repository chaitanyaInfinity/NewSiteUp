package com.example.infinitylabs.dynamictrackerapp.request_response;

/**
 * Created by infinitylabs on 5/7/17.
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
        "section_id",
        "site_id"
})
public class QuestionListRequest {

    @JsonProperty("section_id")
    private String sectionId;
    @JsonProperty("site_id")
    private String orderId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("section_id")
    public String getSectionId() {
        return sectionId;
    }

    @JsonProperty("section_id")
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @JsonProperty("site_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("site_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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