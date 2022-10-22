package oit.is.z0604.kaizi.janken2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class JankenAuthConfiguration {
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserBuilder users = User.builder();

    UserDetails user1 = users
        .username("user1")
        .password("$2y$10$Be5UlEWL/KsSo0UNO4phYuMpj/zeX7KLLhg1luEegjP6qpna9X7Ri")
        .roles("USER")
        .build();
    UserDetails user2 = users
        .username("user2")
        .password("$2y$10$2ZUK0wrxgWoPThXxTVMEpulE.ouoT0Xd0bRS7kPV9MoAF0hl/DH7e")
        .roles("USER")
        .build();
    UserDetails user3 = users
        .username("ほんだ")
        .password("$2y$10$P0dvHqebethDAvym4fZxrO9h74OZwGPbPCDfdcVuBq3ZUygBgopu6")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user1, user2, user3);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin();

    http.authorizeHttpRequests()
        .mvcMatchers("/janken/**").authenticated();

    http.logout().logoutSuccessUrl("/");
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
