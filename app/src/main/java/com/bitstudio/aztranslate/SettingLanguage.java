package com.bitstudio.aztranslate;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SettingLanguage extends AppCompatActivity {
    public ListView listview;
    ArrayList<Languages> languageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_language);
        listview = (ListView)findViewById(R.id.list);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        Languages L1 = new Languages(R.drawable.afrikaans, "Afrikaans", "afr.traineddata", "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/afr.traineddata", "af");
        Languages L2 = new Languages(R.drawable.belorussian,"Belorussian", "bel.traineddata", "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/bel.traineddata", "be");
        Languages L3 = new Languages(R.drawable.chinese,"Chinese" , "chi_tra.traineddata" , "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/chi_tra.traineddata","zh");
        Languages L4 = new Languages(R.drawable.danish, "Danish" , "dan.traineddata", "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/dan.traineddata", "da");
        Languages L5 = new Languages(R.drawable.english, "English" , "eng.traineddata" , "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/eng.traineddata", "en");
        Languages L6 = new Languages(R.drawable.french,"French" , "fra.traineddata" , "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/fra.traineddata","fr");
        Languages L7 = new Languages(R.drawable.greek,"Greek",  "grc.traineddata" , "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/grc.traineddata","el");
        Languages L8 = new Languages(R.drawable.hindi,"Hindi", "hin.traineddata", "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/hin.traineddata","hi");
        Languages L9 = new Languages(R.drawable.italian,"Italian", "ita.traineddata" , "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/ita.traineddata","it");
        Languages L10 = new Languages(R.drawable.laotian,"Laotian"	, "lao.traineddata" , "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/lao.traineddata","lo");
        Languages L11 = new Languages(R.drawable.malay,"Malay" ,"mal.traineddata" ,"https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/mal.traineddata","ms");
        Languages L12 = new Languages(R.drawable.portuguese,"Portuguese" ,"por.traineddata" ,"https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/por.traineddata","pt");
        Languages L13 = new Languages(R.drawable.russian,"Russian" ,"rus.traineddata" ,"https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/rus.traineddata","ru");
        Languages L14 = new Languages(R.drawable.spanish,"Spanish" ,"spa.traineddata" , "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/spa.traineddata","es");
        Languages L15 = new Languages(R.drawable.vietnamese,"Vietnamese" ,"vie.traineddata" ,"https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/vie.traineddata","vi");
        Languages L16 = new Languages(R.drawable.japanese,"Japanese", "jpn.traineddata", "https://raw.githubusercontent.com/tesseract-ocr/tessdata/3.04.00/jpn.traineddata","ja");




        final DatabaseReference mData;
        mData = FirebaseDatabase.getInstance().getReference();
/*
        mData.child("Languages").push().setValue(L1);
        mData.child("Languages").push().setValue(L2);
        mData.child("Languages").push().setValue(L3);
        mData.child("Languages").push().setValue(L4);
        mData.child("Languages").push().setValue(L5);
        mData.child("Languages").push().setValue(L6);
        mData.child("Languages").push().setValue(L7);
        mData.child("Languages").push().setValue(L8);
        mData.child("Languages").push().setValue(L9);
        mData.child("Languages").push().setValue(L10);
        mData.child("Languages").push().setValue(L11);
        mData.child("Languages").push().setValue(L12);
        mData.child("Languages").push().setValue(L13);
        mData.child("Languages").push().setValue(L14);
        mData.child("Languages").push().setValue(L15);
        mData.child("Languages").push().setValue(L16);

*/


        languageList = new ArrayList<Languages>();
        CustomAdapter customAdaper = new CustomAdapter(this,R.layout.row_listview,languageList);
        listview.setAdapter(customAdaper);




        mData.child("Languages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Languages languages = dataSnapshot.getValue(Languages.class);
                languages.setKey(dataSnapshot.getKey());
                languageList.add(languages);


                Collections.sort(languageList, new Comparator<Languages>() {
                    @Override
                    public int compare(Languages l1, Languages l2) {
                        return l1.getName().compareToIgnoreCase(l2.getName());
                    }
                });

                for(int i=0; i<languageList.size(); i++){
                    listview.setItemChecked(i,false);
                }

                customAdaper.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Languages lanNew = dataSnapshot.getValue(Languages.class);

                for (Languages lang: languageList) {
                    if(lang.getKey().equals(dataSnapshot.getKey())){
                        int ind = languageList.indexOf(lang);
                        languageList.set(ind, lanNew);
                        lanNew.setKey(dataSnapshot.getKey());
                    }


                }

                Collections.sort(languageList, new Comparator<Languages>() {
                    @Override
                    public int compare(Languages l1, Languages l2) {
                        return l1.getName().compareToIgnoreCase(l2.getName());
                    }
                });

                for(int i=0; i<languageList.size(); i++){
                    listview.setItemChecked(i,false);
                }

                customAdaper.notifyDataSetChanged();

                //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //builder.setMessage(languageList.get(0).getKey()).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
