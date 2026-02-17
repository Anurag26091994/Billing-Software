package com.custom.Billing.Software.util;


import com.custom.Billing.Software.config.JwtUtil;
import com.custom.Billing.Software.constant.EventName;
import com.custom.Billing.Software.constant.EventType;
import com.custom.Billing.Software.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class GenericEventHandler implements EventHandler<JsonNode> {

    private final UserService userService;
 //   private final AdminService adminService;
//    private final SuperAdminService superAdminService;
//    private final ShgService shgService;
//    private final CustomerService customerService;
    private JwtUtil jwtUtil;

    @Override
    public String handle(JsonNode data) {

        EventType eventType = EventType.valueOf(data.get("eventType").asText());
        EventName eventName = EventName.valueOf(data.get("eventName").asText());
        JsonNode payload = data.get("data");

        String token = data.has("token") ? data.get("token").asText() : null;

        Claims claims = token != null ? jwtUtil.validateToken(token) : null;

        FormLogger.info("GenericEventHandler: get payload data to handle "+payload);

        switch (eventType) {

            case MEDICAL:
                FormLogger.info("EventService: Creating user with event type: " + eventType + " and data: " + payload.toString());
                return handleUserEvents(eventName, payload,claims);

//            case ADMIN:
//                return handleAdminEvents(eventName, payload);

//            case SUPER_ADMIN:
//                return handleSuperAdminEvents(eventName, payload);
//
//            case SHG:
//                return handleShgEvents(eventName, payload);
//
//            case CUSTOMER:
//                return handleCustomerEvents(eventName, payload);

            default:
                throw new IllegalArgumentException("Unsupported EventType");
        }
    }

    @Override
    public EventType getEventType() {
        return null; // handled dynamically
    }

    @Override
    public String getEventName() {
        return "GENERIC";
    }

    @Override
    public Class<JsonNode> getFormClass() {
        return JsonNode.class;
    }

    // ---------- USER ----------
    private String handleUserEvents(EventName eventName, JsonNode payload, Claims claims) {
        switch (eventName) {
            case LOGIN:
                return userService.login(payload);
            case CREATE:
                FormLogger.info("EventService: Creating user with event type: " + eventName + " and data: " + payload.toString());

                return userService.createUser(payload);
            case UPDATE:
                return userService.updateUser(payload);
            case GET:
                return userService.getUserInfo(payload);
            case DELETE:
                return userService.deleteUser(payload);
            default:
                throw new IllegalArgumentException("Unsupported USER event");
        }
    }

    // ---------- ADMIN ----------//
//    private String handleAdminEvents(EventName eventName, JsonNode payload) {
//        switch (eventName) {
//            case CREATE:
//                return adminService.createAdmin(payload);
//            case GET:
//                return adminService.getAllData();
//            case DELETE:
//                return adminService.deleteAdmin(payload);
//            default:
//                throw new IllegalArgumentException("Unsupported ADMIN event");
//        }
//    }
//
//    // ---------- SUPER ADMIN ----------
//    private String handleSuperAdminEvents(EventName eventName, JsonNode payload) {
//        switch (eventName) {
//            case CREATE:
//                return superAdminService.create(payload);
//            default:
//                throw new IllegalArgumentException("Unsupported SUPER_ADMIN event");
//        }
//    }
//
//    // ---------- SHG ----------
//    private String handleShgEvents(EventName eventName, JsonNode payload) {
//        switch (eventName) {
//            case CREATE:
//                return shgService.create(payload);
//            default:
//                throw new IllegalArgumentException("Unsupported SHG event");
//        }
//    }
//
//    // ---------- CUSTOMER ----------
//    private String handleCustomerEvents(EventName eventName, JsonNode payload) {
//        switch (eventName) {
//            case CREATE:
//                return customerService.create(payload);
//            default:
//                throw new IllegalArgumentException("Unsupported CUSTOMER event");
//        }
//    }
}
