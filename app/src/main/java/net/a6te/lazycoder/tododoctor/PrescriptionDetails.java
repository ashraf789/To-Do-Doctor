package net.a6te.lazycoder.tododoctor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.a6te.lazycoder.tododoctor.modelClass.PrescriptionModelClass;

import java.io.File;
import java.io.IOException;

public class PrescriptionDetails extends AppCompatActivity {

    private TextView prescriptionDoctorName;
    private TextView prescriptionDate;
    private TextView prescriptionTime;
    private ImageView prescriptionImg;

    private PrescriptionModelClass prescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_details);

        try {
            initializeAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeAll() throws IOException {
        prescriptionDoctorName = (TextView) findViewById(R.id.prescription_docName);
        prescriptionDate = (TextView) findViewById(R.id.prescription_showDate);
        prescriptionTime = (TextView) findViewById(R.id.prescription_showTime);
        prescriptionImg = (ImageView) findViewById(R.id.prescription_showImage);

        prescription = (PrescriptionModelClass) getIntent().getSerializableExtra("prescription");

        prescriptionDoctorName.setText(prescription.getPrescriptionDoctorName());
        prescriptionDate.setText(prescription.getPrescriptionDate());
        prescriptionTime.setText(prescription.getPrescriptionTime());
        prescriptionImg.setImageBitmap(getBitmapImage(prescription.getImageName()));

    }

    public void updatePrescription(View view) {
        startActivity(new Intent(PrescriptionDetails.this,AddNewPrescription.class).putExtra("prescription",prescription));
    }

    public Bitmap getBitmapImage(String pictureName) throws IOException {


        File pictureDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+"/ToDoDoctor/");
        File imageFile = new File(pictureDirectory,pictureName);//image path
        Uri picture = Uri.fromFile(imageFile);

        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(PrescriptionDetails.this.getContentResolver(),picture);

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
                startActivity(new Intent(PrescriptionDetails.this,MainActivity.class));
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
