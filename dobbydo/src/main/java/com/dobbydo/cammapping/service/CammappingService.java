package com.dobbydo.cammapping.service;

import java.util.List;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

public interface CammappingService {

    boolean createCammapping(Cammapping cammapping);
    List<Cammapping> getAllCams();
    List<Cammapping> getLinesfsByCamId(int cam_id);
    void updateBooksfIdToCammapping(Cammapping cammapping);
}
