package net.a6te.lazycoder.tododoctor.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.a6te.lazycoder.tododoctor.MainActivity;
import net.a6te.lazycoder.tododoctor.modelClass.AppointmentModel;
import net.a6te.lazycoder.tododoctor.modelClass.PrescriptionModelClass;

import java.util.ArrayList;

/**
 * Created by Mobile App Develop on 4/5/2017.
 */

public class AppointmentDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ToDo Doctor";
    private static final String TABLE_NAME_APPOINTMENT = "AppointmentDetails";
    private static final String TABLE_NAME_PRESCRIPTION = "PrescriptionDetails";


    // all variable of prescription table
    private static final String COL_PRESCRIPTION_ID = "prescriptionId";
    private static final String COL_PRESCRIPTION_APPOINTMENT_ID = "prescriptionAppointmentId";
    private static final String COL_PRESCRIPTION_DOC_NAME = "prescriptionDocName";
    private static final String COL_PRESCRIPTION_PICTURE_NAME = "prescriptionImageName";
    private static final String COL_PRESCRIPTION_DATE = "prescriptionDate";
    private static final String COL_PRESCRIPTION_TIME = "prescriptionTime";


    //all variable of appointment table
    private static final String COL_APP_DOC_ID = "appId";
    private static final String COL_APP_DOC_NAME = "docName";
    private static final String COL_APP_DOC_DETAILS = "docDetail";
    private static final String COL_APP_DOC_APPOINTMENT = "docAppointment";
    private static final String COL_APP_DOC_PHONE = "docPhone";
    private static final String COL_APP_DOC_EMAIL = "docEmail";

    private static final String appointmentTaleCreateQuery = "Create Table "+
            TABLE_NAME_APPOINTMENT+" ( "+
            COL_APP_DOC_ID+" Integer primary key, "+
            COL_APP_DOC_NAME+" Text, "+
            COL_APP_DOC_DETAILS+" Text, "+
            COL_APP_DOC_APPOINTMENT+" text, "+
            COL_APP_DOC_PHONE+" Text, "+
            COL_APP_DOC_EMAIL+" Text);";
    private static final String prescriptionTableCreateQuery = "Create TABLE "+TABLE_NAME_PRESCRIPTION+"(" +
            COL_PRESCRIPTION_ID+" INTEGER PRIMARY KEY, " +
            COL_PRESCRIPTION_APPOINTMENT_ID +" INTEGER, " +
            COL_PRESCRIPTION_DOC_NAME+" TEXT, " +
            COL_PRESCRIPTION_PICTURE_NAME+" TEXT, " +
            COL_PRESCRIPTION_DATE+" TEXT,  " +
            COL_PRESCRIPTION_TIME+" TEXT );";
    private SQLiteDatabase database;

    private Context context;

    public AppointmentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(appointmentTaleCreateQuery);
        sqLiteDatabase.execSQL(prescriptionTableCreateQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        openDatabase();
        database.execSQL("Drop table if exist "+TABLE_NAME_APPOINTMENT);
        database.execSQL("Drop table if exist "+TABLE_NAME_PRESCRIPTION);
        onCreate(sqLiteDatabase);
    }

    public void openDatabase(){
        database = this.getReadableDatabase();
    }
    public void closeDatabase(){
        database.close();
    }

    public boolean addAppointment(AppointmentModel appointment){
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_APP_DOC_NAME,appointment.getDoctorName());
        contentValues.put(COL_APP_DOC_DETAILS,appointment.getDoctorSpecialist());
        contentValues.put(COL_APP_DOC_APPOINTMENT,appointment.getDoctorAppointment());
        contentValues.put(COL_APP_DOC_EMAIL,appointment.getDoctorEmail());
        contentValues.put(COL_APP_DOC_PHONE,appointment.getDoctorPhone());
        long status = database.insert(TABLE_NAME_APPOINTMENT,null,contentValues);
        closeDatabase();
        return (status > 0 ? true : false);
    }


    public ArrayList<AppointmentModel> getAppointmetList(){
        openDatabase();
        ArrayList<AppointmentModel > appointments = new ArrayList<>();

        Cursor cursor = database.rawQuery("Select *From "+TABLE_NAME_APPOINTMENT,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){

            appointments.add(new AppointmentModel(cursor.getInt(cursor.getColumnIndex(COL_APP_DOC_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_DETAILS)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_APPOINTMENT)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_APP_DOC_PHONE))
            ));
        }
        closeDatabase();
        return appointments;
    }

    public AppointmentModel getAppointment(int appointmentId){
        openDatabase();
        AppointmentModel  appointments = new AppointmentModel();

        Cursor cursor = database.rawQuery("Select *From "+TABLE_NAME_APPOINTMENT+" WHERE "+
                COL_APP_DOC_ID+" = "+appointmentId,null);
        cursor.moveToFirst();

        appointments = new AppointmentModel(cursor.getInt(cursor.getColumnIndex(COL_APP_DOC_ID)),
                cursor.getString(cursor.getColumnIndex(COL_APP_DOC_NAME)),
                cursor.getString(cursor.getColumnIndex(COL_APP_DOC_DETAILS)),
                cursor.getString(cursor.getColumnIndex(COL_APP_DOC_APPOINTMENT)),
                cursor.getString(cursor.getColumnIndex(COL_APP_DOC_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COL_APP_DOC_PHONE))
        );
        closeDatabase();
        return appointments;
    }

    //delete appointment
    public boolean deleteAppointment(int id){
        openDatabase();
        long status = database.delete(TABLE_NAME_APPOINTMENT,COL_APP_DOC_ID+" = ?",new String []{String.valueOf(id)});
        closeDatabase();
        return status > 0 ? true : false;//if status > 0 then it will return true else it will return false
    }
    //update doctor appointment
    public boolean updateAppointment(AppointmentModel appointment){
        openDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_APP_DOC_NAME,appointment.getDoctorName());
        contentValues.put(COL_APP_DOC_DETAILS,appointment.getDoctorSpecialist());
        contentValues.put(COL_APP_DOC_APPOINTMENT,appointment.getDoctorAppointment());
        contentValues.put(COL_APP_DOC_PHONE,appointment.getDoctorPhone());
        contentValues.put(COL_APP_DOC_EMAIL,appointment.getDoctorEmail());

        long status = database.update(TABLE_NAME_APPOINTMENT,contentValues,COL_APP_DOC_ID +" = ?",new String []{String.valueOf(appointment.getAppointmentId())});
        closeDatabase();
        return status > 0 ? true:false;
    }




    //its all about prescription database --------------------------------------

    public boolean insertPrescription(PrescriptionModelClass prescription){
        openDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_PRESCRIPTION_APPOINTMENT_ID,prescription.getAppointmentId());
        contentValues.put(COL_PRESCRIPTION_DOC_NAME,prescription.getPrescriptionDoctorName());
        contentValues.put(COL_PRESCRIPTION_PICTURE_NAME,prescription.getImageName());
        contentValues.put(COL_PRESCRIPTION_DATE,prescription.getPrescriptionDate());
        contentValues.put(COL_PRESCRIPTION_TIME,prescription.getPrescriptionTime());

        long status = database.insert(TABLE_NAME_PRESCRIPTION,null,contentValues);
        closeDatabase();
        return status > 0 ? true:false;

    }


    public ArrayList<PrescriptionModelClass> getPrescriptionList(int doctorId){
        openDatabase();
        ArrayList<PrescriptionModelClass > prescription = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT *FROM "+TABLE_NAME_PRESCRIPTION+" WHERE "+
                COL_PRESCRIPTION_APPOINTMENT_ID+" = "+doctorId,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            prescription.add(new PrescriptionModelClass(
                    cursor.getString(cursor.getColumnIndex(COL_PRESCRIPTION_DOC_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_PRESCRIPTION_PICTURE_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_PRESCRIPTION_DATE)),
                    cursor.getString(cursor.getColumnIndex(COL_PRESCRIPTION_TIME)),
                    cursor.getInt(cursor.getColumnIndex(COL_PRESCRIPTION_ID)),
                    cursor.getInt(cursor.getColumnIndex(COL_PRESCRIPTION_APPOINTMENT_ID))
            ));
        }
        closeDatabase();
        return prescription;
    }


    //delete appointment
    public boolean deletePrescription(int id){
        openDatabase();
        long status = database.delete(TABLE_NAME_PRESCRIPTION,COL_PRESCRIPTION_ID+" = ?",new String []{String.valueOf(id)});
        closeDatabase();
        return status > 0 ? true : false;//if status > 0 then it will return true else it will return false
    }

    //update doctor appointment
    public boolean updatePrescription(PrescriptionModelClass prescription){
        openDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_PRESCRIPTION_APPOINTMENT_ID,prescription.getPrescriptionId());
        contentValues.put(COL_PRESCRIPTION_DOC_NAME,prescription.getPrescriptionDoctorName());
        contentValues.put(COL_PRESCRIPTION_PICTURE_NAME,prescription.getImageName());
        contentValues.put(COL_PRESCRIPTION_DATE,prescription.getPrescriptionDate());
        contentValues.put(COL_PRESCRIPTION_TIME,prescription.getPrescriptionTime());

        long status = database.update(TABLE_NAME_PRESCRIPTION,contentValues,COL_PRESCRIPTION_ID +" = ?",new String []{String.valueOf(prescription.getPrescriptionId())});
        closeDatabase();
        return status > 0 ? true:false;
    }

    // checking if table is exist or not its only use on development time
    public int checkTableExist(){
        openDatabase();

        Cursor cursor = database.rawQuery("select count(*) from "+TABLE_NAME_PRESCRIPTION,null);
        int status = cursor.getCount();
        closeDatabase();
        return status;
    }
    public int checkTableExistAppointment(){
        openDatabase();

        Cursor cursor = database.rawQuery("select count(*) from "+TABLE_NAME_APPOINTMENT,null);
        int status = cursor.getCount();
        closeDatabase();
        return status;
    }
}