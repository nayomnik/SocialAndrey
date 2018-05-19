package andrey.ru.socialapplicationandrey.fragments.registerlogin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import andrey.ru.socialapplicationandrey.R;

/**
 * Created by User on 5/5/2017.
 */

public class RegistrationThirdStepFragment extends Fragment {
    private View view;
    private EditText editTextPassword1;
    private EditText editTextPassword2;

    public RegistrationThirdStepFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerPrm,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_third_fragment, containerPrm, false);

        editTextPassword1 = (EditText) view.findViewById(R.id.password_1_registration_3);
        editTextPassword2 = (EditText) view.findViewById(R.id.password_2_registration_3);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public String[] getPasswords() {
        String phoneLcl, nickLcl, password1Lcl, password2Lcl;
        password1Lcl = editTextPassword1.getText().toString();
        password2Lcl = editTextPassword2.getText().toString();
        String[] passwordsLcl = new String[2];
        passwordsLcl[0] = password1Lcl;
        passwordsLcl[1] = password2Lcl;

        return passwordsLcl;
    }
}