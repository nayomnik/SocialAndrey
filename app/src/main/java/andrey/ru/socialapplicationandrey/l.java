package andrey.ru.socialapplicationandrey;

import android.util.Log;

/**
 * Convinuent class for logging.
 */
public class l {
    static public void a(Object o) {
        Log.v("iiiiiiiiiiiiii", o.toString());
    }

    static public void a(int o) {
        Log.v("iiiiiiiiiiiiiii", "" + o);
    }

    static public void b(Object o) {
        Log.v("***", o.toString());
    }

    static public void b(int o) {
        Log.v("***", "" + o);
    }

    static public void pause(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
            try {
                Thread.sleep(i);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                try {
                    Thread.sleep(i);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
