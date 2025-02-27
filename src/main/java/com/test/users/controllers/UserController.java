/**
 * 
 */
package com.test.users.controllers;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.test.users.entities.User;
import com.test.users.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author 
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<Page<User>> getUsers(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "1000") int size) {
		
		if(new Random().nextInt(10)==5) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found");
		}
		if(new Random().nextInt(10)==7) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"bad request");
		}
		if(new Random().nextInt(10)==2) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"error");
		}
		
		
		return new ResponseEntity<>(service.getUsers(page, size), HttpStatus.OK);
	}

	@GetMapping("/usernames")
	public ResponseEntity<Page<String>> getUsernames(
			@RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@RequestParam(required = false, value = "size", defaultValue = "1000") int size) {
		return new ResponseEntity<>(service.getUsernames(page, size), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	@ApiOperation(value = "Returns a user for a given user id", response =User.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The record was found"),
			@ApiResponse(code = 404, message = "The record was not found")
	})
	public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
		User user = service.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
		return new ResponseEntity<>(service.getUserByUsername(username), HttpStatus.OK);

	}

	@PostMapping("/authenticate")
	public ResponseEntity<User> authenticate(@RequestBody User user) {
		return new ResponseEntity<>(service.getUserByUsernameAndPassword(user.getUsername(), user.getPassword()),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteUser(@PathVariable("username") String username){
		service.deleteUserByUsername(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
