package com.jiang;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenTest {


    private final String TOKEN_SECRE = "valsdjvbaejlvervboeqrhbvlerkvbqeiyurvgqwreivg";

    // 签名密钥
    private static final String SECRET = "SaveMemoryJWT";

    @Test
    public void getToken() {
         SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .claim("uid",123456) // 设置载荷信息
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        System.out.println(builder.compact());
    }

    /**
     * 获取token中的参数
     */
    @Test
    public void  parseToken() {
        String   token = "eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEyMzQ1Nn0.SBAl28XE4bVEhHKTT4QkZHsqQBpkEnSw-Y8DiPUSX4U";
        DecodedJWT jwt = null;
        try {
            jwt = JWT.decode(token);
            System.out.println(jwt.getClaim("uid").asLong());
//            return jwt;
        } catch (JWTDecodeException exception) {
            //Invalid token

        }

    }
}