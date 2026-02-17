package com.custom.Billing.Software.dto;

import com.custom.Billing.Software.constant.EventType;
import tools.jackson.databind.JsonNode;

public class EventRequest {
    private EventType eventType;
    private String eventName;
    private JsonNode data;
    private Boolean flag;

    public EventRequest(String eventName, EventType eventType, JsonNode data, Boolean flag) {
        this.eventType = eventType;
        this.eventName = eventName;
        this.data = data;
        this.flag = flag;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "EventRequest{" +
                "eventType=" + eventType +
                ", eventName=" + eventName +
                ", jsonNode data=" + data +
                ", flag=" + flag +
                '}';
    }
}
