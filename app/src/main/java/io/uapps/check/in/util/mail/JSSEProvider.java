package io.uapps.check.in.util.mail;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;

/**
 * Check-in
 * Created by G_Art on 1/2/2015.
 */
public class JSSEProvider extends Provider {

    public JSSEProvider(){
        super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                put("SSLContext.TLS",
                        "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
                put("Alg.Alias.SSLContext.TLSv1", "TLS");
                put("KeyManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
                put("TrustManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
                return null;
            }
        });
    }
}
