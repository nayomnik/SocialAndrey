package andrey.ru.socialapplicationandrey.fragments.registerlogin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import andrey.ru.socialapplicationandrey.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForgotPassword1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForgotPassword1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPassword1Fragment extends Fragment {
    private View view;
    private EditText editTextPhone;

    public ForgotPassword1Fragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerPrm,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgot_password_fragment, containerPrm, false);

        editTextPhone = (EditText) view.findViewById(R.id.phone_registration);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void handleRegister() {
        String phoneLcl, nickLcl, password1Lcl, password2Lcl;
        phoneLcl = editTextPhone.getText().toString();
//        new RegistrationHandler(  nickLcl  , password1Lcl ,password2Lcl);
    }

    public String getPhone() {

        return editTextPhone.getText().toString();
    }
}