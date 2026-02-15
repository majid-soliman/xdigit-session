package com.java.xdigit_session2.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.java.xdigit_session2.dao.SessionDAO;
import com.java.xdigit_session2.ent.SessionsEnt;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {

	@Autowired
	SessionDAO dao;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
		
	public SessionsEnt createSession(HttpSession session){
		
		SessionsEnt sessionEnt = new SessionsEnt(session.getId(),session.getAttribute("userName").toString(),new Date());
		dao.createSession(sessionEnt);
        return sessionEnt;		
	}
	
	public SessionsEnt renameSession(Map<String, Object> reqMap) {
		return dao.renameSession((Integer)reqMap.get("id"), reqMap.get("name").toString());
	}
	
	public SessionsEnt deleteSession(int id) {
		SessionsEnt ent = dao.deleteSession(id);
	    if(ent.getId()!=0) {
		  List<ServiceInstance> instances = discoveryClient.getInstances("xdigit-message");
	        if (instances != null && instances.size() > 0) {//todo: replace with a load balancing mechanism
	            ServiceInstance serviceInstance = instances.get(0);
	            String url = serviceInstance.getUri().toString();
	            url = url + "/deleteMessages";
	            RestTemplate restTemplate = new RestTemplate();
	            String message = restTemplate.postForObject(url,Map.of("sessionId",ent.getSessionId()),String.class);
	            System.out.println("Message result: "+message);
	 }}
		
	return ent;
	}
	
	public Map<String, Object> getMessages(Map<String, Object> reqMap){
		List<ServiceInstance> instances = discoveryClient.getInstances("xdigit-message");
        if (instances != null && instances.size() > 0) {//todo: replace with a load balancing mechanism
            ServiceInstance serviceInstance = instances.get(0);
            String url = serviceInstance.getUri().toString();
            url = url + "/getMessages";
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> resMap = restTemplate.postForObject(url,reqMap,Map.class);
            return resMap;
 }
        else {
        	return null;
        }
	}
	
	
}
