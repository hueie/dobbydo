package com.dobbydo.cammapping.dao;

import org.springframework.stereotype.Repository;

import com.dobbydo.cammapping.entity.Cammapping;

public interface CammappingDAO {
	void createCammapping(Cammapping cammapping);
}
