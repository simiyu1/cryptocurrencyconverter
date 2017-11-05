package joslabs.cryptocurrency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Converter extends AppCompatActivity {

    String mcurrency;
    String mbtcvalue;
    String methvalue;
    Double ethconv,btcconv;
    EditText  convvalue;
    TextView txtcurrency, edteth,edtbtc;
    Button btnconvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        txtcurrency= (TextView) findViewById(R.id.converted);
        edtbtc = (TextView) findViewById(R.id.convertedbtc);
        edteth = (TextView) findViewById(R.id.convertedeth);
        convvalue = (EditText) findViewById(R.id.valuetoconvert);
        btnconvert = (Button) findViewById(R.id.doconvert);


        Intent intent = getIntent();
        mbtcvalue= intent.getStringExtra("btcvalue");
        methvalue = intent.getStringExtra("ethvalue");
        mcurrency = intent.getStringExtra("currency");

        txtcurrency.setText(mcurrency);

        btnconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(convvalue.getText().toString().trim().length()<=0){
                    Toast.makeText(Converter.this, "Converter is empty", Toast.LENGTH_SHORT).show();
                }
                int ss = Integer.parseInt(convvalue.getText().toString());

                ethconv=ss/Double.parseDouble(mbtcvalue);
                btcconv=ss/Double.parseDouble(mbtcvalue);
                edtbtc.setText(ethconv+"");
                edteth.setText(btcconv+"");

            }
        });






    }

}
