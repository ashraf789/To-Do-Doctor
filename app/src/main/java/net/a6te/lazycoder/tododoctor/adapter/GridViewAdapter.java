package net.a6te.lazycoder.tododoctor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import net.a6te.lazycoder.tododoctor.AddNewPrescription;
import net.a6te.lazycoder.tododoctor.MainActivity;
import net.a6te.lazycoder.tododoctor.R;
import net.a6te.lazycoder.tododoctor.modelClass.PrescriptionModelClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Programmer on 4/11/2017.
 */

public class GridViewAdapter extends ArrayAdapter<PrescriptionModelClass> {

    private Context context;
    private ArrayList<PrescriptionModelClass> prescriptions;

    public GridViewAdapter(@NonNull Context context, ArrayList<PrescriptionModelClass> prescriptions) {
        super(context, R.layout.gridview_custom);
        this.context =context;
        this.prescriptions = prescriptions;
    }


    @Override
    public int getCount() {

        return prescriptions.size();
    }

    @Override
    public PrescriptionModelClass getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imgView;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_custom,parent,false);

            holder = new ViewHolder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.imageShow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.imgView.setImageBitmap(getBitmapImage(prescriptions.get(position).getImageName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public Bitmap getBitmapImage(String pictureName) throws IOException {


        File pictureDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()+"/ToDoDoctor/");
        File imageFile = new File(pictureDirectory,pictureName);//image path
        Uri picture = Uri.fromFile(imageFile);

        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),picture);

        return imageBitmap;
    }

}
