package com.dobbydo.fileupload.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dobbydo.fileupload.entity.Fileupload;

public interface FileuploadDAO {
	void createFileupload(Fileupload fileupload);
	List<Fileupload> getFilesByFileuploadRegId(String fileupload_reg_id);
}
