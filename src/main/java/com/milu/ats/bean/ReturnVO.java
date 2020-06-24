package com.milu.ats.bean;

/**
 * @author max.chen
 * @class
 */
public class ReturnVO<T> {
    private static final int SUCCESS_CODE = 0;
    private static final int FAIL_CODE = 1;
    private static final String SUCCESS_MSG = "请求成功";
    private static final String FAIL_MSG = "请求失败";

    //返回码 0-成功，1-服务错误
    private int code;
    //返回码对应说明
    private String msg;
    //返回具体数据
    private T data;

    public ReturnVO(int code, String msg) {
        this(code, msg, (T) null);
    }

    public static ReturnVO success() {
        return new ReturnVO(SUCCESS_CODE, SUCCESS_MSG, (Object) null);
    }

    public static <T> ReturnVO success(T t) {
        return new ReturnVO(SUCCESS_CODE, SUCCESS_MSG, t);
    }

    public static ReturnVO fail() {
        return new ReturnVO(FAIL_CODE, FAIL_MSG, (Object) null);
    }

    public static <T> ReturnVO.ResponseAOBuilder<T> builder() {
        return new ReturnVO.ResponseAOBuilder();
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ReturnVO)) {
            return false;
        } else {
            ReturnVO<?> other = (ReturnVO) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ReturnVO;
    }

    @Override
    public String toString() {
        return "ReturnVO(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public ReturnVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ReturnVO() {
    }

    public static class ResponseAOBuilder<T> {
        private int code;
        private String msg;
        private T data;

        public ReturnVO.ResponseAOBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public ReturnVO.ResponseAOBuilder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public ReturnVO.ResponseAOBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ReturnVO<T> build() {
            return new ReturnVO(this.code, this.msg, this.data);
        }

        @Override
        public String toString() {
            return "ReturnVO.ResponseAOBuilder(code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ")";
        }
    }
}

