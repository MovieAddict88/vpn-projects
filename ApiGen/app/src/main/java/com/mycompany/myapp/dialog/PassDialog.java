package com.mycompany.myapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.mycompany.mygen.R;

public class PassDialog {

    private SharedPreferences sp;

    private Context c;

    public PassDialog(final Context c, String test) {

        sp = PreferenceManager.getDefaultSharedPreferences(c);
        AlertDialog.Builder a=new AlertDialog.Builder(c);
        View v=LayoutInflater.from(c).inflate(R.layout.password, null);
        final EditText e1=v.findViewById(R.id.password);

        if (sp.getString("pass", "").isEmpty()){
            sp.edit().putString("pass", test).apply();
        }

        e1.setText(sp.getString("pass", ""));

        a.setView(v);
        a.setNegativeButton("Close", null);
        a.setPositiveButton("Save", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface p1, int p2) {

                    if (e1.getText().toString().isEmpty()){
                        Toast.makeText(c, "Please set a password!", Toast.LENGTH_LONG).show();

                    }else{

                        String x = e1.getText().toString();

                        sp.edit().putString("pass", x).apply();

                        Toast.makeText(c, "Password saved!", Toast.LENGTH_LONG).show();

                    }

                }
            });
        a.create().show();
    }

    public PassDialog(Context c) {
        sp = PreferenceManager.getDefaultSharedPreferences(c);
        this.c = c;
    }
}
