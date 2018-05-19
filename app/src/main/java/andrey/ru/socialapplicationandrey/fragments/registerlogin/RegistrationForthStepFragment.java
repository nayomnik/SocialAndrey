package andrey.ru.socialapplicationandrey.fragments.registerlogin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andrey.ru.socialapplicationandrey.R;

/**
 * Created by User on 5/5/2017.
 */

public class RegistrationForthStepFragment extends Fragment {
    private View view;

    public RegistrationForthStepFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerPrm,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_forth_fragment, containerPrm, false);

//         editTextPhone = (EditText) view.findViewById(R.id.phone_registration);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}