package com.dobbydo.fileupload.dao;

import org.springframework.stereotype.Repository;

import com.dobbydo.fileupload.entity.Fileupload;

public interface FileuploadDAO {
	void createFileupload(Fileupload fileupload);
}
