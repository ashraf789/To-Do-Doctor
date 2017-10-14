package net.a6te.lazycoder.tododoctor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.a6te.lazycoder.tododoctor.database.AppointmentDatabaseHelper;
import net.a6te.lazycoder.tododoctor.modelClass.AppointmentModel;
import net.a6te.lazycoder.tododoctor.modelClass.PrescriptionModelClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddNewPrescription extends AppCompatActivity implements View.OnClickListener{

    private TextView tvDocName;
    private Button prescriptionDateBtn;
    private Button prescriptionTimeBtn;
    private ImageView selectImg;
    private Button prescriptionAddBtn;


    private AppointmentModel appointment;
    private PrescriptionModelClass prescriptionModelClass;
    private String appointmentId="0";
    private String prescriptionId = "0";
    private Calendar calendar;
    private int day,month,year;
    private int hours, minute;
    private String date,time;
    private String docName = "";
    private String pictureName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_prescription);

        initializeAll();
        prescriptionDateBtn.setOnClickListener(this);
        prescriptionTimeBtn.setOnClickListener(this);
        selectImg.setOnClickListener(this);
    }

    private void initializeAll() {
        tvDocName = (TextView) findViewById(R.id.prescription_docName);
        prescriptionDateBtn = (Button) findViewById(R.id.prescription_docAppointment);
        prescriptionTimeBtn = (Button) findViewById(R.id.prescription_time);
        selectImg = (ImageView) findViewById(R.id.select_img);
        prescriptionAddBtn = (Button) findViewById(R.id.addNewPrescription);

        appointment = (AppointmentModel) getIntent().getSerializableExtra("appointment");
        prescriptionModelClass = (PrescriptionModelClass) getIntent().getSerializableExtra("prescription");

        calendar = Calendar.getInstance(Locale.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        date = day+"."+month+"."+year;

        hours = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        time = hours+"."+minute;


        try{// if newAppointment.getDoctorName() == null then
            // it will give a exception for handle that exception i used here try catch block
            if (!appointment.getDoctorName().equals(null)){
                docName = appointment.getDoctorName();
                appointmentId = String.valueOf(appointment.getAppointmentId());
            }

            if (!prescriptionModelClass.getPrescriptionDoctorName().equals(null)){
                docName = prescriptionModelClass.getPrescriptionDoctorName();
                appointmentId = String.valueOf(prescriptionModelClass.getAppointmentId());
                prescriptionId = String.valueOf(prescriptionModelClass.getPrescriptionId());
                pictureName = String.valueOf(prescriptionModelClass.getImageName());
                selectImg.setImageBitmap(getBitmapImage(pictureName));

                time = prescriptionModelClass.getPrescriptionTime();
                date = prescriptionModelClass.getPrescriptionDate();
                prescriptionAddBtn.setText("Update");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try{// if newAppointment.getDoctorName() == null then
            // it will give a exception for handle that exception i used here try catch block
            if (!prescriptionModelClass.getPrescriptionDoctorName().equals(null)){
                docName = prescriptionModelClass.getPrescriptionDoctorName();
                appointmentId = String.valueOf(prescriptionModelClass.getAppointmentId());
                pictureName = String.valueOf(prescriptionModelClass.getImageName());
                selectImg.setImageBitmap(getBitmapImage(pictureName));

                //Todo: need to work in this area
//                time = prescriptionModelClass.getPrescriptionTime();
//                date = prescriptionModelClass.getPrescriptionDate();

                prescriptionAddBtn.setText("Update");
            }
        }catch (Exception e){
            e.printStackTrace();

        }

        updateDetails();//initialize default present date and time setting

    }

    private void updateDetails() {
        tvDocName.setText(docName);
        prescriptionDateBtn.setText(date);
        prescriptionTimeBtn.setText(time);
    }


    public void addNewPrescription(View view) {
        AppointmentDatabaseHelper database = new AppointmentDatabaseHelper(AddNewPrescription.this);
        AppointmentModel appointment = database.getAppointment(Integer.parseInt(appointmentId));

        if (pictureName !=null){
            if (prescriptionAddBtn.getText().equals("Update")){
                PrescriptionModelClass prescription = new PrescriptionModelClass(
                        docName,pictureName,
                        prescriptionDateBtn.getText().toString(),
                        prescriptionTimeBtn.getText().toString(),
                        Integer.parseInt(appointmentId),Integer.parseInt(prescriptionId));

                boolean status = database.updatePrescription(prescription);
                if (status){
                    Toast.makeText(this,"successfully Updated value",Toast.LENGTH_LONG).show();
                }else Toast.makeText(this,"failed Update value",Toast.LENGTH_LONG).show();
            }else{
                PrescriptionModelClass prescription = new PrescriptionModelClass(
                        docName,pictureName,
                        prescriptionDateBtn.getText().toString(),
                        prescriptionTimeBtn.getText().toString(),
                        Integer.parseInt(appointmentId));

                boolean status = database.insertPrescription(prescription);
                if (status){
                    Toast.makeText(this,"successfully inserted value",Toast.LENGTH_LONG).show();
                }else Toast.makeText(this,"failed inserted value",Toast.LENGTH_LONG).show();
            }
            startActivity(new Intent(AddNewPrescription.this,Prescription.class).putExtra("appointment",appointment));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prescription_docAppointment:
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPrescription.this,dateSelectListener,year,month,day);
                datePickerDialog.show();
                break;
            case R.id.prescription_time:
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewPrescription.this,timePickerListener,hours,minute,false);
                timePickerDialog.show();
                break;
            case R.id.select_img:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Directory Creation

                File pictureDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+"/ToDoDoctor/");
                pictureDirectory.mkdirs();
                if (pictureName == null){
                    pictureName = "TodoDoctor"+appointmentId+date+time+".jpg";
                }

                File imageFile = new File(pictureDirectory,pictureName);//image path

                // Directory creation complete
                Uri picture = Uri.fromFile(imageFile);
                // We have to create an URI resource because putExtra expects URI resource as the second argument.
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,picture);

                // Start the Activity Now
                startActivityForResult(cameraIntent,0);
                break;
        }
    }



    public void appointmentBtn(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSelectListener,year,month,day);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date = dayOfMonth+"."+month+"."+year;
            prescriptionDateBtn.setText(date);
        }
    };

    TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time = hourOfDay+"."+minute;
            prescriptionTimeBtn.setText(time);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK){
            try {
                selectImg.setImageBitmap(getBitmapImage(pictureName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap getBitmapImage(String pictureName) throws IOException {


        File pictureDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+"/ToDoDoctor/");
        File imageFile = new File(pictureDirectory,pictureName);//image path
        Uri picture = Uri.fromFile(imageFile);

        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(AddNewPrescription.this.getContentResolver(),picture);

        return imageBitmap;
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
                startActivity(new Intent(AddNewPrescription.this,MainActivity.class));
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
