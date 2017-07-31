package com.dobbydo.cammapping.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cammapping.entity.Cam;
import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;

public interface CammappingDAO {
	void createCammapping(Cammapping cammapping);
	void deleteCammappingByFileuploadId(int fileupload_id, int user_id);
    List<Cam> getAllCams();
	List<Cammapping> getLinesfsByFileuploadId(int fileupload_id, int user_id);
    void updateBooksfIdToCammapping(Cammapping cammapping);
}
