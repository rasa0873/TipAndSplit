package com.siraapps.raul.tipandsplitrevb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class MainActivityCal extends AppCompatActivity {

    private EditText displayEditText;
    private EditText upperEditText;

    private Button buttonZero, buttonOne, buttonTwo, buttonThree, buttonFour, buttonEqu,
            buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine, buttonPercent, buttonDot ;
    private ImageButton buttonPlus, buttonBack;
    private ImageButton buttonDiv, buttonMul, buttonMinus;

    private String billAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(R.string.calculator_subtitle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);

        // Wire preliminary result TextView
        upperEditText = findViewById(R.id.upperTextView);

        // Wire EditText to layout
        displayEditText = findViewById(R.id.displayEditText);

        // Wire buttons to layout
        buttonZero = findViewById(R.id.zero);
        buttonOne = findViewById(R.id.one);
        buttonTwo = findViewById(R.id.two);
        buttonThree = findViewById(R.id.three);
        buttonFour = findViewById(R.id.four);
        buttonFive = findViewById(R.id.five);
        buttonSix = findViewById(R.id.six);
        buttonSeven = findViewById(R.id.seven);
        buttonEight = findViewById(R.id.eight);
        buttonNine = findViewById(R.id.nine);

        buttonDiv = findViewById(R.id.div);
        buttonMul = findViewById(R.id.multiply);
        buttonMinus = findViewById(R.id.minus);
        buttonPlus = findViewById(R.id.plus);
        buttonBack = findViewById(R.id.back);
        buttonPercent = findViewById(R.id.percent);
        buttonDot = findViewById(R.id.dot);
        buttonEqu = findViewById(R.id.equ);

        // activate Listeners for buttons
        activateListeners();

        Intent i3 = getIntent();
        billAmount = i3.getStringExtra("currentBillAmount");
        assert billAmount != null;
        if (!billAmount.isEmpty() && billAmount.length() <= 13){
            displayEditText.setText(billAmount);
        } else {
            displayEditText.setText("0");
        }

    }

    private Boolean checkMaxLengthEditText(){
        boolean maxLengthReached = false;
        if (displayEditText.getText().length() == 13) {
            maxLengthReached = true;
            Toast.makeText(this, getResources().getString(R.string.max_length_reached_input),
                    Toast.LENGTH_SHORT).show();
        }
        return maxLengthReached;
    }

    @Override
    public boolean onSupportNavigateUp() {
        sendResponse();
        finish();
        return true;
    }

    private void activateListeners(){

        // Dot button
        buttonDot.setOnClickListener(v -> {

            if (displayEditText.getText().length() > 11){ // Only for dot button
                Toast.makeText(MainActivityCal.this, getResources().getString(R.string.max_length_reached_input),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (displayEditText.getText().toString().isEmpty()){
                displayEditText.setText("0.");
                displayEditText.setSelection(2);
                return;
            }

            if (verifyOperatorInserted())  // if last character is not a symbol
                if (!verifyDotInserted()) { // if dot was not already inserted
                    insertTextSymbol(".");
                }
        });

        // Equal Button
        buttonEqu.setOnClickListener(v -> {
            if (!upperEditText.getText().toString().isEmpty()) { // if something in upperEditText
                if (verifyOperatorInserted())
                    insertTextSymbol("=");
            }
        });

        // Division button
        buttonDiv.setOnClickListener(v -> {
            if (verifyOperatorInserted()) {
                if (!upperEditText.getText().toString().isEmpty()){
                    // execute equal and then execute multiply
                    insertTextSymbol("=");
                }

                insertTextSymbol("/");
            }
        });

        // Plus button
        buttonPlus.setOnClickListener(v -> {
            if (verifyOperatorInserted()) {
                if (!upperEditText.getText().toString().isEmpty()){
                    // execute equal and then execute multiply
                    insertTextSymbol("=");
                }

                insertTextSymbol("+");
            }
        });

        // Minus button
        buttonMinus.setOnClickListener(v -> {
            if (verifyOperatorInserted()) {
                if (!upperEditText.getText().toString().isEmpty()){
                    // execute equal and then execute multiply
                    insertTextSymbol("=");
                }

                insertTextSymbol("-");
            }
        });

        // Percent button
        buttonPercent.setOnClickListener(v -> {
            if (verifyOperatorInserted()) {
                if (!upperEditText.getText().toString().isEmpty()){
                    // execute equal and then execute multiply
                    insertTextSymbol("=");
                }

                insertTextSymbol("%");
            }
        });

        // Multiply button
        buttonMul.setOnClickListener(v -> {
            if (verifyOperatorInserted()) {
                // Verify if something in upperEditText
                if (!upperEditText.getText().toString().isEmpty()){
                    // execute equal and then execute multiply
                    insertTextSymbol("=");
                }
                insertTextSymbol("x");
            }
        });

        // Back button
        buttonBack.setOnLongClickListener(v -> {
            displayEditText.setText("0");
            displayEditText.setTextColor(Color.parseColor("#000000")); // black
            upperEditText.setText("");
            return true;
        });

        buttonBack.setOnClickListener(v -> {
            String displayText = displayEditText.getText().toString();
            String displayTextNew;
            if (displayText.length() > 1) {
                displayText = displayText.substring(0, displayText.length() - 1);

                boolean endsWithDotZero = displayText.endsWith(".0");

                displayTextNew = transformInsertNumber(displayText);
                if (endsWithDotZero){
                    displayTextNew = displayTextNew.concat(".0");
                }
                displayEditText.setText(displayTextNew);
                displayEditText.setSelection(displayTextNew.length());
                }
             else displayEditText.setText("0");

            displayEditText.setTextColor(Color.parseColor("#000000")); // black
        });


        // Zero button
        buttonZero.setOnClickListener(v -> {
            // send number zero to a method to retrieve
            insertTextNumber("0");
        });

        // One button
        buttonOne.setOnClickListener(v -> insertTextNumber("1"));

        // Two button
        buttonTwo.setOnClickListener(v -> insertTextNumber("2"));

        // Three button
        buttonThree.setOnClickListener(v -> insertTextNumber("3"));

        // Four button
        buttonFour.setOnClickListener(v -> insertTextNumber("4"));

        // Five button
        buttonFive.setOnClickListener(v -> insertTextNumber("5"));

        // Six button
        buttonSix.setOnClickListener(v -> insertTextNumber("6"));

        // Seven button
        buttonSeven.setOnClickListener(v -> insertTextNumber("7"));

        // Eight button
        buttonEight.setOnClickListener(v -> insertTextNumber("8"));

        // Nine button
        buttonNine.setOnClickListener(v -> insertTextNumber("9"));
    }

    private Boolean verifyDotInserted (){
        String numberText = displayEditText.getText().toString();
        return numberText.contains(".");
    }


    private void insertTextSymbol (String symbol){
        String displayText = displayEditText.getText().toString();

        if (symbol.equals(".")){
            displayEditText.setText(displayText.concat(symbol) );   // displayText + symbol
            displayEditText.setSelection(displayEditText.getText().length());
        } else if (symbol.equals("=")){
            extractCalculate();
        } else {
            upperEditText.setText(displayText.concat(symbol)); // displayText + symbol
            displayEditText.setText("");
        }
    }

    private void insertTextNumber (String number) {

        if (checkMaxLengthEditText()){
            return;
        }

        String lastNumber = displayEditText.getText().toString();
        String newNumber = lastNumber.concat(number);

            //lastNumber = displayText;

        if (number.equals("0")) {
            if (lastNumber.contains(".00")){
                displayEditText.setSelection(displayEditText.getText().length());
                return;
            } else if (lastNumber.contains(".0")){
                displayEditText.setText( newNumber);
                displayEditText.setSelection(displayEditText.getText().length());
                return;
            } else if (lastNumber.contains(".")){
                displayEditText.setText( newNumber);
                displayEditText.setSelection(displayEditText.getText().length());
                return;
            }
        }

        displayEditText.setText(transformInsertNumber(newNumber));
        displayEditText.setTextColor(Color.parseColor("#000000"));
        displayEditText.setSelection(displayEditText.getText().length());
    }

    private String transformInsertNumber(String numberToFormat) {

        Double doubleNumber;
        String formattedNumber;

        // remove commas from display number
        doubleNumber = Double.valueOf(numberToFormat.replaceAll(",",""));

        // Adjusting the Locale:
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

        DecimalFormat formato = new DecimalFormat("#,###,###.##", symbols);

        formattedNumber = formato.format(doubleNumber);

        return formattedNumber;
    }

    public Boolean verifyOperatorInserted (){

        String currentText = displayEditText.getText().toString();
        if (currentText.isEmpty()) // if there is nothing in display
            return false;

        String lastCharacter = currentText.substring(currentText.length()-1);

        switch (lastCharacter){
            case "x":
            case "-":
            case "/":
            case "+":
            case "%":
            case ".":
                return false;
            default:
                break;
        }

        return true;
    }


    private void extractCalculate () {

        String topNumberString = upperEditText.getText().toString();
        String topNumberOriginal = topNumberString;
        Double topNumberDouble; // initialize

        String lowerNumber = displayEditText.getText().toString();
        Double lowerNumberDouble; // initialize

        Double resultado; // initialize

        topNumberString = topNumberString.substring(0,topNumberString.length()-1);
        topNumberString = topNumberString.replaceAll(",","");
        topNumberDouble = Double.parseDouble(topNumberString);

        lowerNumber = lowerNumber.replaceAll(",","");
        lowerNumberDouble = Double.parseDouble(lowerNumber);

        if (topNumberOriginal.contains("x")){
            // Multiply
            resultado = topNumberDouble * lowerNumberDouble ;
        } else if (topNumberOriginal.contains("/")){
            // division
            resultado = topNumberDouble / lowerNumberDouble ;
        } else if (topNumberOriginal.contains("+")){
            // add
            resultado = topNumberDouble + lowerNumberDouble ;
        } else if (topNumberOriginal.contains("%")){
            // Percentage
            resultado = topNumberDouble * (lowerNumberDouble/100);
        } else {
            // Subtract
            resultado = topNumberDouble - lowerNumberDouble ;
        }
        if (resultado > 9.999999999E9){
            Toast.makeText(this, getResources().getString(R.string.result_exceeds_max_13),
                    Toast.LENGTH_SHORT).show();
            displayEditText.setTextColor(Color.parseColor("#FA3232")); // red
            return;
        }

        // Adjusting the Locale:
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

        DecimalFormat formato = new DecimalFormat("#,###,###.##", symbols);

        displayEditText.setText(formato.format(resultado));
        displayEditText.setTextColor(Color.parseColor("#0000FF")); // blue
        displayEditText.setSelection(displayEditText.getText().length());

        upperEditText.setText("");
    }

    public void sendResponse (){
        Intent responseIntent = new Intent();
        billAmount = displayEditText.getText().toString();
        responseIntent.putExtra("responseCalculator", billAmount);
        setResult(RESULT_OK, responseIntent);
    }

}
