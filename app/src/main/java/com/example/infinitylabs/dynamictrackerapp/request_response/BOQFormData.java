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
"label",
"description",
"quantity",
"answer",
"comment"
})
public class BOQFormData {

@JsonProperty("label")
private String label;
@JsonProperty("description")
private String description;
@JsonProperty("quantity")
private String quantity;
@JsonProperty("answer")
private String answer;
@JsonProperty("comment")
private String comment;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("label")
public String getLabel() {
return label;
}

@JsonProperty("label")
public void setLabel(String label) {
this.label = label;
}

@JsonProperty("description")
public String getDescription() {
return description;
}

@JsonProperty("description")
public void setDescription(String description) {
this.description = description;
}

@JsonProperty("quantity")
public String getQuantity() {
return quantity;
}

@JsonProperty("quantity")
public void setQuantity(String quantity) {
this.quantity = quantity;
}

@JsonProperty("answer")
public String getAnswer() {
return answer;
}

@JsonProperty("answer")
public void setAnswer(String answer) {
this.answer = answer;
}

@JsonProperty("comment")
public String getComment() {
return comment;
}

@JsonProperty("comment")
public void setComment(String comment) {
this.comment = comment;
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
        return "BOQFormData{" +
                "label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", quantity='" + quantity + '\'' +
                ", answer='" + answer + '\'' +
                ", comment='" + comment + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}