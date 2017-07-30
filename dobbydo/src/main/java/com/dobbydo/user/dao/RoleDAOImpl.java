package com.dobbydo.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dobbydo.atestpage.dao.IArticleDAO;
import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;
import com.dobbydo.user.entity.Role;

@Transactional
@Repository
public class RoleDAOImpl  implements RoleDAO {
	@PersistenceContext	
	private EntityManager entityManager;	

	@Override
	public void createRole(Role role) {
		entityManager.persist(role);
	}	
	
	@SuppressWarnings("unchecked") //Ignore Warnings
	@Override
	public Role findByRole(String role) {
		String hql = "FROM Role  WHERE role = :role";
		return (Role) entityManager.createQuery(hql).setParameter("role", role).getSingleResult();
	}
	
}
