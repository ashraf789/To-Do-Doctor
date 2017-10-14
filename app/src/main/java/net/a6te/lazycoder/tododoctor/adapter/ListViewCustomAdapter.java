package net.a6te.lazycoder.tododoctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.a6te.lazycoder.tododoctor.R;
import net.a6te.lazycoder.tododoctor.modelClass.AppointmentModel;

import java.util.ArrayList;

/**
 * Created by Programmer on 4/7/2017.
 */

public class ListViewCustomAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<AppointmentModel> appointments;

    public ListViewCustomAdapter(@NonNull Context context, @NonNull ArrayList<AppointmentModel> objects) {
        super(context, R.layout.custom_layout, objects);
        this.context = context;
        this.appointments = objects;
    }

    class ViewHolder{
        TextView docNameTv;
        TextView docAppointmentTv;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){//this will call only first time
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_layout,parent,false);
            holder = new ViewHolder();

            holder.docNameTv = (TextView) convertView.findViewById(R.id.custom_textDocName);
            holder.docAppointmentTv = (TextView) convertView.findViewById(R.id.custom_textAppointmentDate);

            convertView.setTag(holder);//convert view will store this view holder when next time this method will be called that time it won,t be null
        }else {
            holder = (ViewHolder) convertView.getTag();//retrieve holder
        }
        holder.docNameTv.setText(appointments.get(position).getDoctorName());
        holder.docAppointmentTv.setText(appointments.get(position).getDoctorAppointment());
        return convertView;
    }
}
