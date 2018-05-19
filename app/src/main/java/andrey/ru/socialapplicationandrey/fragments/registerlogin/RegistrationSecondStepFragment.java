package andrey.ru.socialapplicationandrey.fragments.registerlogin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import andrey.ru.socialapplicationandrey.R;

/**
 * Created by User on 5/5/2017.
 */

public class RegistrationSecondStepFragment extends Fragment {
    private View view;
    private EditText editTextPhone, editTextNick;
    protected Button button;

    public RegistrationSecondStepFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerPrm,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_second_fragment, containerPrm, false);

        button = (Button) view.findViewById(R.id.register_2_button);
        button.setTag("registration");
        editTextPhone = (EditText) view.findViewById(R.id.code_registration2);
        return view;
    }


    public boolean isSMSCodeCorrect() {
        if (editTextPhone.getText().toString().equals("0000"))
            return true;
        return false;
    }

    public String getConfirmationCode() {
        return editTextPhone.getText().toString();
    }
//
//    public void handleRegister() {
//    String phoneLcl,nickLcl,password1Lcl,password2Lcl;
//        phoneLcl= editTextPhone.getText().toString();
//        nickLcl = editTextNick.getText().toString();
//        password1Lcl = editTextPassword1.getText().toString();
//        password2Lcl = editTextPassword2.getText().toString();
//        new RegistrationHandler(phoneLcl,   nickLcl  , password1Lcl ,password2Lcl);
//    }
}