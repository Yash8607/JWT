package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UsersDto;
import com.example.demo.entities.Users;
import com.example.demo.serviceImpl.UserDetailsServiceImpl;

@RequestMapping("/user")
@RestController
public class UserController {
	
	@Autowired
	private UserDetailsServiceImpl userService;

	@PostMapping("/save")
	public ResponseEntity<Users> createUser(@RequestBody Users users){
		
		Users obj1 = userService.createUser(users);
		return new ResponseEntity<>(obj1, HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UsersDto userObj){
		
		String user=userService.validateUser(userObj.getEmail(), userObj.getPassword());
		
			
		return ResponseEntity.ok(user);
		}
	
	
	public ResponseEntity<String> authenticatedApi(){
		String str = "Authenticated Api";
		return ResponseEntity.ok(str);
	}
	
	
	}
	
	
	
	
	


