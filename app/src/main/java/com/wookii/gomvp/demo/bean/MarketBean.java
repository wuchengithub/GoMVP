package com.wookii.gomvp.demo.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wuchen on 2018/4/17.
 */

public class MarketBean {


    /**
     * success : true
     * message : 成功
     * resultCode : 1000
     * value : {"marketData":{"amount":0,"qtty":0,"customerPrice":0,"onlineSalesRate":0,"offlineSalesRate":0,"amountRtv":0,"ordsRtv":0,"qttyRtv":0,"skusRtv":0,"customerPriceRtv":0}}
     * totalElements : 0
     */

    private boolean success;
    private String message;
    private String resultCode;
    private ValueBean value;
    private int totalElements;

    public static MarketBean objectFromData(String str) {

        return new Gson().fromJson(str, MarketBean.class);
    }

    public static MarketBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), MarketBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public static class ValueBean {
        /**
         * marketData : {"amount":0,"qtty":0,"customerPrice":0,"onlineSalesRate":0,"offlineSalesRate":0,"amountRtv":0,"ordsRtv":0,"qttyRtv":0,"skusRtv":0,"customerPriceRtv":0}
         */

        private MarketDataBean marketData;

        public static ValueBean objectFromData(String str) {

            return new Gson().fromJson(str, ValueBean.class);
        }

        public static ValueBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ValueBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public MarketDataBean getMarketData() {
            return marketData;
        }

        public void setMarketData(MarketDataBean marketData) {
            this.marketData = marketData;
        }

        public static class MarketDataBean {
            /**
             * amount : 0.0
             * qtty : 0.0
             * customerPrice : 0.0
             * onlineSalesRate : 0
             * offlineSalesRate : 0
             * amountRtv : 0.0
             * ordsRtv : 0.0
             * qttyRtv : 0.0
             * skusRtv : 0.0
             * customerPriceRtv : 0.0
             */

            private double amount;
            private double qtty;
            private double customerPrice;
            private int onlineSalesRate;
            private int offlineSalesRate;
            private double amountRtv;
            private double ordsRtv;
            private double qttyRtv;
            private double skusRtv;
            private double customerPriceRtv;

            public static MarketDataBean objectFromData(String str) {

                return new Gson().fromJson(str, MarketDataBean.class);
            }

            public static MarketDataBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), MarketDataBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getQtty() {
                return qtty;
            }

            public void setQtty(double qtty) {
                this.qtty = qtty;
            }

            public double getCustomerPrice() {
                return customerPrice;
            }

            public void setCustomerPrice(double customerPrice) {
                this.customerPrice = customerPrice;
            }

            public int getOnlineSalesRate() {
                return onlineSalesRate;
            }

            public void setOnlineSalesRate(int onlineSalesRate) {
                this.onlineSalesRate = onlineSalesRate;
            }

            public int getOfflineSalesRate() {
                return offlineSalesRate;
            }

            public void setOfflineSalesRate(int offlineSalesRate) {
                this.offlineSalesRate = offlineSalesRate;
            }

            public double getAmountRtv() {
                return amountRtv;
            }

            public void setAmountRtv(double amountRtv) {
                this.amountRtv = amountRtv;
            }

            public double getOrdsRtv() {
                return ordsRtv;
            }

            public void setOrdsRtv(double ordsRtv) {
                this.ordsRtv = ordsRtv;
            }

            public double getQttyRtv() {
                return qttyRtv;
            }

            public void setQttyRtv(double qttyRtv) {
                this.qttyRtv = qttyRtv;
            }

            public double getSkusRtv() {
                return skusRtv;
            }

            public void setSkusRtv(double skusRtv) {
                this.skusRtv = skusRtv;
            }

            public double getCustomerPriceRtv() {
                return customerPriceRtv;
            }

            public void setCustomerPriceRtv(double customerPriceRtv) {
                this.customerPriceRtv = customerPriceRtv;
            }
        }
    }
}
