According to [the documentation](https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#stateless-authentication):

### Expected:

Sometimes there is no need to create and maintain a HttpSession for example, to persist the authentication across requests. Some authentication mechanisms like HTTP Basic are stateless and, therefore, re-authenticates the user on every request.
If you do not wish to create sessions, you can use SessionCreationPolicy.STATELESS, like so:

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) {
    http
        // ...
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
    return http.build();
}
```

The above configuration is configuring the SecurityContextRepository to use a NullSecurityContextRepository and is also preventing the request from being saved in the session.

### Actual:

There is a test in this repository that checks SecurityContextRepository used by BasicAuthenticationFilter
and it fails because it uses **RequestAttributeSecurityContextRepository** instead of NullSecurityContextRepository.
