package com.kiri.ai;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;
import com.getcapacitor.PluginCall;
import com.getcapacitor.JSObject;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

public class MainActivity extends BridgeActivity implements PaymentResultWithDataListener {

    public static PluginCall pendingPaymentCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(RazorpayPlugin.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPaymentSuccess(String paymentId, PaymentData data) {
        if (pendingPaymentCall != null) {
            JSObject ret = new JSObject();
            ret.put("razorpay_payment_id", data.getPaymentId());
            ret.put("razorpay_order_id", data.getOrderId());
            ret.put("razorpay_signature", data.getSignature());
            pendingPaymentCall.resolve(ret);
            pendingPaymentCall = null;
        }
    }

    @Override
    public void onPaymentError(int code, String response, PaymentData data) {
        if (pendingPaymentCall != null) {
            pendingPaymentCall.reject(response);
            pendingPaymentCall = null;
        }
    }
}
