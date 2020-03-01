package com.example.assignment3.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    EditText txtname;
    SharedPreferences pref;
    Button btnname;

    //background tap var
    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout cLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel.class);
        //this is view, changed form root to v
        View v = inflater.inflate(R.layout.fragment_home, container, false);

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


        txtname = v.findViewById(R.id.txtName);
        btnname = v.findViewById(R.id.btnName);
        //use getActivity() to access sharedpreferences through MainActivity

        pref = getActivity().getSharedPreferences("nameInfo", Context.MODE_PRIVATE);


        //set onclick listener - utilize view (v)
        btnname.setOnClickListener(this);
        return v;

    }

    //when button is pressed take value in name store in SharedPreferences
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnName:
                boolean t = TextUtils.isEmpty(txtname.getText() );

                //if no name is entered dont submit value
                if (!t) {
                    String name = txtname.getText().toString();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name", name);
                    editor.commit();
                    Toast.makeText(getActivity().getApplicationContext(), "Name stored. Click Convert Tab.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity().getApplicationContext(), "Enter name", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}