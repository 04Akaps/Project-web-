package com.godcoder.myhome.config;
// Delete할떄에 단순히 api만을 입력하면 똑같이 삭제버튼과 같이 작동하기 떄문에
// 따로 보안을 설정해 주어야 한다.

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

}
