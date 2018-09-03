package xin.shaozeming.security.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: 邵泽铭
 * @date: 2018/8/30
 * @description:
 * 参数异常
 **/
public class ParamException extends AuthenticationException {
    public ParamException(String msg, Throwable t) {
        super(msg, t);
    }
    public ParamException(String msg) {
        super(msg);
    }
}