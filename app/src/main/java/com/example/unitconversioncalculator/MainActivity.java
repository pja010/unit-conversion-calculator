package com.example.unitconversioncalculator;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private static final double WEIGHT_CONVERSION_CONSTANT = 2.20462262;
    private static final double LENGTH_CONVERSION_CONSTANT = 30.48;
    private final String UNIT_WEIGHT = "Weight";
    private final String LBS = "lbs: ";
    private final String KG = "kg:";
    private final String FT = "ft:";
    private final String CM = "cm: ";

    private String spinnerUnit;
    private TextView textViewTop;
    private TextView textViewBelow;
    private Boolean isBtnChecked = false;
    private EditText inputText;
    private TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputUnitText);
        outputText = findViewById(R.id.outputUnitText);
        ToggleButton toggleButtonUnit = findViewById(R.id.toggleButtonUnit);
        textViewTop = findViewById(R.id.textViewTop);
        textViewBelow = findViewById(R.id.textViewBelow);
        Spinner spinner = findViewById(R.id.spinnerDimensionType);

        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dimension_type_array, R.layout.spinner_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        toggleButtonUnit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBtnChecked = isChecked;
                switchUnits();
                initializeText();
            }
        });

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 0) {
                    processText(s);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    processText(s);
                } else {
                    outputText.setText(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    processText(s);
                }
            }
        });
    }

    private void initializeText() {
        inputText.setText(null);
        outputText.setText(null);
    }

    private void updateUnitLabels(String inputUnitLabel, String outputUnitLabel) {
        textViewTop.setText(inputUnitLabel);
        textViewBelow.setText(outputUnitLabel);
    }

    private void processText(CharSequence s) {
        if (s.length() > 0) {
            float inputNumber = Float.parseFloat(s.toString());
            float outputNumber = convertUnitData(inputNumber);
            outputText.setText(String.valueOf(outputNumber));
        }
    }

    private float convertUnitData(float inputNumber) {
        float outputNumber;
        if (isBtnChecked) {
            if (spinnerUnit.equals(UNIT_WEIGHT)) {
                outputNumber = convertLbsToKg(inputNumber);
            } else {
                outputNumber = convertFeetToCm(inputNumber);
            }
        } else {
            if (spinnerUnit.equals(UNIT_WEIGHT)) {
                outputNumber = convertKgToLbs(inputNumber);
            } else {
                outputNumber = convertCmToFeet(inputNumber);
            }
        }
        return outputNumber;
    }

    private void switchUnits() {
        if (isBtnChecked) {
            if (spinnerUnit.equals(UNIT_WEIGHT)) {
                updateUnitLabels(LBS, KG);
            } else {
                updateUnitLabels(FT, CM);
            }
        } else {
            if (spinnerUnit.equals(UNIT_WEIGHT)) {
                updateUnitLabels(KG, LBS);
            } else {
                updateUnitLabels(CM, FT);
            }
        }
    }

    private static float convertLbsToKg(float tensionLbs) {
        return (float) (tensionLbs / WEIGHT_CONVERSION_CONSTANT);
    }

    private static float convertKgToLbs(float tensionKg) {
        return (float) (WEIGHT_CONVERSION_CONSTANT * tensionKg);
    }

    private static float convertFeetToCm(float lengthFeet) {
        return (float) (lengthFeet * LENGTH_CONVERSION_CONSTANT);
    }

    private static float convertCmToFeet(float lengthCm) {
        return (float) (lengthCm / LENGTH_CONVERSION_CONSTANT);
    }


    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerUnit = parent.getItemAtPosition(position).toString();
        switchUnits();
        initializeText();
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}