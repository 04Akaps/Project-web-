package com.godcoder.myhome.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	// 말 그대로 데이터들을 가져오는 인스턴스를 가져온다.

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // 실제 사이트 에서는 사용하면 안된다.(보안에 취약해 진다.)
				.authorizeRequests()
					.antMatchers("/", "/css/**", "/account/register","/api/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					.loginPage("/account/login").permitAll()
					.and()
				.logout()
					.permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select username, password, enabled "		// 인증 처리
						+ "from user "
						+ "where username = ?")
				// username, password, enabled를 가져오는데 user라는 곳에서 가져오며 그중 username을 가져 오라는 코드
				.authoritiesByUsernameQuery("select u.username, r.name "		// 권한을 처리
						+ "from user_role ur inner join user u on ur.user_id = u.id "
						+ "inner join role r on ur.role_id = r.id "
						+ "where u.username = ?");
						// user_role 를 ur로 지정을 하거 user 는 u로 지정 후 ur.user_id = u.id 와 같다로 조인을 결성
						// 마찬가지로 role는 r로 지정후 조인을 결성
						// 로그인을 할떄에 이 쿼리가 실행된다.
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// password를 암호화 하기 위해서 인코딩 하는것

}