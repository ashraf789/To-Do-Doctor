package net.a6te.lazycoder.tododoctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.a6te.lazycoder.tododoctor.adapter.GridViewAdapter;
import net.a6te.lazycoder.tododoctor.database.AppointmentDatabaseHelper;
import net.a6te.lazycoder.tododoctor.modelClass.AppointmentModel;
import net.a6te.lazycoder.tododoctor.modelClass.PrescriptionModelClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Prescription extends AppCompatActivity {

    private AppointmentModel appointment;
    private AppointmentDatabaseHelper database;
    private ArrayList<PrescriptionModelClass> prescriptions;
    private GridView gridview;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        int res = R.drawable.select;
        appointment = (AppointmentModel) getIntent().getSerializableExtra("appointment");
        gridview = (GridView) findViewById(R.id.gridview);
        database = new AppointmentDatabaseHelper(this);
        prescriptions = new ArrayList<>();

        loadGrid();
        gridview.setOnItemClickListener(clickListener);
        gridview.setOnItemLongClickListener(longClickListener);

    }


    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(new Intent(Prescription.this,PrescriptionDetails.class).putExtra("prescription",prescriptions.get(position)));
        }
    };

    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Prescription.this);
            builder.setMessage("Are you sure you want to delete this?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    database.deletePrescription(prescriptions.get(position).getPrescriptionId());
                    loadGrid();
                }
            });
            builder.setNegativeButton("No",null);
            builder.show();
            return true;
        }
    };


    public void loadGrid(){
        prescriptions = database.getPrescriptionList(appointment.getAppointmentId());

        gridViewAdapter = new GridViewAdapter(this,prescriptions);
        gridview.setAdapter(gridViewAdapter);

    }
    public void addPrescriptionOnClickBtn(View view) {
        Intent intent = new Intent(Prescription.this,AddNewPrescription.class);
        intent.putExtra("appointment",appointment);
        startActivity(intent);
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
                startActivity(new Intent(Prescription.this,MainActivity.class));
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
