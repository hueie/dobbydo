package com.dobbydo.cubemap.service;

import java.util.List;

import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

public interface CubemapService {
    List<Stack> getAllStacks();
    boolean createStack(Stack stack);
    List<Cubemap> getCubemapsByStackId(int stack_id);
    
}
