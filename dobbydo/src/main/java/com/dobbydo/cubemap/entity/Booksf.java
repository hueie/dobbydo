package com.dobbydo.cubemap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="booksf")
public class Booksf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name="booksf_id")
    private int booksf_id;  
	
	@Column(name="stack_id")
    private int stack_id;  
	@Column(name="booksf_nm")
    private String booksf_nm;
	@Column(name="booksf_remk")
	private String booksf_remk;

	@Column	(name="booksf_height")
	private int booksf_height;
	@Column	(name="booksf_row_cnt")
	private int booksf_row_cnt;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getStack_id() {
		return stack_id;
	}


	public void setStack_id(int stack_id) {
		this.stack_id = stack_id;
	}


	public int getBooksf_id() {
		return booksf_id;
	}


	public void setBooksf_id(int booksf_id) {
		this.booksf_id = booksf_id;
	}


	public String getBooksf_nm() {
		return booksf_nm;
	}


	public void setBooksf_nm(String booksf_nm) {
		this.booksf_nm = booksf_nm;
	}


	public String getBooksf_remk() {
		return booksf_remk;
	}


	public void setBooksf_remk(String booksf_remk) {
		this.booksf_remk = booksf_remk;
	}


	public int getBooksf_height() {
		return booksf_height;
	}


	public void setBooksf_height(int booksf_height) {
		this.booksf_height = booksf_height;
	}


	public int getBooksf_row_cnt() {
		return booksf_row_cnt;
	}


	public void setBooksf_row_cnt(int booksf_row_cnt) {
		this.booksf_row_cnt = booksf_row_cnt;
	}


	
}
