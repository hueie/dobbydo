package com.dobbydo.cubemap.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dobbydo.cubemap.dao.CubemapDAO;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;
@Service
public class CubemapServiceImpl implements CubemapService {

	@Autowired
	private CubemapDAO cubemapDAO;
	

	@Override
	public synchronized boolean createStack(Stack stack){
       if (cubemapDAO.stackExists(stack.getStack_nm())) {
    	   return false;
       } else {
    	   cubemapDAO.createStack(stack);
    	   return true;
       }
	}

	@Override
	public synchronized boolean createBooksf(Booksf booksf){
		cubemapDAO.createBooksf(booksf);
		return true;
	}

	@Override
	public synchronized boolean createBox(Box box){
		cubemapDAO.createBox(box);
		return true;
	}
	
	@Override
	public List<Stack> getAllStacks() {
		return cubemapDAO.getAllStacks();
	}

	@Override
	public List<Box> getAllBoxes() {
		return cubemapDAO.getAllBoxes();
	}

	@Override
	public List<Cubemap> getCubemapsByStackId(int stack_id) {
		return cubemapDAO.getCubemapsByStackId(stack_id);
	}

	@Override
	public List<Booksf> getBooksfsByStackId(int stack_id) {
		return cubemapDAO.getBooksfsByStackId(stack_id);
	}
	
	/*
	public List TrandelyList(TrandelyVO vo) throws Exception{
		return TrandelyDAO.TrandelyList(vo);
	}
	
	public int TrandelyListTotCnt(TrandelyVO vo) throws Exception{
		return TrandelyDAO.TrandelyListTotCnt(vo);
	}
	
	public TrandelyVO TrandelyView(TrandelyVO vo) throws Exception {
		return TrandelyDAO.TrandelyView(vo);
	}
	
	public List TrandelyViewList(TrandelyVO vo) throws Exception{
		return TrandelyDAO.TrandelyViewList(vo);
	}
	
	public int TrandelyViewListTotCnt(TrandelyVO vo) throws Exception{
		return TrandelyDAO.TrandelyViewListTotCnt(vo);
	}
	
	public int TrandelyDel(TrandelyVO vo) throws Exception {
	     return TrandelyDAO.TrandelyDel(vo);
	}
	
	public int TrandelyRecordeChk(TrandelyVO vo) throws Exception {
	     return TrandelyDAO.TrandelyRecordeChk(vo);
	}
	
	public int TrandelyAllRecordeDel(TrandelyVO vo) throws Exception {
	     return TrandelyDAO.TrandelyAllRecordeDel(vo);
	}
	
	public void TrandelyAdd(TrandelyVO vo) throws Exception {
		TrandelyDAO.TrandelyAdd(vo);
	}
	
	public void TrandelyModReal(TrandelyVO vo) throws Exception {
		TrandelyDAO.TrandelyModReal(vo);
	}
	
	public void TrandelyRecordeAdd(TrandelyVO vo) throws Exception {
		TrandelyDAO.TrandelyRecordeAdd(vo);
	}
	
	public void TrandelyRecordeDel(TrandelyVO vo) throws Exception {
		TrandelyDAO.TrandelyRecordeDel(vo); 
	}
	
	public void TrandelyRsnAdd(TrandelyVO vo) throws Exception {
		TrandelyDAO.TrandelyRsnAdd(vo); 
	}
	
	public TrandelyVO TrandelyRsnView(TrandelyVO vo) throws Exception {
		return TrandelyDAO.TrandelyRsnView(vo);
	}
	
	*/
}
