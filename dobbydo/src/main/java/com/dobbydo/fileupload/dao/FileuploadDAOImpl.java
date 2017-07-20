package com.dobbydo.fileupload.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dobbydo.atestpage.dao.IArticleDAO;
import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.fileupload.entity.Fileupload;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

@Transactional
@Repository
public class FileuploadDAOImpl  implements FileuploadDAO {
	@PersistenceContext	
	private EntityManager entityManager;	

	@Override
	public void createFileupload(Fileupload fileupload) {
		entityManager.persist(fileupload);
	}	
}
