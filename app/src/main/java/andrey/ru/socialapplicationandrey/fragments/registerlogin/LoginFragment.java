package andrey.ru.socialapplicationandrey.fragments.registerlogin;

/**
 * Created by User on 5/4/2017.
 */


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import andrey.ru.socialapplicationandrey.R;

public class LoginFragment extends Fragment {
    private View view;
    private EditText phoneEditText;
    private EditText passwordEditText;

    public LoginFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerPrm,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, containerPrm, false);
        phoneEditText = (EditText) view.findViewById(R.id.phone);
        passwordEditText = (EditText) view.findViewById(R.id.password);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public String getPhone() {
        return phoneEditText.getText().toString();
    }

    public String getPassword() {
        return passwordEditText.getText().toString();
    }
}
