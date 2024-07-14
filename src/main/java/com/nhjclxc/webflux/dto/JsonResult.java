package com.nhjclxc.webflux.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回结果集
 * @param <T> 泛型数据类型
 */
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功 true or false
     */
    private Boolean success;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回内容
     */
    private String msg;

    private Long timestamp;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 状态类型
     */
    public enum Type
    {
        /** 成功 */
        SUCCESS(200, "操作成功"),
        /** 警告 */
        WARN(301, "操作警告"),
        /** 错误 */
        ERROR(500, "操作失败");
        private final int value;
        private final String typeName;

        Type(int value, String typeName) {
            this.value = value;
            this.typeName = typeName;
        }

        public int value() {  return this.value; }
        public String typeName() {  return this.typeName; }
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public JsonResult() { }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param type 状态类型
     * @param msg 返回内容
     * @param data 数据对象
     */
    public JsonResult(Type type, String msg, T data) {
        this.code = type.value();
        this.msg = msg;
        if (null != data) {
            this.data = data;
        }
        this.timestamp = System.currentTimeMillis();

        if (type.value == Type.SUCCESS.value) {
            this.success = Boolean.TRUE;
        } else {
            this.success = Boolean.FALSE;
        }
    }

    public JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        if (null != data) {
            this.data = data;
        }

        if (code >= 200 && code < 300) {
            this.success = Boolean.TRUE;
        } else {
            this.success = Boolean.FALSE;
        }
    }

    /**
     * 往data为map类型的数据里面添加keyvalue的数据
     *
     * @param key map对应的key值
     * @param value 改key对应的value值
     * @return 返回本对象
     */
    public JsonResult<T> put(String key, Object value) {
        if (this.data instanceof Map){
            Map<String, Object> data = (Map<String, Object>) this.data;
            data.put(key, value);
            return this; // 支持链式调用
        }
        throw new RuntimeException("data对应的泛型不支持put方法");
    }

    /**
     * 返回一个泛型为Map的JsonResult对象
     */
    public static JsonResult<Map<String, Object>> success(){
//        JsonResult<Map<String, Object>> mapInstance = JsonResult.success().put("asd", 13).put("scasc", "ascasc").put("11", 22);
//        {"code":0,"data":{"11":22,"scasc":"ascasc","asd":13},"msg":"操作成功","success":true}
      return JsonResult.success(new HashMap<>());
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> JsonResult<T> success(T data)
    {
        return JsonResult.success(Type.SUCCESS.typeName(), data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> JsonResult<T> success(String msg)
    {
        return JsonResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> JsonResult<T> success(String msg, T data)
    {
        return new JsonResult<>(Type.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> JsonResult<T> warn(String msg)
    {
        return JsonResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> JsonResult<T> warn(String msg, T data)
    {
        return new JsonResult<>(Type.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 警告消息
     */
    public static <T> JsonResult<T> error()
    {
        return JsonResult.error(Type.ERROR.typeName());
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> JsonResult<T> error(String msg)
    {
        return JsonResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> JsonResult<T> error(String msg, T data)
    {
        return new JsonResult<>(Type.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 异常码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> JsonResult<T> error(int code, String msg)
    {
        return new JsonResult<>(code, msg, null);
    }

    public T getData() {
        return data;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
