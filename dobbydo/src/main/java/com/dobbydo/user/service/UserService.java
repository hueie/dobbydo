package com.dobbydo.user.service;

import java.util.List;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.user.entity.User;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;

public interface UserService {

    boolean createUser(User user);
    User findUserByEmail(String email);
}
