package com.jiang.common;

import lombok.Data;



/**
 * 通用返回结果类，服务端响应的数据最终都会封装成此对象
 *
 * @author SmilingSea
 * @param <T>
 */

@Data
public class Result<T> {

    /**
     * 编码：1成功，0和其它数字为失败
     */

    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;


    /**
     * 构造方法
     * @param object  传入参数
     * @return result
     * @param <T> 类型
     */
    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<>();
        r.data = object;
        r.code = 200;
        return r;
    }
    /**
     * 构造方法
     * @param object  传入参数
     * @return result
     * @param <T> 类型
     */
    public static <T> Result<T> success(T object, String msg) {
        Result<T> r = new Result<>();
        r.data = object;
        r.code = 200;
        r.msg = msg;
        return r;
    }

    /**
     * 构造方法
     * @param msg 错误信息
     * @return result
     * @param <T> 类型
     */
    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = 400;
        return r;
    }


}
