package com.example.demo.user

import com.example.demo.user.dtos.LoginDto
import com.example.demo.user.dtos.RegisterUserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import com.example.demo.util.Message

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
     *      GetUserFromToken -> /auth/user, method: GET
     *      Logout -> /auth/logout, method: POST
     *  Basic:
     *      Delete -> /auth, method: DELETE
     *      Update -> /auth/{userId}, method: PUT { Name, Password, Email?; }
     * */

    @PostMapping
    @RequestMapping("/register")
    fun register(@RequestBody user: RegisterUserDto): User {
        return this.service.create(user)
    }

    @PostMapping
    @RequestMapping("/login")
    fun login(@RequestBody login: LoginDto, response: HttpServletResponse): User {
        return this.service.login(login, response)
    }

    @DeleteMapping
    @RequestMapping("/{userId}")
    fun delete(@PathVariable userId: Long) {
        return this.service.delete(userId)
    }

    @GetMapping("/user")
    fun getUserFromToken(@CookieValue("token") token : String?): User {
        return this.service.getUserFromToken(token);
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("token", "")
        cookie.maxAge = 0
        response.addCookie(cookie)
        return ResponseEntity.ok(Message("Logged off!"))
    }
}