package com.liuliuliu.security.db.service;

import com.liuliuliu.security.db.dao.OauthUserDao;
import com.liuliuliu.security.db.entity.OauthUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OauthUserService {

    @Autowired
    private OauthUserDao oauthUserDao;

    /**
     * 获取UserDetails
     * @return
     * @param username 用户名
     */
    public UserDetails getUserDetails(String username){
        OauthUserEntity oauthUserEntity = oauthUserDao.queryByUsername(username);
        if(oauthUserEntity == null){
            throw new UsernameNotFoundException("用户信息不存在");
        }

        UserDetails userDetails = new User(oauthUserEntity.getUserId().toString(),
                oauthUserEntity.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER,ROLE_liu"));

        return userDetails;
    }

    /**
     * 获取UserDetails
     * @return
     * @param userId 用户id
     */
    public UserDetails getUserDetails(Long userId){
        OauthUserEntity oauthUserEntity = oauthUserDao.queryByUserId(userId);
        if(oauthUserEntity == null){
            throw new UsernameNotFoundException("用户信息不存在");
        }

        UserDetails userDetails = new User(oauthUserEntity.getUserId().toString(),
                oauthUserEntity.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));

        return userDetails;
    }
}
