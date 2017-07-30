package com.dobbydo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.user.dao.UserDAO;
import com.dobbydo.user.entity.User;
import com.dobbydo.cubemap.dao.CubemapDAO;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public synchronized boolean createUser(User user){
		userDAO.createUser(user);
		return true;
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userDAO.findUserByEmail(email);
	}
	/*
	public void saveUser(User user){
	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	user.setActive(1);
	Role userRole = roleRepository.findByRole("ADMIN");
	user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	userRepository.save(user);
	*/
}
