package com.example.assignment3.ui.convert;
import com.example.assignment3.R;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertFragment extends Fragment implements View.OnClickListener {

    private ConvertViewModel mViewModel;
    EditText txtTemp;
    Button btnConvert;
    Switch switchCon;
    TextView lblWelcome;
    SharedPreferences prf;
    SharedPreferences prefConvert;

    //background tap var
    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout cLayout;

    public static ConvertFragment newInstance() {
        return new ConvertFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //set up view
        View v = inflater.inflate(R.layout.convert_fragment, container, false);

        //background tap code
        mContext = getContext().getApplicationContext();
        mActivity = getActivity();
        //had to add constraint-layout manually as id for the layout in activity-main under text
        cLayout = v.findViewById(R.id.constraint_layout);
        cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        txtTemp = v.findViewById(R.id.txtTemp);
        btnConvert = v.findViewById(R.id.btnConvert);
        switchCon = v.findViewById(R.id.switchTemp);
        lblWelcome = v.findViewById(R.id.lblWelcome);
        //set up new preference
        prefConvert = getActivity().getSharedPreferences("convertInfo", Context.MODE_PRIVATE);


        //retrieve name from sharedPref and set welcome label
        prf = getActivity().getSharedPreferences("nameInfo", Context.MODE_PRIVATE);

        String name = prf.getString("name", "empty");

        lblWelcome.setText("Hello " + name + ", please enter a temperature to be converted.");

        //store the temp and switch state in a new SharedPref
        //set listener for click on  button
        btnConvert.setOnClickListener(this);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConvertViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConvert:
                boolean t = TextUtils.isEmpty(txtTemp.getText() );

                //if no temp is entered dont submit values
                if (!t) {
                    float temp = Float.parseFloat(txtTemp.getText().toString());
                    boolean switchState = switchCon.isChecked();

                    SharedPreferences.Editor editor = prefConvert.edit();
                    editor.putBoolean("switchState", switchState);
                    editor.putFloat("temp", temp);
                    editor.commit();
                    Toast.makeText(getActivity().getApplicationContext(),"Values entered. Click Results Tab.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity().getApplicationContext(),"Enter temperature", Toast.LENGTH_SHORT).show();


                break;
        }
    }




}
