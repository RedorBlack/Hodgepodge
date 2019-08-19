package com.red.webflux.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 15:20 2019/8/19
 */
@Entity
@Table(name = "user")
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @Column(name = "jwt_token")
    private String token;
}
