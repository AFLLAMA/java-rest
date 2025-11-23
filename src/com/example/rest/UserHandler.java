package com.example.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String response = "";
        int statusCode = 200;

        try {
            if ("GET".equals(method)) {
                response = "[{\"id\":1, \"name\":\"Alice\"}, {\"id\":2, \"name\":\"Bob\"}]";
            } else if ("POST".equals(method)) {
                // Read request body (simplified for demo)
                byte[] requestBody = exchange.getRequestBody().readAllBytes();
                String body = new String(requestBody, StandardCharsets.UTF_8);
                System.out.println("Received POST data: " + body);
                
                response = "{\"message\": " + "\"User created\"}";
                statusCode = 201;
            } else {
                statusCode = 405; // Method Not Allowed
                response = "{\"error\": \"Method not allowed\"}";
            }
        } catch (Exception e) {
            statusCode = 500;
            response = "{\"error\": \"Internal Server Error\"}";
            e.printStackTrace();
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
