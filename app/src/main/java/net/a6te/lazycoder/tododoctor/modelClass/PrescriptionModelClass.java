package net.a6te.lazycoder.tododoctor.modelClass;

import java.io.Serializable;

/**
 * Created by Programmer on 4/10/2017.
 */

public class PrescriptionModelClass implements Serializable{

    private String prescriptionDoctorName;
    private String imageName;
    private String prescriptionDate;
    private String prescriptionTime;
    private int prescriptionId;
    private int appointmentId;

    public PrescriptionModelClass(String prescriptionDoctorName, String imageName, String prescriptionDate, String prescriptionTime, int prescriptionId, int appointmentId) {
        this.prescriptionDoctorName = prescriptionDoctorName;
        this.imageName = imageName;
        this.prescriptionDate = prescriptionDate;
        this.prescriptionTime = prescriptionTime;
        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
    }

    public PrescriptionModelClass(String prescriptionDoctorName, String imageName, String prescriptionDate, String prescriptionTime, int appointmentId) {
        this.prescriptionDoctorName = prescriptionDoctorName;
        this.imageName = imageName;
        this.prescriptionDate = prescriptionDate;
        this.prescriptionTime = prescriptionTime;
        this.appointmentId = appointmentId;
    }


    public String getPrescriptionDoctorName() {
        return prescriptionDoctorName;
    }

    public void setPrescriptionDoctorName(String prescriptionDoctorName) {
        this.prescriptionDoctorName = prescriptionDoctorName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getPrescriptionTime() {
        return prescriptionTime;
    }

    public void setPrescriptionTime(String prescriptionTime) {
        this.prescriptionTime = prescriptionTime;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
