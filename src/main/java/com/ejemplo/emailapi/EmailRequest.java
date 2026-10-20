package com.ejemplo.emailapi;

public record EmailRequest(String to, String subject, String message) {}