
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
        "task_id",
        "task",
        "type",
        "parent_id",
        "answer",
        "value",
        "is_harwareat",
        "description"
})
public class QuestionData {

    @JsonProperty("task_id")
    private String taskId;
    @JsonProperty("task")
    private String task;
    @JsonProperty("type")
    private String type;
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("description")
    private String description;

    @JsonProperty("is_harwareat")
    public Boolean getHarwareAt() {
        return isHarwareAt;
    }

    @JsonProperty("is_harwareat")
    public void setHarwareAt(Boolean harwareAt) {
        isHarwareAt = harwareAt;
    }

    @JsonProperty("is_harwareat")
    private Boolean isHarwareAt;


    @JsonProperty("answer")
    private List<Answer> answer = null;
    @JsonProperty("value")
    private List<String> value = null;
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

    @JsonProperty("task")
    public String getTask() {
        return task;
    }

    @JsonProperty("task")
    public void setTask(String task) {
        this.task = task;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("parent_id")
    public String getParentId() {
        return parentId;
    }

    @JsonProperty("parent_id")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @JsonProperty("answer")
    public List<Answer> getAnswer() {
        return answer;
    }

    @JsonProperty("answer")
    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    @JsonProperty("value")
    public List<String> getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(List<String> value) {
        this.value = value;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
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
