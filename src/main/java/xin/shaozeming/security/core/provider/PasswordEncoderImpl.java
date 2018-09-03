package xin.shaozeming.security.core.provider;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: 邵泽铭
 * @date: 2018/8/30
 * @description:
 *
 * 自定义密码加密
 **/

@Component("passwordEncoderImpl")
public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {

            return  charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {

        return charSequence.toString().equals(s);
    }
}