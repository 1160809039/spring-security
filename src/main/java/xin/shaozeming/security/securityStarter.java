package xin.shaozeming.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author: 邵泽铭
 * @date: 2018/8/31
 * @description:
 **/

@SpringBootApplication
public class securityStarter {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(securityStarter.class,args);
    }
}