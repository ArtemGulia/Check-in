package io.uapps.check.in.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.uapps.check.in.R;
import io.uapps.check.in.util.mail.AsyncMailTask;

/**
 * Check-in
 * Created by G_Art on 31/1/2015.
 */
public class CheckInFragment extends Fragment implements View.OnClickListener {

    private final static int ENTER = 0;
    private final static int THX = 1;

    private EditText edTxtName;
    private Button btnCheckin;
    private TextView txtThanks;
    private TextView txtName;
    private String senderMail;
    private EmailListener mListener;

    public interface EmailListener {
        public String getEmail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fr_layout_checkin, container, false);

        edTxtName = (EditText) view.findViewById(R.id.edTxtName);
        btnCheckin = (Button) view.findViewById(R.id.btnCheckin);
        txtThanks = (TextView) view.findViewById(R.id.txtThx);
        txtName = (TextView) view.findViewById(R.id.txtName);

        btnCheckin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        senderMail = mListener.getEmail();
        Editable editable = edTxtName.getText();
        if (!editable.toString().equals("")) {
            txtName.setText(editable);
            changeVisibility(THX);

            new AsyncMailTask().execute(editable.toString(), senderMail);

            new CountDownTimer(15000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d("test", "seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    edTxtName.setText("");
                    txtName.setText("");
                    changeVisibility(ENTER);
                }
            }.start();
        } else {
            Toast.makeText(getActivity(), R.string.empty_name, Toast.LENGTH_SHORT).show();
        }
    }

    private void changeVisibility(int state) {
        switch (state) {
            case ENTER:
                edTxtName.setVisibility(View.VISIBLE);
                btnCheckin.setVisibility(View.VISIBLE);
                txtThanks.setVisibility(View.INVISIBLE);
                txtName.setVisibility(View.INVISIBLE);
                break;
            case THX:
                edTxtName.setVisibility(View.INVISIBLE);
                btnCheckin.setVisibility(View.INVISIBLE);
                txtThanks.setVisibility(View.VISIBLE);
                txtName.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (EmailListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement EmailListener");
        }
    }
}
