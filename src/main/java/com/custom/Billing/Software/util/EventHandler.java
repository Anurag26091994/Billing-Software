package com.custom.Billing.Software.util;


import com.custom.Billing.Software.constant.EventType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public interface EventHandler<T> {

    String handle(T form);
    EventType getEventType();
    String getEventName();
    Class<T> getFormClass();

    default T handleJson(JsonNode jsonNode, ObjectMapper mapper) {

        if (jsonNode == null || jsonNode.isEmpty()) {
            FormLogger.warn("EventHandler : Json data is empty", new IllegalArgumentException("JSON data is empty"));
            throw new IllegalArgumentException("JSON data is empty");
        }
        try {
            T form = mapper.treeToValue(jsonNode, getFormClass());
            return (T) handle(form);
        } catch (Exception e) {
            throw new RuntimeException("Failed to handle JSON event or Parse data: ", e);
        }
    }
}
