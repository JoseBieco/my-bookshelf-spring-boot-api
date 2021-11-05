package com.example.demo.user

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "USERS")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(columnDefinition = "TEXT",  nullable = false)
    var name: String? = null,

    @Column(columnDefinition = "TEXT",  nullable = false, unique = true)
    var email: String? = null,

    @Column(columnDefinition = "TEXT",  nullable = false)
    var password: String? = null,
): Serializable {
}