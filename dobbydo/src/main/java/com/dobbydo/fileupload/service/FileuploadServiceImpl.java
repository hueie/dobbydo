package com.dobbydo.fileupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dobbydo.fileupload.dao.FileuploadDAO;
import com.dobbydo.fileupload.entity.Fileupload;
import com.dobbydo.cubemap.dao.CubemapDAO;
import com.dobbydo.cubemap.entity.Cubemap;

@Service
public class FileuploadServiceImpl implements FileuploadService {
	@Autowired
	private FileuploadDAO fileuploadDAO;
	
	@Override
	public synchronized boolean createFileupload(Fileupload fileupload){
		fileuploadDAO.createFileupload(fileupload);
		return true;
	}
}
