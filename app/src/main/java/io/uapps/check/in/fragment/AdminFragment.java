package io.uapps.check.in.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.uapps.check.in.R;

/**
 * Check-in
 * Created by G_Art on 31/1/2015.
 */
public class AdminFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private KeepScreenOnListener mListener;

    private final String passwordEtalon = "e16b2ab8d12314bf4efbd6203906ea6c";

    private Button btnExit;
    private Button btnChEmail;
    private EditText edPass;
    private EditText edEmail;
    private CheckBox chBoxPreventSleep;

    public interface KeepScreenOnListener {
        public void changeSleep(boolean flag);

        public void changeEmail(String email);

        public boolean isKeepOn();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fr_layout_admin, container, false);

        btnExit = (Button) view.findViewById(R.id.btnExit);
        btnChEmail = (Button) view.findViewById(R.id.btnChangeEmail);

        edPass = (EditText) view.findViewById(R.id.edPass);
        edEmail = (EditText) view.findViewById(R.id.edEmail);

        chBoxPreventSleep = (CheckBox) view.findViewById(R.id.chBoxPreventSleep);

        btnExit.setOnClickListener(this);
        btnChEmail.setOnClickListener(this);

        chBoxPreventSleep.setChecked(mListener.isKeepOn());
        chBoxPreventSleep.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                if (passwordEtalon.equals(md5(edPass.getText().toString()))) {
                    getActivity().getPackageManager().clearPackagePreferredActivities(getActivity().getPackageName());
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), R.string.wrong_pass, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnChangeEmail:
                if (passwordEtalon.equals(md5(edPass.getText().toString()))) {
                    if (!"".equals(edEmail.getText().toString())) {
                        mListener.changeEmail(edEmail.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), R.string.email, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.wrong_pass, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mListener.changeSleep(isChecked);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (KeepScreenOnListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement KeepScreenOnListener");
        }
    }

    private String md5(String input) {
        String md5 = null;

        if (null == input) return null;

        try {

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }
}
