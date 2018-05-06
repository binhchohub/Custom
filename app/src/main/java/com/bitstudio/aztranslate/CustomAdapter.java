package com.bitstudio.aztranslate;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Welcome on 8/27/2016.
 */
public class CustomAdapter extends ArrayAdapter<Languages> {

    private Context context;
    private int resource;
    private List<Languages> arrContact;

    public CustomAdapter(Context context, int resource, ArrayList<Languages> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }

    private boolean copyFile(File src,File dst)throws IOException {
        if(src.getAbsolutePath().toString().equals(dst.getAbsolutePath().toString())){

            return true;

        }else{
            InputStream is=new FileInputStream(src);
            OutputStream os=new FileOutputStream(dst);
            byte[] buff=new byte[1024];
            int len;
            while((len=is.read(buff))>0){
                os.write(buff,0,len);
            }
            is.close();
            os.close();
        }
        return true;
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
            viewHolder.btnD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Languages lang = arrContact.get(position);
                    viewHolder.btnD.setVisibility(View.INVISIBLE);
                    viewHolder.tvDownload.setText("Downloading...");

                    File direct = new File(Environment.getExternalStorageDirectory()
                            + "tessdata");

                    if (!direct.exists()) {
                        direct.mkdirs();
                    }


                    DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(lang.getFileurl());
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir("tessdata", lang.getFilename());
                    downloadManager.enqueue(request);



                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Languages language = arrContact.get(position);
        viewHolder.tvFlag.setImageResource(language.getScr());
        viewHolder.tvName.setText(language.getName());

        return convertView;
    }

    public class ViewHolder {
        ImageView tvFlag;
        TextView tvName, tvDownload;
        ImageButton btnD;
    }
}

