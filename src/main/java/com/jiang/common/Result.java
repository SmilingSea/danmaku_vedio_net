package com.jiang.common;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果类，服务端响应的数据最终都会封装成此对象
 * @param <T>
 */

@Data
public class Result<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

//    private Map map = new HashMap(); //动态数据

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<>();
        r.data = object;
        r.code = 200;
        return r;
    }

    public static <T> Result<T> success(T object,String msg) {
        Result<T> r = new Result<>();
        r.data = object;
        r.code = 200;
        r.msg = msg;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = 0;
        return r;
    }

//    public Result<T> add(String key, Object value) {
//        this.map.put(key, value);
//        return this;
//    }

}