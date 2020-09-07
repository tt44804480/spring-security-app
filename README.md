# spring-security-app
springsecurity for token

使用springsecurity 通过大部分自定义配置 来实现：

- 可以自定义认证逻辑
- 自定义认证成功，失败的处理函数
- 自定义生成token，验证token逻辑
- 可以配置角色（权限）进行控制特定url访问
- ...

其中：

- authenticationFailureHandler包里为自定义失败的处理函数
- authenticationSuccessHandler 包里为自定义成功的处理函数
- conf 包中是自定义的资源服务器和认证服务器配置
- controller包里是特定的访问接口
- exception 为认证或接口访问过程中的自定义异常
- jwtTokenStore包里是自定义的token生成逻辑（jwt）,创建token和通过刷新token来重新获取token
- mobile 包中是自定义的登录方式
- userDetailsService 包中是各种加载用户信息的userDetailsService，因为可能会有多种认证方式，采用一种认证方式对应一种userDetailsService
    其中返回值中的principal 均为业务userId.
- 其余的一些配置。。


