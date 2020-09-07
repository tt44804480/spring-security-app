package com.liuliuliu.security.constant;

/**
 * @Author lty
 * @Date 2019/11/28 14:47
 */
public class GlobalConstant {
    public static final String UNKNOWN = "unknown";

    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    public static final int MAX_IP_LENGTH = 15;
    /**
     * 上下文用户id
     */
    public static final String  CONTEXT_USER_ID = "userId";
    /**
     * 上下文租户id
     */
    public static final String  CONTEXT_SYSTEM_TYPE = "systemType";

    /**
     * 上下文组织id
     */
    public static final String  CONTEXT_DEPT_ID = "deptId";
    /**
     * 上下文用户名
     */
    public static final String  CONTEXT_USER_NAME= "userName";
    /**
     * 后台用户token
     */
    public static final String  CONTEXT_USER_TOKEN = "userToken";

    /**
     * api用户token
     */
    public static final String  CONTEXT_API_TOKEN = "apiToken";

    /**
     * id生成器工作id
     */
    public static final long  WORK_ID = 1;
    /**
     * id生成器数据中心id
     */
    public static final long  DATACENTER_ID = 1;





    /**
     * The class Symbol.
     *
     * @author paascloud.net@gmail.com
     */
    public static final class Symbol {
        private Symbol() {
        }

        /**
         * The constant COMMA.
         */
        public static final String COMMA = ",";
        public static final String SPOT = ".";
        /**
         * The constant UNDER_LINE.
         */
        public final static String UNDER_LINE = "_";
        /**
         * The constant PER_CENT.
         */
        public final static String PER_CENT = "%";
        /**
         * The constant AT.
         */
        public final static String AT = "@";
        /**
         * The constant PIPE.
         */
        public final static String PIPE = "||";
        public final static String SHORT_LINE = "-";
        public final static String SPACE = " ";
        public static final String SLASH = "/";
        public static final String MH = ":";

    }
}
