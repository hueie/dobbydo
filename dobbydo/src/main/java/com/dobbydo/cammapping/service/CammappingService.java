package com.dobbydo.cammapping.service;

import java.util.List;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cammapping.entity.Cam;
import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

public interface CammappingService {

    boolean createCammapping(Cammapping cammapping);
    boolean deleteCammappingByFileuploadId(int fileupload_id, int user_id);
    List<Cam> getAllCams();
    List<Cammapping> getLinesfsByFileuploadId(int fileupload_id, int user_id);
    void updateBooksfIdToCammapping(Cammapping cammapping);
}
