package com.dobbydo.cubemap.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dobbydo.atestpage.dao.IArticleDAO;
import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

@Transactional
@Repository
public class CubemapDAOImpl  implements CubemapDAO {
	@PersistenceContext	
	private EntityManager entityManager;	

	@SuppressWarnings("unchecked")
	@Override
	public List<Stack> getAllStacks() {
		String hql = "FROM Stack ORDER BY stack_id DESC";
		return (List<Stack>) entityManager.createQuery(hql).getResultList();
	}	
	@Override
	public void createStack(Stack stack) {
		entityManager.persist(stack);
	}
	@Override
	public boolean stackExists(String stack_nm) {
		String hql = "FROM Stack WHERE stack_nm = :stack_nm "; //this Table Name Is Class Name Not Real Table name
		int count = entityManager.createQuery(hql).setParameter("stack_nm", stack_nm).getResultList().size();
		return count > 0 ? true : false;
	}
	
	@SuppressWarnings("unchecked") //Ignore Warnings
	@Override
	public List<Cubemap> getCubemapsByStackId(int stack_id) {
		String hql = "FROM Cubemap  WHERE stack_id = :stack_id ORDER BY stack_id DESC";
		return (List<Cubemap>) entityManager.createQuery(hql).setParameter("stack_id", stack_id).getResultList();
	}
	
	/*
	public List TrandelyList(TrandelyVO vo) throws Exception {
		return list("TrandelyList", vo);
    }
	
	public int TrandelyListTotCnt(TrandelyVO vo) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("TrandelyListTotCnt", vo);
    }
	
	public TrandelyVO TrandelyView(TrandelyVO vo) throws Exception {
        return (TrandelyVO) selectByPk("TrandelyView", vo);
    }
	
	public List TrandelyViewList(TrandelyVO vo) throws Exception {
		return list("TrandelyViewList", vo);
    }
	
	public int TrandelyViewListTotCnt(TrandelyVO vo) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("TrandelyViewListTotCnt", vo);
    }

	public int TrandelyDel(TrandelyVO vo) throws Exception {
		return delete("TrandelyDelete", vo);
    }
	
	public int TrandelyRecordeChk(TrandelyVO vo) throws Exception {
		return (Integer)getSqlMapClientTemplate().queryForObject("TrandelyDeleteChk", vo);
    }
	
	public int TrandelyAllRecordeDel(TrandelyVO vo) throws Exception {
		return delete("TrandelyAllRecordeDel", vo);
    }
	
	public void TrandelyAdd(TrandelyVO vo) throws Exception {
		insert("CreateTrandely", vo);
    }
   
	public void TrandelyModReal(TrandelyVO vo) throws Exception {
		update("UpdateTrandelyAppro", vo);
    }
	
	public void TrandelyRecordeAdd(TrandelyVO vo) throws Exception {
		insert("TrandelyCreateRecorde", vo);
    }
	
	public void TrandelyRecordeDel(TrandelyVO vo) throws Exception {
		 delete("TrandelyRecordeDel", vo);
  }
   
	public void TrandelyRsnAdd(TrandelyVO vo) throws Exception {
		 update("updateTrandelyRsn", vo);
   }
	
	public TrandelyVO TrandelyRsnView(TrandelyVO vo) throws Exception {
        return (TrandelyVO) selectByPk("TrandelyRsnView", vo);
    }
	*/
}
