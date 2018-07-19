package com.example.pratyush.razorpaydemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    EditText value;
    Button pay;
    double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());
        Checkout.clearUserData(getApplicationContext());            //REMOVE THIS LINE IF YOU WANT TO KEEP PREVIOUSLY ENTERED FIELD VALUES!!
        value = (EditText)findViewById(R.id.input);
        pay = (Button)findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    private void startPayment() {
        try{
            amount = Double.parseDouble(value.getText().toString());
            if (amount < 1) {
                Toast.makeText(MainActivity.this,"Minimum amount is Re.1!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            Checkout checkout = new Checkout();
            final Activity activity = this;
            try {
                JSONObject options = new JSONObject();
                options.put("name", "");                       //ENTER MERCHANT NAME HERE!!
                options.put("description", "Payment using RazorPay");
                options.put("currency", "INR");
                options.put("amount", amount * 100);
                checkout.open(activity, options);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            Toast.makeText(MainActivity.this,"Please enter the amount!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(MainActivity.this, "Payment was Successful!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(MainActivity.this,"Payment has Failed!!", Toast.LENGTH_SHORT).show();
    }
}
