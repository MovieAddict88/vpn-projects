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
import com.mycompany.myapp.AESCrypt;
import com.mycompany.mygen.R;
import com.mycompany.myapp.util.FileUtil;
import org.json.JSONObject;

public class SaveDialog 
{

	private SharedPreferences sp;
	
	private String outputString;
	
	public SaveDialog(final Context c,final JSONObject ja)
	{

		sp=PreferenceManager.getDefaultSharedPreferences(c);
		AlertDialog.Builder a=new AlertDialog.Builder(c);
		View v=LayoutInflater.from(c).inflate(R.layout.dialog_save,null);
	//	final EditText e1=v.findViewById(R.id.version);
		//final EditText e2=v.findViewById(R.id.password);
		    final EditText e4=v.findViewById(R.id.fName);
		
		final EditText[] e={};
		for(int i=0;i<e.length;i++)
		{
        //e2.setText("");
     //   e1.setText("1.0.0");
			e[i].setText(sp.getString("SAVE_"+i,""));
		}
		a.setView(v);
		a.setNegativeButton("Close",null);
		a.setPositiveButton("Save",new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
				//	String x=e1.getText().toString();
					
				//	String y=e2.getText().toString();
					
					    String b=e4.getText().toString();
            String string = sp.getString("pass", ""); 
                     
					String[] s={b};
					for(int i=0;i<e.length;i++)
					{
						sp.edit().putString("SAVE_"+i,s[i]).apply();
					}
            if (e4.getText().toString().isEmpty()) { 
                Toast.makeText(c, "Please set a file name!", Toast.LENGTH_LONG).show(); //Toast.makeText(this, "Please set a file name!", 1).show(); 
                                  return; 
                             } 
                        String editable = e4.getText().toString(); 
                        sp.edit().putString("name", editable).apply(); 
                        String string1 = sp.getString("pass", ""); 
					try
					{
              
					//	ja.put("Version", x);
						//ja.put("ReleaseNotes", z);
              if (b.isEmpty())
              {
                  Toast.makeText(c,"Please Complete the fields!",1).show();
              }else
              {
                  FileUtil.save(c,b,ja.toString());
              }
						try {
                outputString = AESCrypt.encrypt(string1, ja.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						FileUtil.save(c,b, outputString);
						OutputDialog a=new OutputDialog(c);

						a.show(outputString);

						a.init();
						
					}
					catch (Exception e)
					{}
				}
			});
		a.create().show();
	}
	
	
}
