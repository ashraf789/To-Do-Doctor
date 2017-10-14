package net.a6te.lazycoder.tododoctor.modelClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.io.Serializable;

/**
 * Created by Programmer on 4/7/2017.
 */

public class AppointmentModel implements Serializable{
    private String doctorName;
    private String doctorSpecialist;
    private String doctorAppointment;
    private String doctorEmail;
    private String doctorPhone;

    private int appointmentId;
    public AppointmentModel(String doctorName, String doctorSpecialist, String doctorAppointment, String doctorEmail, String doctorPhone) {
        this.doctorName = doctorName;
        this.doctorSpecialist = doctorSpecialist;
        this.doctorAppointment = doctorAppointment;
        this.doctorEmail = doctorEmail;
        this.doctorPhone = doctorPhone;
    }

    public AppointmentModel(int appointmentId,String doctorName, String doctorSpecialist, String doctorAppointment, String doctorEmail, String doctorPhone) {
        this.doctorName = doctorName;
        this.doctorSpecialist = doctorSpecialist;
        this.doctorAppointment = doctorAppointment;
        this.doctorEmail = doctorEmail;
        this.doctorPhone = doctorPhone;
        this.appointmentId = appointmentId;
    }

    public AppointmentModel(String doctorName, String doctorAppointment) {
        this.doctorName = doctorName;
        this.doctorAppointment = doctorAppointment;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public AppointmentModel() {
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialist() {
        return doctorSpecialist;
    }

    public void setDoctorSpecialist(String doctorSpecialist) {
        this.doctorSpecialist = doctorSpecialist;
    }

    public String getDoctorAppointment() {
        return doctorAppointment;
    }

    public void setDoctorAppointment(String doctorAppointment) {
        this.doctorAppointment = doctorAppointment;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }
}
