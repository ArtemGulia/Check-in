package io.uapps.check.in.util.mail;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Check-in
 * Created by G_Art on 1/2/2015.
 */
public class AsyncMailTask extends AsyncTask {

//TODO change sender mail and password
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            GMailSender sender = new GMailSender(
                    "android.dev.g.art@gmail.com",
                    "androiDevGart");
            sender.sendMail("This is Subject",
                    params[0] +" has checked in.",
                    "android.dev.g.art@gmail.com",
                    (String)params[1]);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
        return null;
    }
}
