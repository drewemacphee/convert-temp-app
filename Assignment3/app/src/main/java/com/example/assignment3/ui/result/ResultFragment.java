package com.example.assignment3.ui.result;
import com.example.assignment3.R;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultFragment extends Fragment {

    private ResultViewModel mViewModel;
    TextView result;
    SharedPreferences prefName;
    SharedPreferences prefTemp;
    Double newTemp;
    String strOldTemp;
    String strNewTemp;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //set up view
        View v = inflater.inflate(R.layout.result_fragment, container, false);

        result = v.findViewById(R.id.lblResult);

        //retrieve values from sharedPrefs
        prefName = getActivity().getSharedPreferences("nameInfo", Context.MODE_PRIVATE);
        String name = prefName.getString("name", "empty");

        prefTemp = getActivity().getSharedPreferences("convertInfo", Context.MODE_PRIVATE);
        Float oldTemp = prefTemp.getFloat("temp", 0);
        Boolean swState = prefTemp.getBoolean("switchState", false);

        if (swState) {
            //convert to celsius
            newTemp = (oldTemp - 32)/1.8;
            strNewTemp = "celsius";
            strOldTemp = "fahrenheit";
        }
        else {
            //convert to fahrenheit
            newTemp = 1.8 * oldTemp + 32;
            strNewTemp = "fahrenheit";
            strOldTemp = "celsius";
        }

        //set label
        result.setText("Hello " + name + ", your previous temperature of "+ oldTemp + " " + strOldTemp + " has been converted to " +(String.format("%.2f", newTemp)) + " " + strNewTemp +".");




        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        // TODO: Use the ViewModel
    }

}
