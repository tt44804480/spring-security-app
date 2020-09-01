package com.liuliuliu.security.db.entity;

import lombok.Data;

@Data
public class OauthUserEntity {

    private Long id;
    private Long userId;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

}
