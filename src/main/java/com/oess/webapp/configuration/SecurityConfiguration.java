package com.oess.webapp.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static final String LOGIN_PATH = "/login";
    private static final String LOGOUT_PATH = "/logout";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthRedirectWithFlashData redirectToLoginHandler = new AuthRedirectWithFlashData(LOGIN_PATH);
        http.csrf().disable()
            .authorizeRequests()
            .mvcMatchers(LOGIN_PATH, LOGOUT_PATH).permitAll()
            .anyRequest().authenticated()
            .and().formLogin()
            .loginPage(LOGIN_PATH)
            .failureHandler(redirectToLoginHandler)
            .and().logout()
            .logoutUrl(LOGOUT_PATH)
            .logoutSuccessHandler(redirectToLoginHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
            .withUser("root").password(passwordEncoder().encode("root")).roles("USER").and()
            .withUser("inactive").password(passwordEncoder().encode("root")).roles("USER").accountLocked(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(LOGIN_PATH).setViewName("login");
    }

    private static class AuthRedirectWithFlashData implements AuthenticationFailureHandler, LogoutSuccessHandler {

        private String redirectUrl;
        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        public AuthRedirectWithFlashData(String redirectUrl) {
            Assert.isTrue(UrlUtils.isValidRedirectUrl(redirectUrl),
                    "'" + redirectUrl + "' is not a valid redirect URL");
            this.redirectUrl = redirectUrl;
        }

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            FlashMap flashMap = new FlashMap();
            flashMap.put("isBadCredential", 
                    exception instanceof BadCredentialsException ||
                    exception instanceof UsernameNotFoundException);
            flashMap.put("isInactive",
                    exception instanceof AccountStatusException);
            flashMap.put("isOtherAuthError",  // "None of the above" flag
                    flashMap.values().stream().allMatch(v -> Boolean.FALSE.equals(v)));
            FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
            redirectStrategy.sendRedirect(request, response, redirectUrl);
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
            FlashMap flashMap = new FlashMap();
            flashMap.put("isLogout", true);
            FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
            redirectStrategy.sendRedirect(request, response, redirectUrl);
        }
    }

}
