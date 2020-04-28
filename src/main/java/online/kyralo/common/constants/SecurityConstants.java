package online.kyralo.common.constants;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2018-12-11
 * \* Time: 下午6:46
 * \* Description:安全相关的一些常量
 * \
 * @author 王宸
 */
public class SecurityConstants {
    /**
     *  3*60*60*1000 3小时
     */
    public static final long EXPIRATION_TIME = 3 * 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
