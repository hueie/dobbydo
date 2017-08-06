package com.dobbydo.cubemap.service;

import java.util.List;

import com.dobbydo.cubemap.entity.Bookarng;
import com.dobbydo.cubemap.entity.Booksf;
import com.dobbydo.cubemap.entity.Box;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

public interface CubemapService {
    List<Stack> getAllStacks();
    List<Box> getAllBoxes();
    boolean createCubemap(Cubemap cubemap);
    boolean createStack(Stack stack);
    boolean createBooksf(Booksf booksf);
    boolean createBookarng(Bookarng bookarng);
    boolean createBox(Box box);
    List<Cubemap> getCubemapsByStackId(int stack_id);
    List<Cubemap> getCubemapsBySql(String sql);
    
    List<Booksf> getBooksfsByStackId(int stack_id);
    

    Booksf getBooksfByBooksfId(int booksf_id);
    Box getBoxByBoxId(int box_id);
    

    void deleteCubemap(int stack_d);
    void deleteBookarng(int stack_d);
}
