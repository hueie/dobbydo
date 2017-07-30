package com.dobbydo.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dobbydo.atestpage.dao.IArticleDAO;
import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.user.entity.User;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

@Transactional
@Repository
public class UserDAOImpl  implements UserDAO {
	@PersistenceContext	
	private EntityManager entityManager;	

	@Override
	public void createUser(User user) {
		entityManager.persist(user);
	}	
	
	@SuppressWarnings("unchecked") //Ignore Warnings
	@Override
	public User findUserByEmail(String email) {
		String hql = "FROM User  WHERE email = :email";
		return (User) entityManager.createQuery(hql).setParameter("email", email).getSingleResult();
	}
	
}
