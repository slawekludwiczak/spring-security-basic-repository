package com.example.securitybasicrepository;

import jakarta.servlet.Filter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SecurityBasicRepositoryApplicationTests {

    @Autowired
    SecurityFilterChain filterChain;

    @Test
    void shouldUseNullSecurityContextRepository() throws NoSuchFieldException, IllegalAccessException {
        List<Filter> filters = filterChain.getFilters();
        BasicAuthenticationFilter basicFilter = (BasicAuthenticationFilter)filters.stream()
                .filter(filter -> filter.getClass().equals(BasicAuthenticationFilter.class))
                .findFirst().get();
        Field securityContextRepositoryField = BasicAuthenticationFilter.class.getDeclaredField("securityContextRepository");
        securityContextRepositoryField.setAccessible(true);
        SecurityContextRepository securityContextRepository = (SecurityContextRepository) securityContextRepositoryField.get(basicFilter);
        assertEquals(NullSecurityContextRepository.class, securityContextRepository.getClass());
    }

}
