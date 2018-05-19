package andrey.ru.socialapplicationandrey.fragments.general.cityplus;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import andrey.ru.socialapplicationandrey.R;

/**
 * Created by User on 5/29/2017.
 */

public class CityPlusFragment extends Fragment {
    private View view;
    private EditText phoneEditText;
    private EditText passwordEditText;

    public CityPlusFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerPrm,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.city_plus_fragment, containerPrm, false);
//        editTextCodeFromSMS= (EditText) view.findViewById(R.id.code);
//        editTextPassword1 = (EditText) view.findViewById(R.id.password_1_registration_3);
//        editTextPassword2 = (EditText) view.findViewById(R.id.password_2_registration_3);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public String[] getValuess() {
        String codeLcl, password1Lcl, password2Lcl;
//        codeLcl= editTextCodeFromSMS.getText().toString();
//        password1Lcl = editTextPassword1.getText().toString();
//        password2Lcl = editTextPassword2.getText().toString();
//        String[] valuesLcl = new String[3];
//        valuesLcl[0]=codeLcl;
//        valuesLcl[1] = password1Lcl;
//        valuesLcl[2] = password2Lcl;

        return null/*valuesLcl*/;
    }
}