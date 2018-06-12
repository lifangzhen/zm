package com.lun.mlm.utils;

/**
 * ajax访问时统一的返回格式对象
 * 具体业务数据为泛类T
 * @param <T>
 */
public class ApiResponse<T> {

    private String errorCode; // 定义的错误码
    private String errorInfo; // 出现错误时具体错误信息
    private T data; // 返回的数据对象
    public ApiResponse(){}

    public ApiResponse(String errorCode, String errorInfo, T t) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.data = t;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResponse<Object> success(){
        return new ApiResponse("0","success", "");
    }

    public static ApiResponse<Object> success(Object t) {
        return new ApiResponse("0", "", t);
    }

    public static ApiResponse<String> fail(String errorCode, String errorInfo) {
        return new ApiResponse(errorCode, errorInfo, "");
    }

}
