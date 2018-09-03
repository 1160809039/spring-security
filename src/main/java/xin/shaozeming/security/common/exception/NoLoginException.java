package xin.shaozeming.security.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 邵泽铭
 * \* Date: 2018/8/15
 * \* Time: 18:07
 * \* Description:
 * \  未登录异常
 */
public class NoLoginException extends AuthenticationException {
    public NoLoginException(String msg, Throwable t) {
        super(msg, t);
    }
    public NoLoginException(String msg) {
        super(msg);
    }
}