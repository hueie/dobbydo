package com.dobbydo.cammapping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dobbydo.cammapping.dao.CammappingDAO;
import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cubemap.dao.CubemapDAO;
import com.dobbydo.cubemap.entity.Cubemap;

@Service
public class CammappingServiceImpl implements CammappingService {
	@Autowired
	private CammappingDAO cammappingDAO;
	
	@Override
	public synchronized boolean createCammapping(Cammapping cammapping){
		cammappingDAO.createCammapping(cammapping);
		return true;
	}
}
