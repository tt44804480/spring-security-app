package com.liuliuliu.security.authentication.authenticationSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.liuliuliu.security.authentication.conf.MyAuthorizationServerConfig;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 自定义登录成功的处理类
 *
 * @author liutianyang
 * @since 1.0
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClientDetailsService clientDetailsService;

    /*@Autowired
    @Qualifier("defaultAuthorizationServerTokenServices")
    private AuthorizationServerTokenServices authorizationServerTokenServices;*/

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        logger.info("登录成功 {}", authentication.getName());

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {
            //参数不合法：
            throw new UnapprovedClientAuthenticationException("请求头中无client信息...");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        //校验clientId clientSecret
        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId 对应的配置信息不存在...");
        }else if(!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)){
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配...");
        }

        //校验 grant_type
        Set<String> authorizedGrantTypes = clientDetails.getAuthorizedGrantTypes();
        String grantType = request.getParameter("grant_type");
        boolean isGrantType = authorizedGrantTypes.contains(grantType);
        if(!isGrantType){
            throw new UnapprovedClientAuthenticationException("不支持的授权类型，grant_type:{}"+ request.getParameter("grant_type"));
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken accessToken = MyAuthorizationServerConfig.myAuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(accessToken));
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     * Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }
}
