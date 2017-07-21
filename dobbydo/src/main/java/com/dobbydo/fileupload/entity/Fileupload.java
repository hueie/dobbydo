package com.dobbydo.fileupload.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fileupload")
public class Fileupload implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fileupload_id")
	private int fileupload_id;
	@Column(name="file_nm")
	private String file_nm;
	@Column(name="file_path")
	private String file_path;
	@Column(name="fileupload_reg_id")
	private String fileupload_reg_id;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getFileupload_id() {
		return fileupload_id;
	}
	public void setFileupload_id(int fileupload_id) {
		this.fileupload_id = fileupload_id;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getReg_id() {
		return fileupload_reg_id;
	}
	public void setReg_id(String reg_id) {
		this.fileupload_reg_id = reg_id;
	}
}