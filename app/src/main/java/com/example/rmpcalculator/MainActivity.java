package com.example.rmpcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rmpcalculator.R;

import org.apache.http.HttpException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button num9, num8, num7, num6, num5, num4, num3, num2, num1, num0, plus, minus, divide, equ, comma, clear, mul;
    ProgressBar progress;
    EditText numField;
    ArrayList<Double> operands = new ArrayList<Double>();
    String operand = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        num0 = findViewById(R.id.num0);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        divide = findViewById(R.id.divide);
        mul = findViewById(R.id.mul);
        equ = findViewById(R.id.equ);
        comma = findViewById(R.id.comma);
        clear = findViewById(R.id.clear);

        progress = findViewById(R.id.progress);

        numField = findViewById(R.id.numField);

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "1");
                operand += "1";
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "2");
                operand += "2";
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "3");
                operand += "3";
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "4");
                operand += "4";
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "5");
                operand += "5";
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "6");
                operand += "6";
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "7");
                operand += "7";
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "8");
                operand += "8";
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "9");
                operand += "9";
            }
        });
        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "0");
                operand += "0";
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText("");
                operand = "";
                operands = new ArrayList<Double>();
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "*");
                operands.add(Double.parseDouble(operand));
                operand = "";
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "/");
                operands.add(Double.parseDouble(operand));
                operand = "";
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "+");
                operands.add(Double.parseDouble(operand));
                operand = "";
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + "-");
                operands.add(Double.parseDouble(operand));
                operand = "";
            }
        });
        comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numField.setText(numField.getText() + ",");
                operand += ".";
            }
        });
        equ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading(true);
                String expression = numField.getText().toString();
                Double result = 0d;
                new HttpCalculatorRequest().execute(expression);

                operand = result.toString();
                loading(false);
            }
        });

    }

    private class HttpCalculatorRequest extends AsyncTask<String, String, Double>
    {
        private HttpCalculator _httpCalculator = new HttpCalculator();

        @Override
        protected Double doInBackground(String... strings) {
            try {
                return _httpCalculator.calculate(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Double result) {
            super.onPostExecute(result);
            numField.setText(result.toString());
        }
    }

    public static Double sum(ArrayList<Double> list){
        Double sum =0.0;
        for (Double d: list) {
            sum += d;
        }
        return sum;
    }

    public void loading(boolean isStart){
        if(isStart){
            progress.setVisibility(View.VISIBLE);
        }
        else{
            progress.setVisibility(View.INVISIBLE);
        }
    }
}