package joslabs.cryptocurrency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button btncheck, btnconvert;
    TextView edtbtc,edtetc,txconvert;
    Spinner spncurrency;
    List<String> currencies=new ArrayList<>();
String currencySelected, myconvert ,valbtc, valeth;
    ArrayAdapter<String > currencyadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       btncheck= (Button) findViewById(R.id.btncheck);
        btnconvert = (Button) findViewById(R.id.btnconvert);
        edtbtc= (TextView) findViewById(R.id.edtbtc);
        edtetc= (TextView) findViewById(R.id.edtetc);
        txconvert= (TextView) findViewById(R.id.txtconvtcurrency);
        spncurrency= (Spinner) findViewById(R.id.currencyspn);
        spncurrency.setBackgroundResource(R.color.goldicon);

        currencyadapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,currencies);
              currencyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencies.add("Australian Dollar,AUD");
        currencies.add("Brazilian Real,BRL");
        currencies.add("Canadian Dollar,CAD");
        currencies.add("Chinese Yuan,CNY");
        currencies.add("Czech Koruna,CZK");
        currencies.add("Danich Krone,DKK");
        currencies.add("Euros,EUR");
        currencies.add("Great Britain Pounds,GBP");
        currencies.add("Hong Kong Dollar,HKD");
        currencies.add("Indian Rupee,INR");
        currencies.add("Isreali New Shekel,ILS");
        currencies.add("Japanese Yen,JPY");
        currencies.add("Kenya Shs,KES");
        currencies.add("Nigerian Naira,NGN");
        currencies.add("Norwegian Krone,NOK");
        currencies.add("Russian Ruble,RUB");
        currencies.add("Singapore Dollar,SGD");
        currencies.add("Swiss Franc,CHF");
        currencies.add("Turkish Lira,TRY");
        currencies.add("US dollars,USD");

//USD,EUR,KES,NGN,GBP,AUD,CAD,BRL,CNY,JPY,HKD,RUB,CZK,DKK,INR,ILS,CHF,TRY,NOK,SGD
        spncurrency.setAdapter(currencyadapter);
        spncurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mystring =spncurrency.getSelectedItem().toString();
                myconvert = mystring;
                String mystring2= mystring.substring(mystring.indexOf(",")+1);
                currencySelected= mystring2;
                //currencySelected=spncurrency.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Requesting...", "Please wait...", false, false);
                //   if(validated) {
//String ORDER_URL="https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR,KES,NGN,GBP,AUD,CAD,BRL,CNY,JPY,HKD,RUB,CZK,DKK,INR,ILS,CHF,TRY,NOK,SGD";

                String ORDER_URL="https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms="+currencySelected;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("userData", response);
//progress.dismiss();
                        loading.dismiss();

                        try {
                            JSONObject jsonObjectb = new JSONObject(response);
                            JSONObject btc = new JSONObject(jsonObjectb.getString("BTC"));
                            JSONObject eth = new JSONObject(jsonObjectb.getString("ETH"));
                            String mybtc=btc.getString(currencySelected);
                            String myeth=eth.getString(currencySelected);
                            valbtc= mybtc;
                            valeth = myeth;
                            Log.e("btc",mybtc+"\t"+myeth);
                            edtbtc.setText(mybtc);
                            edtetc.setText(myeth);
                            txconvert.setText (myconvert);

                        }catch (Exception e)
                        {

                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("vError", error.toString());
                        error.printStackTrace();



                        if (error instanceof NoConnectionError) {
                            //  Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();
                        }

                        if (error instanceof NetworkError) {
                            // Toast.makeText(Motorregister.this, "No Internet", Toast.LENGTH_LONG).show();

                        }

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();


                        System.out.print("was here");
                        Log.e("paramx", params.toString());
                        return params;
                    }
                };

                int x = 2;// retry count
                stringRequest.setRetryPolicy(new

                        DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                        x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(getApplicationContext()).

                        add(stringRequest);
            }
        });

        btnconvert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(getApplicationContext(), Converter.class);
                ints.putExtra("currency", myconvert);
                ints.putExtra("btcvalue", valbtc);
                ints.putExtra("ehtvalue", valeth);
                startActivity(ints);
            }


        });
    }

}
