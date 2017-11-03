package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class BitcoinModel {
    private float value;
    private String currency;

    public BitcoinModel() {
    }

    public BitcoinModel(float value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLabel () {
        return String.valueOf(value) + " " + currency;
    }

    public static BitcoinModel fromJson(JSONObject jsonObject, String currency) throws JSONException {
        BitcoinModel model = new BitcoinModel();
        JSONObject baseObject = jsonObject.getJSONObject("BTC" + currency);
        model.setCurrency(currency);
        model.setValue((float)baseObject.getDouble("ask"));
        return model;
    }

    @Override
    public String toString() {
        return "BitcoinModel{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
