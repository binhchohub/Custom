package com.bitstudio.aztranslate;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Welcome on 8/27/2016.
 */
public class CustomAdapter extends ArrayAdapter<Languages> {


    private  boolean flag = true;
    private Context context;
    private int resource;
    private ArrayList<Languages> arrContact;
    private ArrayList<Integer> arrBool;

    public CustomAdapter(Context context, int resource, ArrayList<Languages> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
        arrBool = new ArrayList<Integer>();
        for(int i=0; i<1000; i++){
            arrBool.add(1);
        }


    }


    public void resetList(){
        for(int i=0; i<SettingLanguage.languagesListAll.size(); i++){

            Languages lang = SettingLanguage.languagesListAll.get(i);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tessdata/" + lang.getFilename());
            if(file.exists()){
                //Log.d("HERE", "resetList: "+lang.getFilename());
                deleteFormList(lang.getFilename());
            }
        }
        notifyDataSetChanged();
    }
    public void deleteFormList(String filename){
        for(int i=0; i<arrContact.size(); i++){
            if(arrContact.get(i).getFilename().equals(filename)){
                arrBool.remove(i);
                arrContact.remove(i);
            }
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvFlag = (ImageView) convertView.findViewById(R.id.tvFlag);
            viewHolder.btnD = (ImageButton) convertView.findViewById(R.id.btnD);
            viewHolder.tvDownload = (TextView) convertView.findViewById(R.id.tvDownload);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }



        Languages language = arrContact.get(position);
        viewHolder.tvFlag.setImageResource(context.getResources().getIdentifier(language.getName().toLowerCase(), "drawable", context.getPackageName()));
        viewHolder.tvName.setText(language.getName());
        viewHolder.btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrBool.set(position,0);
                viewHolder.btnD.setVisibility(View.INVISIBLE);
                viewHolder.tvDownload.setText("Downloading...");


                Languages lang = arrContact.get(position);
                File direct = new File(Environment.getExternalStorageDirectory()
                        + "tessdata");

                if (!direct.exists()) {
                    direct.mkdirs();
                }

                //Xoa File neu bi trung
                File del = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tessdata/" + lang.getFilename());
                if(del.exists()){
                    del.delete();
                }


                DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(lang.getFileurl());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir("tessdata", lang.getFilename());
                downloadManager.enqueue(request);

                BroadcastReceiver onComplete=new BroadcastReceiver() {
                    public void onReceive(Context ctxt, Intent intent) {
                        resetList();
                    }
                };
                getContext().registerReceiver(onComplete,
                        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));




                    /*
                    Languages lang = arrContact.get(position);
                    Log.d("HERE", "position "+position);
                    Log.d("HERE", "name "+lang.getName());
                    Log.d("HERE", "filename "+lang.getFilename());


                    viewHolder.btnD.setVisibility(View.INVISIBLE);
                    viewHolder.tvDownload.setText("Downloading...");

                    File direct = new File(Environment.getExternalStorageDirectory()
                            + "tessdata");

                    if (!direct.exists()) {
                        direct.mkdirs();
                    }

                    //Xoa File neu bi trung
                    File del = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/tessdata/" + lang.getFilename());
                    if(del.exists()){
                        del.delete();
                    }


                    DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(lang.getFileurl());
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir("tessdata", lang.getFilename());
                    downloadManager.enqueue(request);
                    */

            }
        });

        if(arrBool.get(position) == 0){
            viewHolder.btnD.setVisibility(View.INVISIBLE);
            viewHolder.tvDownload.setText("Downloading...");
        }else{
            viewHolder.btnD.setVisibility(View.VISIBLE);
            viewHolder.tvDownload.setText("");
        }
        return convertView;
    }

    public class ViewHolder {
        ImageView tvFlag;
        TextView tvName, tvDownload;
        ImageButton btnD;
    }


}


