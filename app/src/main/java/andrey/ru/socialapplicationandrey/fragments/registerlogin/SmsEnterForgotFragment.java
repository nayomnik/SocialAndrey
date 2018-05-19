package andrey.ru.socialapplicationandrey.fragments.registerlogin;

import android.app.Fragment;
import android.os.Bundle;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SmsEnterForgotFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SmsEnterForgotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SmsEnterForgotFragment extends RegistrationSecondStepFragment {

    public SmsEnterForgotFragment() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        button.setTag("forgot");
    }
}
