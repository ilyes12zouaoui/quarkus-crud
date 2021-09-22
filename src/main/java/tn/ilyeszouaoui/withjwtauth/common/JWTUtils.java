package tn.ilyeszouaoui.withjwtauth.common;

import io.smallrye.jwt.build.Jwt;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class JWTUtils {

    public static String generateJWT(String firstName, String lastName, String email, String role, int age) {
        return Jwt.issuer("https://example.com/issuer")
                .upn("ilyes@gmail.com")
                .groups(new HashSet<>(Collections.singletonList(role)))
                .claim("firstName", firstName)
                .claim("lastName", lastName)
                .claim("email", email)
                .claim("role", role)
                .claim("age", age)
                .sign();
    }

}
