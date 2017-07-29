package com.dobbydo.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.user.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dobbydo.atestpage.entity.Article;

public interface RoleDAO {
	void createRole(Role role);
	Role findByRole(String role);
}
