package com.example.rasnassesment.security;


import com.example.rasnassesment.SpringApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Objects;


public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING ="Authorization";
    public static final String SIGN_UP_URL = "users";
    public static final String TOKEN_SECRET="oXh5cpeyg2IBthtEryWluG9g446tQTiM1cMdsUhSDsDjSuxgpe4bV2MQYFMz09MO";
    public static final String H2_CONSOLE = "/h2-console/**";

    public static byte[] getTokenSecret(){
        return TOKEN_SECRET.getBytes();
    }


}
