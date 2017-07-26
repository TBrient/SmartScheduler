package alex.tyler.smartscheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddHardEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddHardEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddHardEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddHardEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddHardEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddHardEventFragment newInstance(String param1, String param2) {
        AddHardEventFragment fragment = new AddHardEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initializeCalendarPopup(final EditText dateText){
        final Calendar cal = Calendar.getInstance();
        updateLabel(dateText, cal);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dateText, cal);
            }
        };

        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                new DatePickerDialog(getActivity(), date, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText dateText, Calendar cal) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(cal.getTime()));
    }

    private void initializeTimerPopup(final TextView timeText, final String title) {
        timeText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);
                mTimePicker.setTitle(title);
                mTimePicker.show();

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hard_event, container, false);

        final EditText nameField = (EditText)view.findViewById(R.id.EventName);
        final EditText dateText = (EditText)view.findViewById(R.id.dateEditText);
        initializeCalendarPopup(dateText);
        final EditText locationField = (EditText)view.findViewById(R.id.locationEditText);
        final EditText descriptionField = (EditText)view.findViewById(R.id.descriptionText);
        final TextView startTime = (TextView)view.findViewById(R.id.startTime);
        final TextView endTime = (TextView)view.findViewById(R.id.endTime);
        initializeTimerPopup(startTime, "Select Starting Time");
        initializeTimerPopup(endTime, "Select Ending Time");
        final CheckBox allDayCheck = (CheckBox)view.findViewById(R.id.allDayCheckbox);
        Button saveButton = (Button)view.findViewById(R.id.saveButton);
        Button cancelButton = (Button)view.findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Check required fields to make sure that they are there. (also rules like ending time shouldn't be b4 starting time)
                Time startTimeFin;
                Time endTimeFin;
                if (allDayCheck.isChecked()) {
                    startTimeFin = new Time(0, 0, 0); //TODO: Deprecated
                    endTimeFin = new Time(23, 59, 59);

                } else {
                    String[] startTimeArr = startTime.getText().toString().split(":");
                    String[] endTimeArr = endTime.getText().toString().split(":");
                    startTimeFin = new Time(Integer.parseInt(startTimeArr[0]), Integer.parseInt(startTimeArr[1]), 0); //TODO: Deprecated
                    endTimeFin = new Time(Integer.parseInt(endTimeArr[0]), Integer.parseInt(endTimeArr[1]), 0);
                }
                Date date;
                //TODO: Sloppy may fix later
                String dateTemp = dateText.getText().toString();
                String[] dateArr = dateTemp.split("/");
                Calendar c = Calendar.getInstance();
                c.set(Integer.parseInt(dateArr[2]) + 2000, Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]));

                DataStorage ds = ((MainActivity)getActivity()).getDataStorage();
                ds.addEvent(new HardEvent(nameField.getText().toString(), descriptionField.getText().toString(), locationField.getText().toString(), c.getTime(), startTimeFin, endTimeFin));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom);
                fragmentTransaction.replace(R.id.fragmentContainer, ((MainActivity)getActivity()).getDashboardFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
