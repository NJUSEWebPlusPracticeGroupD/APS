package com.webplusgd.aps.controller;

import com.webplusgd.aps.annotation.Log;
import com.webplusgd.aps.api.UserApi;
import com.webplusgd.aps.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Rollingegg
 * @create_time 10/12/2020 10:02 PM
 */
@RestController
@RequestMapping("/api/user")
public class UserController implements UserApi {

    @Log("获取用户信息")
    @Override
    public ResponseEntity<User> getUserByName(String username) {
        User user = new User();
        user.setId((long) 100001);
        user.setUsername("rollingegg");
        user.setUserStatus(0);
        user.setPassword("123456");
        return ResponseEntity.of(Optional.of(user));
    }
}
