package com.ejemplo.emailapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = {"http://localhost:4200", "${app.cors.origin}"})
public class EmailController {

    @Value("${serverless.url}")
    private String serverlessUrl;

    private final RestClient rest = RestClient.create();

    @PostMapping
    public ResponseEntity<String> enviar(@RequestBody EmailRequest request) {
        try {
            ResponseEntity<String> respuesta = rest.post()
                    .uri(serverlessUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toEntity(String.class);
            return ResponseEntity.status(respuesta.getStatusCode()).body(respuesta.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("{\"ok\":false,\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}