package org.psc.waauth;

import javax.sql.DataSource;

import org.psc.waauth.user.UserCredentialsRepository;
import org.psc.waauth.user.UserRepository;
import org.psc.waauth.user.WaAuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@SpringBootApplication
@EnableAutoConfiguration
public class WaAuthApplication {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SpringResourceTemplateResolver springResourceTemplateResolver;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialsRepository usercredentialsRepository;

    public static void main(String[] args) {
        SpringApplication.run(WaAuthApplication.class, args);
    }

    /**
     * Add thymeleaf's {@link SpringSecurityDialect} to the
     * {@link SpringTemplateEngine}
     * 
     * @return
     */
    // @Bean(name = "templateEngine")
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addDialect(new SpringSecurityDialect());
        springTemplateEngine.setEnableSpringELCompiler(true);
        springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);
        return springTemplateEngine;
    }

    /**
     * Add thymeleaf's SpringSecurityDialect to the
     * {@link SpringTemplateEngine}.
     * 
     * @param springTemplateEngine
     */
    @Autowired
    public void configureSpringTemplateEngine(SpringTemplateEngine springTemplateEngine) {
        springTemplateEngine.addDialect(new SpringSecurityDialect());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        UserBuilder users = User.builder();
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
                .withUser(users.username("user").password(passwordEncoder().encode("password")).roles("USER"))
                .withUser(users.username("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN"));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new WaAuthUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
