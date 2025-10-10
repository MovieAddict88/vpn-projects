package com.mycompany.myapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.mycompany.mygen.R;

public class ReleaseNotes1 {

    private SharedPreferences sp;

    private Context c;

    public ReleaseNotes1(final Context c, String test) {

        sp = PreferenceManager.getDefaultSharedPreferences(c);
        AlertDialog.Builder a=new AlertDialog.Builder(c);
        View v=LayoutInflater.from(c).inflate(R.layout.abc_notes1, null);
        final EditText e1= (EditText)v.findViewById(R.id.notes1);

        if (sp.getString("ReleaseNotes1", "").isEmpty()){
            sp.edit().putString("ReleaseNotes1", test).apply();
        }

        e1.setText(sp.getString("ReleaseNotes1", ""));

        a.setView(v);
        a.setNegativeButton("Close", null);
        a.setPositiveButton("Save", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface p1, int p2) {

                    sp.edit().putString("ReleaseNotes1", e1.getText().toString()).apply(); 
					sp.edit().putString("isChanged", "yes").apply();                  	                        
                }
            });
        a.create().show();
    }

    public ReleaseNotes1(Context c,ReleaseNotes1 notesDialog1, EditText editText1) {
        sp = PreferenceManager.getDefaultSharedPreferences(c);
        this.c = c;
    }
}
