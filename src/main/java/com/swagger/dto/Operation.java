package com.swagger.dto;


import java.util.LinkedHashMap;
import java.util.List;


public class Operation {
    private List<String> tags = null;
    private String summary = null;
    private String operationId = null;
    private List<String> consumes;
    private List<String> produces;
    private List<Parameter> parameters = null;
    private LinkedHashMap<String, ApiResponse> responses = null;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public LinkedHashMap<String, ApiResponse> getResponses() {
        return responses;
    }

    public void setResponses(LinkedHashMap<String, ApiResponse> responses) {
        this.responses = responses;
    }
}
