package com.sinoiov.lhjh.file.api.common;

import java.io.Serializable;

/**
 * 响应结果
 * 
 * @author xiongyan
 * @date 2016年8月26日 下午1:34:37
 */
public class ResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 成功标识
     */
    private static final char SUCCESS = 'Y';

    /**
     * 确认标示
     */
    private static final char CONFIRM = 'C';

    /**
     * 失败标识
     */
    private static final char ERROR = 'N';

    /**
     * 返回状态吗
     */
    private char code;

    /**
     * 返回消息内容
     */
    private String message;

    /**
     * 返回结果集
     */
    private Object data;

    /**
     * 默认构造方法
     */
    public ResponseDto() {

    }

    /**
     * 成功
     *
     * @return
     */
    public static ResponseDto bulidSuccess() {
        ResponseDto response = new ResponseDto();
        response.setCode(SUCCESS);
        return response;
    }

    /**
     * 成功
     *
     * @param message 消息内容
     * @return
     */
    public static ResponseDto bulidSuccess(String message) {
        ResponseDto response = new ResponseDto();
        response.setCode(SUCCESS);
        response.setMessage(message);
        return response;
    }

    /**
     * 成功
     *
     * @param data 结果集
     * @return
     */
    public static ResponseDto bulidSuccess(Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(SUCCESS);
        response.setData(data);
        return response;
    }

    /**
     * 成功
     *
     * @param message 消息内容
     * @param data    结果集
     * @return
     */
    public static ResponseDto bulidSuccess(String message, Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(SUCCESS);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * 失败
     *
     * @return
     */
    public static ResponseDto bulidError() {
        ResponseDto response = new ResponseDto();
        response.setCode(ERROR);
        return response;
    }

    /**
     * 失败
     *
     * @param message 消息内容
     * @return
     */
    public static ResponseDto bulidError(String message) {
        ResponseDto response = new ResponseDto();
        response.setCode(ERROR);
        response.setMessage(message);
        return response;
    }

    /**
     * 失败
     *
     * @param data 结果集
     * @return
     */
    public static ResponseDto bulidError(Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(ERROR);
        response.setData(data);
        return response;
    }

    /**
     * 失败
     *
     * @param message 消息内容
     * @param data    结果集
     * @return
     */
    public static ResponseDto bulidError(String message, Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(ERROR);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * 确认
     *
     * @return
     */
    public static ResponseDto bulidConfirm() {
        ResponseDto response = new ResponseDto();
        response.setCode(CONFIRM);
        return response;
    }

    /**
     * 确认
     *
     * @param message 消息内容
     * @return
     */
    public static ResponseDto bulidConfirm(String msg) {
        ResponseDto response = new ResponseDto();
        response.setCode(CONFIRM);
        response.setMessage(msg);
        return response;
    }


    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    } 
}