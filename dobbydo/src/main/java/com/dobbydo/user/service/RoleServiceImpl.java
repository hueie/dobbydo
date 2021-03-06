package com.dobbydo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cubemap.dao.CubemapDAO;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;
import com.dobbydo.user.dao.RoleDAO;
import com.dobbydo.user.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public synchronized boolean createRole(Role role){
		roleDAO.createRole(role);
		return true;
	}
	
	@Override
	public Role findByRole(String role) {
		return roleDAO.findByRole(role);
	}
	
}
