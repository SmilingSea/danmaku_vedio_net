package com.jiang.util;

import com.jiang.domain.UserDO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

public class TokenCreater {

    private static final String SECRET = "nihao";

    public static String getToken(UserDO user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .claim("uid",user.getId()) // 设置载荷信息
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }

}
