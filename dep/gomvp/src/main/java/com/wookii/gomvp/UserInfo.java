package com.wookii.gomvp;

/**
 * Created by wuchen on 2017/12/6.
 */

public class UserInfo {
    /**
     * cashierName : 131214
     * password : 131214
     * storeNum : 131214
     * code : 1000
     * msg : success
     * data : {"storeNum":"131214","posNum":null,"cashierName":"131214","password":"131214","token":"","errorList":""}
     */

    private int cashierName;
    private int password;
    private int storeNum;
    private String code;
    private String msg;
    private DataBean data;

    public int getCashierName() {
        return cashierName;
    }

    public void setCashierName(int cashierName) {
        this.cashierName = cashierName;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * storeNum : 131214
         * posNum : null
         * cashierName : 131214
         * password : 131214
         * token :
         * errorList :
         */

        private String storeNum;
        private Object posNum;
        private String cashierName;
        private String password;
        private String token;
        private String errorList;

        public String getStoreNum() {
            return storeNum;
        }

        public void setStoreNum(String storeNum) {
            this.storeNum = storeNum;
        }

        public Object getPosNum() {
            return posNum;
        }

        public void setPosNum(Object posNum) {
            this.posNum = posNum;
        }

        public String getCashierName() {
            return cashierName;
        }

        public void setCashierName(String cashierName) {
            this.cashierName = cashierName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getErrorList() {
            return errorList;
        }

        public void setErrorList(String errorList) {
            this.errorList = errorList;
        }
    }
}
