package com.dobbydo.cubemap.dao;

import java.util.List;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cubemap.entity.Bookarng;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

public interface CubemapDAO {

    List<Stack> getAllStacks();
    List<Box> getAllBoxes();
    void createCubemap(Cubemap cubemap);
    void createStack(Stack stack);
    void createBooksf(Booksf booksf);
    void createBookarng(Bookarng bookarng);
    void createBox(Box box);
    boolean stackExists(String stackNm);
	List<Cubemap> getCubemapsByStackId(int stack_id);
	List<Cubemap> getCubemapsBySql(String sql);
	
	List<Booksf> getBooksfsByStackId(int stack_id);

	Booksf getBooksfByBooksfId(int booksf_id);
	Box getBoxByBoxId(int box_id);
	
    void deleteCubemap(int stack_id);
}
