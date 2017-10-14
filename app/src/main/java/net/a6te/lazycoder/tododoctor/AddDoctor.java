package net.a6te.lazycoder.tododoctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import net.a6te.lazycoder.tododoctor.database.AppointmentDatabaseHelper;
import net.a6te.lazycoder.tododoctor.modelClass.AppointmentModel;

import java.util.Locale;

public class AddDoctor extends AppCompatActivity {

    private EditText docNameEt;
    private EditText docDetailsEt;
    private EditText docPhoneEt;
    private EditText docEmailEt;
    private Button appointmentBtn;
    private Button addNewDoctorBtn;

    private AppointmentDatabaseHelper database;
    private AppointmentModel newAppointment;
    private Calendar calendar;
    private int day,month,year;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        initializeAll();
        newAppointment = (AppointmentModel) getIntent().getSerializableExtra("updateAppointment");

        try{// if newAppointment.getDoctorName() == null then
            // it will give a exception for handle that exception i used here try catch block
            if (!newAppointment.getDoctorName().equals(null)){
                updateDetails();
            }
        }catch (Exception e){

        }
    }

    public void updateDetails(){
        docNameEt.setText(newAppointment.getDoctorName());
        docDetailsEt.setText(newAppointment.getDoctorSpecialist());
        appointmentBtn.setText(newAppointment.getDoctorAppointment());
        docEmailEt.setText(newAppointment.getDoctorEmail());
        docPhoneEt.setText(newAppointment.getDoctorPhone());
        addNewDoctorBtn.setText("Update");
    }
    private void initializeAll() {
        docNameEt = (EditText) findViewById(R.id.doctorNameEt);
        docDetailsEt = (EditText) findViewById(R.id.doctorDetailsEt);
        appointmentBtn = (Button) findViewById(R.id.appointmentBtn);
        docPhoneEt = (EditText) findViewById(R.id.doctorPhoneEt);
        docEmailEt = (EditText) findViewById(R.id.doctorEmailEt);
        addNewDoctorBtn = (Button) findViewById(R.id.addNewDoctorBtn);

        database = new AppointmentDatabaseHelper(this);
        newAppointment = new AppointmentModel();
        calendar = Calendar.getInstance(Locale.getDefault());

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        date = day+"."+month+"."+year;
        appointmentBtn.setText(date);
    }

    public void addNewAppointment(View view) {
        String name = docNameEt.getText().toString();
        String details = docDetailsEt.getText().toString();
        String appointment = date;
        String phone = docPhoneEt.getText().toString();
        String email = docEmailEt.getText().toString();

        Log.d("data", "addNewAppointment: "+name+"\n"+details+"\n"+appointment+"\n"+email+"\n"+phone);
        if (validationCheck(name,details,phone,email)) {

            if (addNewDoctorBtn.getText().equals("Update")){
                newAppointment = new AppointmentModel(newAppointment.getAppointmentId(),name,details,appointment,email,phone);
                boolean status = database.updateAppointment(newAppointment);
                if (status == true){
                    startActivity(new Intent(AddDoctor.this,Doctors.class));
                }else {
                    showToast("Field to Update appointment details");
                }
            }else {
                newAppointment = new AppointmentModel(name,details,appointment,email,phone);
                boolean status = database.addAppointment(newAppointment);
                if (status == true){
                    startActivity(new Intent(AddDoctor.this,Doctors.class));
                }else {
                    showToast("Field to add new appointment");
                }
            }


        }else return;

    }

    //validation checker
    public boolean validationCheck(String name, String details, String phone, String email){
        if (name.isEmpty() || details.isEmpty() || phone.isEmpty() || email.isEmpty()){

            if (name.isEmpty()) docNameEt.setError("Doctor name can,t be empty");
            if (details.isEmpty()) docDetailsEt.setError("Doctor email can,t be empty");
            if (phone.isEmpty()) docPhoneEt.setError("Doctor phone can,t be empty");
            if (email.isEmpty()) docEmailEt.setError("Doctor email can,t be empty");
            return false;
        }else {
            return true;
        }

    }

    public void showToast(String str){
        Toast.makeText(AddDoctor.this,str,Toast.LENGTH_LONG).show();
    }

    public void appointmentBtn(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSelectListener,year,month,day);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date = dayOfMonth+"."+month+"."+year;
            appointmentBtn.setText(date);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                startActivity(new Intent(AddDoctor.this,MainActivity.class));
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
