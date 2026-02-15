package com.java.xdigit_session2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.java.xdigit_session2.ent.SessionsEnt;
import com.java.xdigit_session2.service.SessionService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/sessionApi")
public class SessionController {

	@Autowired
	private SessionService service;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
  @PostMapping("/saveSession")
   public SessionsEnt handleRequest(@RequestBody Map<String, Object> reqMap, HttpSession session) {
	        //accessing hello-service
		  session.setAttribute("userName", reqMap.get("name").toString());
		  session.setAttribute("timestamp", System.currentTimeMillis());
		  SessionsEnt sessionObject = service.createSession(session);
	      
		  
		  List<ServiceInstance> instances = discoveryClient.getInstances("xdigit-message");
	        if (instances != null && instances.size() > 0) {//todo: replace with a load balancing mechanism
	            ServiceInstance serviceInstance = instances.get(0);
	            String url = serviceInstance.getUri().toString();
	            url = url + "/saveMessage";
	            RestTemplate restTemplate = new RestTemplate();
	         	String message = restTemplate.postForObject(url,Map.of("sessionId",session.getId(),
	            "userName",session.getAttribute("userName"),"message",reqMap.get("message")) ,String.class);
	            System.out.println("Message result: "+message);
	          
	        }
	        return sessionObject;
	    }
	
  
  @PostMapping("/renameSession")
  public SessionsEnt renameSession(@RequestBody Map<String, Object> reqMap, HttpSession session) {
	  return service.renameSession(reqMap);
  }
  
  @PostMapping("/deleteSession")
  public SessionsEnt deleteSession(@RequestBody Map<String, Object> reqMap, HttpSession session) {
	  return service.deleteSession((Integer)reqMap.get("id"));
  }
	
  @PostMapping("/getMessages")
  public Map<String, Object> getMesages(@RequestBody Map<String, Object> reqMap){
	  return service.getMessages(reqMap);
  }
  
	
	
}
