package com.dobbydo.user.service;

import java.util.List;

import com.dobbydo.atestpage.entity.Article;
import com.dobbydo.cubemap.entity.Cubemap;
import com.dobbydo.cubemap.entity.Stack;
import com.dobbydo.user.entity.Role;

public interface RoleService {

    boolean createRole(Role role);
    Role findByRole(String role);
}
