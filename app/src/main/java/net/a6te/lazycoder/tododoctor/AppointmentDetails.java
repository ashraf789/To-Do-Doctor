package net.a6te.lazycoder.tododoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.a6te.lazycoder.tododoctor.modelClass.AppointmentModel;

public class AppointmentDetails extends AppCompatActivity {

    private AppointmentModel appointment;

    private TextView docNameTv;
    private TextView docDetailsTv;
    private TextView docAppointmentTv;
    private TextView docPhoneTv;
    private TextView docEmailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        initializeAll();
        docNameTv.setText(appointment.getDoctorName());
        docDetailsTv.setText(appointment.getDoctorSpecialist());
        docAppointmentTv.setText(appointment.getDoctorAppointment());
        docPhoneTv.setText(appointment.getDoctorPhone());
        docEmailTv.setText(appointment.getDoctorEmail());
    }

    private void initializeAll() {
        appointment = (AppointmentModel) getIntent().getSerializableExtra("appointment");
        docNameTv = (TextView) findViewById(R.id.details_docNameTv);
        docDetailsTv = (TextView) findViewById(R.id.details_docDetails);
        docAppointmentTv = (TextView) findViewById(R.id.details_docAppointment);
        docPhoneTv = (TextView) findViewById(R.id.details_docPhone);
        docEmailTv = (TextView) findViewById(R.id.details_docEmail);
    }

    public void update(View view) {
        startActivity(new Intent(AppointmentDetails.this,AddDoctor.class).putExtra("updateAppointment",appointment));
        finish();//when user press back button then this activity will be killed
    }

    public void addPrescriptionBtn(View view) {
        startActivity(new Intent(AppointmentDetails.this,Prescription.class).putExtra("appointment",appointment));
    }

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
                startActivity(new Intent(AppointmentDetails.this,MainActivity.class));
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
