package ku.cs.flowerManagement.config;


import ku.cs.flowerManagement.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImp userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()


                        .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/static/assets/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/queue")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/charge-money")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/queue-order")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/stock")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/view-sales")).permitAll()



                        .requestMatchers(new AntPathRequestMatcher("/signup")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/flower/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/flower/create")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/flower/detail")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/order/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/invoice/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/invoiceConfirm/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/invoiceCompleteButton/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/stock/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/allocate/**")).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/static/assets/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/queue")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/charge-money")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/queue-order")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/stock")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/view-sales")).permitAll()
//
//
//                        .requestMatchers(new AntPathRequestMatcher("/order")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/invoice")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/allocate")).permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
//    }

}
