package com.custom.Billing.Software.serviceimpl;


import com.custom.Billing.Software.constant.EventType;
import com.custom.Billing.Software.exception.BusinessException;
import com.custom.Billing.Software.util.FormLogger;
import com.custom.Billing.Software.util.GenericEventHandler;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@Service
public class EventService {



    //    private final UserService userService;
    private final GenericEventHandler genericEventHandler;
    private final ObjectMapper objectMapper;

    public EventService(GenericEventHandler genericEventHandler, ObjectMapper objectMapper) {
        this.genericEventHandler = genericEventHandler;
        this.objectMapper = objectMapper;
    }


    public String processEvent(String eventName, EventType eventType,
                               JsonNode data, Boolean flag) {
        String response = "";
        FormLogger.info("EventService: processEvent called with eventName: " + eventName +
                ", eventType: " + eventType + ", flag: " + flag);
        try {
            if (flag) {
                FormLogger.info("EventService: Processing event as flag is true.");
                if (!eventType.toString().isBlank() && !eventName.isEmpty() && !eventType.toString().isEmpty() && !eventName.isBlank()) {
                    FormLogger.info("EventService: Handling USER event: " + eventName);

                    //  Build FULL event JSON
                    ObjectNode fullEvent = objectMapper.createObjectNode();
                    fullEvent.put("eventType", eventType.name());
                    fullEvent.put("eventName", eventName);
                    fullEvent.set("data", data);

//                  response= String.valueOf(genericEventHandler.handleJson(fullEvent,objectMapper));
                    response=genericEventHandler.handle(fullEvent);


                    FormLogger.info("EventService: Response from process event case: " + eventName);
                    return response;
                }

                if (eventType.toString().isEmpty()) {
                    response = "Event type is empty, cannot process event.";
                    return response;
                }
            } else {
                response = "Flag is false, event not processed.";
                FormLogger.info("EventService: " + response);
                return response;
            }
            FormLogger.info("EventService: Final response from processEvent: " + response);
            return response;
        }
        catch (BusinessException e) {
            FormLogger.warn("Business error: " + e.getMessage());
            return e.getMessage(); //  show actual message
        }catch (Exception e) {
            FormLogger.error("EventService: Error in processEvent - " + e);
            return "EventService :Error processing event: " +e.getMessage();
        }
    }
}
