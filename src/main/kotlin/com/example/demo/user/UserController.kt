package com.example.demo.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class UserController(
    @Autowired
    val service: UserService
) {
    /**
     *  Routes:
     *      Login -> /auth/login, method: POST
     *      Register -> /auth/register, method: POST
     *  Basic:
     *      Delete -> /auth, method: DELETE
     *      Update -> /auth/{userId}, method: PUT { Name, Password, Email? }
     * */
}