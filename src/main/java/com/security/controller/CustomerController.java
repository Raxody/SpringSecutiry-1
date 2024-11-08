package com.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("v1")
public class CustomerController {

    private SessionRegistry sessionRegistry;

    public CustomerController(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/index")
    public String index(){
        return "Hello world!";
    }

    @GetMapping("/index2")
    public String index2(){
        return "Hello world Not Secure!";
    }

    @GetMapping("/session")
    public ResponseEntity<Object> getDetailsSession(){
        String sessionId = "";
        User userObject = null;

        List<Object> sessions = sessionRegistry.getAllPrincipals();

        for (Object session : sessions) {
            if(session instanceof User){
                userObject = (User)session;
            }
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, Boolean.FALSE);

            for (SessionInformation sessionInformation : sessionInformations) {
                sessionId = sessionInformation.getSessionId();
            }
        }
        return ResponseEntity.ok().body(Map.of("Response", "Hello world!", "sessionId", sessionId, "userObject", userObject));
    }


}
