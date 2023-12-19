package com.jiang.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jiang.domain.UserDO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWTUtils {



    private static final String SECRET = "nihao";

    private static final long EXPIRE = 604800;

    public static String getToken(UserDO user) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * EXPIRE);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .claim("isBlocked",user.getBlocked())
                .claim("uid",user.getId()) // 设置载荷信息
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(expireDate);




        //生成JWT
        return builder.compact();
    }

    /**
     * 获取token中的参数
     *
     * @param token
     * @return
     */
    public static Long getIdByToken(String token) {
        Long uid = null;
        DecodedJWT jwt = null;
        try {
            jwt = JWT.decode(token);
            uid = jwt.getClaim("uid").asLong();
            return uid;
        } catch (JWTDecodeException exception) {
            //Invalid token
        }
        return uid;
    }


    /**
     * 验证token是否合法
     */
}