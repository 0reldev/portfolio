package com.reldev.website.service;

import com.reldev.website.entities.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {

    void autoLogin(HttpServletRequest request, String username, String password);
    User getLoggedUser();
}
