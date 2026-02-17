package com.custom.Billing.Software.service;

import tools.jackson.databind.JsonNode;

public interface UserService {
    String createUser(JsonNode data);

    String getUserInfo(JsonNode data);

    String getUserDetailsByEmail(JsonNode data);

    String deleteUser(JsonNode data);

    String updateUser(JsonNode payload);

    String login(JsonNode payload);
}
