package com.dobbydo.cammapping.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;

public interface CammappingDAO {
	void createCammapping(Cammapping cammapping);
    List<Cammapping> getAllCams();
	List<Cammapping> getLinesfsByCamId(int cam_id);
    void updateBooksfIdToCammapping(Cammapping cammapping);
}
