package com.kiri.ai;

import android.app.Activity;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.razorpay.Checkout;
import org.json.JSONObject;

@CapacitorPlugin(name = "NativeRazorpay")
public class RazorpayPlugin extends Plugin {

    @PluginMethod
    public void open(PluginCall call) {
        MainActivity.pendingPaymentCall = call;

        String key = call.getString("key");
        Object amountObj = call.getData().get("amount");
        String currency = call.getString("currency", "INR");
        String name = call.getString("name", "Kiri-AI");
        String desc = call.getString("description", "Premium Subscription");
        String orderId = call.getString("order_id", "");
        
        Log.d("RazorpayPlugin", "Opening checkout for order: " + orderId);

        JSObject prefillJS = call.getObject("prefill");
        String email = prefillJS != null ? prefillJS.getString("email") : "";
        String contact = prefillJS != null ? prefillJS.getString("contact") : "";
        
        JSObject themeJS = call.getObject("theme");
        String color = themeJS != null ? themeJS.getString("color") : "#6750A4";

        Activity activity = getActivity();
        if (activity == null) {
            call.reject("Activity is null");
            return;
        }
        
        Checkout.preload(activity.getApplicationContext());
        Checkout checkout = new Checkout();
        checkout.setKeyID(key);

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", desc);
            options.put("currency", currency);
            
            // Amount can be passed as String or Number from JS, Razorpay expects an Integer in paise
            if (amountObj instanceof Number) {
                options.put("amount", ((Number) amountObj).longValue());
            } else if (amountObj instanceof String) {
                options.put("amount", Long.parseLong((String) amountObj));
            } else {
                throw new Exception("Invalid amount type: " + (amountObj != null ? amountObj.getClass().getName() : "null"));
            }
            
            if (orderId != null && !orderId.isEmpty()) {
                options.put("order_id", orderId);
            }
            
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", contact);
            options.put("prefill", preFill);

            JSONObject theme = new JSONObject();
            theme.put("color", color);
            options.put("theme", theme);

            Log.d("RazorpayPlugin", "Checkout options: " + options.toString());
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("Razorpay", "Error in starting Razorpay Checkout", e);
            call.reject("Error in starting Razorpay Checkout: " + e.getMessage());
            MainActivity.pendingPaymentCall = null;
        }
    }
}
