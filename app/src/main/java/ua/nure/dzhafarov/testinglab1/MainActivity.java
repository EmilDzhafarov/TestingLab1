package ua.nure.dzhafarov.testinglab1;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private int years;
    private double amountMoney;
    private double loanRate = 0.15;
    
    private int errorCode;
    
    private TextInputLayout yearsInput;
    private TextInputLayout amountMoneyInput;
    private TextInputLayout loanRateInput;
    private EditText yearsEditText;
    private EditText amountMoneyEditText;
    private EditText loanRateEditText;
    private Button calcButton;
    private TextView resultTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yearsInput = (TextInputLayout) findViewById(R.id.years_input);
        amountMoneyInput = (TextInputLayout) findViewById(R.id.amount_money_input);
        loanRateInput = (TextInputLayout) findViewById(R.id.loan_rate_input);
        
        yearsEditText = (EditText) findViewById(R.id.years_edit_text);
        yearsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateYears(s.toString());
            }
        });
        amountMoneyEditText = (EditText) findViewById(R.id.amount_money_edit_text);
        amountMoneyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateAmountMoney(s.toString());
            }
        });
        loanRateEditText = (EditText) findViewById(R.id.loan_rate_edit_text);
        loanRateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateLoanRate(s.toString());
            }
        });
        calcButton = (Button) findViewById(R.id.calc_button);
        calcButton.setOnClickListener(this);

        resultTextView = (TextView) findViewById(R.id.result_text_view);
    }
    

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.calc_button) {
            System.out.println("ERROR CODE === " + errorCode);
            if (errorCode == 0) {
                loanRate = calculateLoanRate(years, amountMoney, loanRate);
                showResult(loanRate, amountMoney);
            } else {
                resultTextView.setText("");
            }
        }
    }

    private void validateYears(String y) {
        if (y != null && y.isEmpty()) {
            yearsInput.setError("This field doesn't be empty!");
            errorCode = -1;
        } else {
            Integer years = Integer.parseInt(y); 
            if (years <= 0) {
                yearsInput.setError("Years should be positive values!");
                errorCode = 1;
            } else if (years >= 1000) {
                yearsInput.setError("Years shouldn't be more 1000!");
                errorCode = 2;
            } else {
                this.years = years;
                check();
                yearsInput.setError(null);
                errorCode = 0;
            }   
        }
    }

    private void validateAmountMoney(String m) {
        if (m != null && m.isEmpty()) {
            amountMoneyInput.setError("Amount of money doesn't be empty!");
            errorCode = -1;
        } else {
            Double money = Double.parseDouble(m);
            if (money <= 0) {
                amountMoneyInput.setError("Amount of money should be more than zero!");
                errorCode = 1;
            } else {
                this.amountMoney = money;
                check();
                amountMoneyInput.setError(null);
                errorCode = 0;
            }
        }
    }

    private void validateLoanRate(String lr) {
        if (lr != null && lr.isEmpty()) {
            loanRateInput.setError("Loan rate doesn't be empty!");
            errorCode = -1;
        } else {
            Double loanRate = Double.parseDouble(lr);
            if (loanRate < 0) {
                loanRateInput.setError("Loan rate should be positive value!");
                errorCode = 1;
            } else if (loanRate > 100) {
                loanRateInput.setError("Loan rate shouldn't be more 100");
                errorCode = 2;
            } else {
                this.loanRate = loanRate / 100;
                loanRateInput.setError(null);
                errorCode = 0;
            }   
        }
    }

    private Double calculateLoanRate(Integer years, Double money, Double l) {
        Double loanRate = l;
        if (loanRate == 0.15) {
            if (years >= 3 && years < 5) {
                loanRate += 0.01;
            }

            if (years >= 5 && years < 10) {
                loanRate += 0.02;
            }

            if (money >= 10000 && money < 20000) {
                loanRate -= 0.01;
            }

            if (money >= 20000 && money < 40000) {
                loanRate -= 0.02;
            }   
        }

        return loanRate;
    }

    private void showResult(Double loanRate, Double amountMoney) {
        resultTextView.setVisibility(View.VISIBLE);
        String result = String.format(Locale.ROOT, "Loan rate: %.2f %%\n", loanRate * 100);
        result += String.format(Locale.ROOT, "Sum to pay: %.2f $", loanRate * amountMoney + amountMoney);
        resultTextView.setText(result);
    }

    private void check() {
        resultTextView.setVisibility(View.GONE);
        if (years >= 10 || amountMoney >= 40000) {
            loanRateInput.setVisibility(View.VISIBLE);
        } else {
            loanRate = 0.15;
            loanRateInput.setVisibility(View.GONE);
            calcButton.setVisibility(View.VISIBLE);
        }
    }
}
