package com.liuliuliu.security.db.dao;

import com.liuliuliu.security.db.entity.OauthUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthUserDao {

    OauthUserEntity queryByUserId(Long userId);

    OauthUserEntity queryByUsername(String username);
}
