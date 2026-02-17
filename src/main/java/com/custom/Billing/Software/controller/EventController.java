package com.custom.Billing.Software.controller;


import com.custom.Billing.Software.dto.EventRequest;
import com.custom.Billing.Software.serviceimpl.EventService;
import com.custom.Billing.Software.util.FormLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin("*")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/trigger")
    public ResponseEntity<String> triggerEvent(@RequestBody EventRequest data) {
        FormLogger.info("EventController: triggerEvent called with eventName: " + data.getEventName() +
                ", eventType: " + data.getEventType() + ", flag: " + data.getFlag());
        try {

            String response = eventService.processEvent(data.getEventName(), data.getEventType(),
                    data.getData(), data.getFlag());
            return ResponseEntity.ok(response);
        }

        catch (Exception e) {
            FormLogger.error("EventController: Error in triggerEvent - " + e.getMessage());
            return ResponseEntity.status(500).body("Error processing event: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public String getInfo(){
        return "get method call";
    }
}
