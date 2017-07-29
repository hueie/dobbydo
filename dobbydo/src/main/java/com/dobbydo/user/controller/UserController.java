package com.dobbydo.user.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dobbydo.user.entity.Role;
import com.dobbydo.user.entity.User;
import com.dobbydo.user.service.RoleService;
import com.dobbydo.user.service.UserService;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;
import com.dobbydo.cubemap.service.CubemapService;
import com.dobbydo.fileupload.entity.Fileupload;
import com.dobbydo.fileupload.service.FileuploadService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@PostMapping("User")
	public ResponseEntity<Void> createUser(
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "last_name", required = false) String last_name,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password
			
			) {

		User user = new User();
		user.setActive(1);
		user.setEmail(email);
		user.setLast_name(last_name);
		user.setName(name);
		//user.setPassword(password);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		Role userRole = roleService.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		boolean flag = userService.createUser(user);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("findByEmail")
	public ResponseEntity<List<User>> findByEmail(@RequestParam(value = "email", required = false) String email) {
		List<User> list = userService.findByEmail(email);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

}
