package com.dobbydo.fileupload.service;

import com.dobbydo.fileupload.entity.Fileupload;

import java.util.List;

import com.dobbydo.cammapping.entity.Cammapping;
import com.dobbydo.cubemap.entity.Cubemap;

public interface FileuploadService {

    boolean createFileupload(Fileupload fileupload);
    List<Fileupload> getFilesByFileuploadRegId(String fileupload_reg_id);
}
