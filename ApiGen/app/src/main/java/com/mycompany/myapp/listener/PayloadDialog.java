package com.mycompany.myapp.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kervzcodes.payload.generator.ssh.PayloadGenerator;
import com.mycompany.mygen.R;
import org.json.JSONException;
import org.json.JSONObject;

public class PayloadDialog
{
	public static class Payload
	{
		private static EditText pName,cPayload, sni;

		private AlertDialog.Builder a;

		private Context c;

		private SharedPreferences sp;
		
		private static CheckBox usessl;
        TextView textView; 
		private static Button gen;
		
        private boolean isSSL;

        AutoCompleteTextView pinfo;

      //  private ArrayList<String> list;

        
       		public Payload(Context c)
		{
        
			a=new AlertDialog.Builder(c);
			sp=PreferenceManager.getDefaultSharedPreferences(c);
			this.c=c;
		}
		public void add()
		{
			View v=LayoutInflater.from(c).inflate(R.layout.dialog_add_payload, null);
			pName = v.findViewById(R.id.pName);
			cPayload = v.findViewById(R.id.cPayload);
			sni = v.findViewById(R.id.sni);
			pinfo = v.findViewById(R.id.pInfo);
		    usessl = v.findViewById(R.id.useSSL);
			gen = v.findViewById(R.id.generate);
       
        String [] list ={"SSH Injected Method","SSL Injected Method"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(c,android.R.layout.select_dialog_item,list);
            pinfo.setAdapter(adapter);        
        
			gen.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View p1)
					{
						PayloadGenerator pg=new PayloadGenerator(c);
						pg.setCancelListener("Close",null);
						pg.setGenerateListener("Generate",new PayloadGenerator.OnGenerateListener()
							{
								@Override
								public void onGenerate(String payloadGenerated)
								{
									cPayload.setText(payloadGenerated);
								}
							});
						pg.show();
					}
				});
			

			a.setView(v);
		}
		public void edit(JSONObject json)
		{
			View v=LayoutInflater.from(c).inflate(R.layout.dialog_add_payload, null);
			pName = v.findViewById(R.id.pName);
			cPayload = v.findViewById(R.id.cPayload);
			sni = v.findViewById(R.id.sni);
			pinfo = v.findViewById(R.id.pInfo);
		    usessl = v.findViewById(R.id.useSSL);
			gen = v.findViewById(R.id.generate);
                 
        String [] list ={"SSH Injected Method","SSL Injected Method"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(c,android.R.layout.select_dialog_item,list);
        pinfo.setAdapter(adapter); 
			gen.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View p1)
					{
						PayloadGenerator pg=new PayloadGenerator(c);
						pg.setCancelListener("Close",null);
						pg.setGenerateListener("Generate",new PayloadGenerator.OnGenerateListener()
							{
								@Override
								public void onGenerate(String payloadGenerated)
								{
									cPayload.setText(payloadGenerated);
								}
							});
						pg.show();
					}
				});

			try {
				pName.setText(json.getString("Name"));
				cPayload.setText(json.getString("Payload"));
				sni.setText(json.getString("SNI"));
				pinfo.setText(json.getString("pInfo"));
				usessl.setChecked(json.getBoolean("isSSL"));

			} catch (JSONException e) {}
			a.setView(v);
		}
		public void onPayloadAdd(final SpinnerListener oca)
		{
			a.setNegativeButton("Close",null);
			a.setPositiveButton("Save",new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						JSONObject jo=new JSONObject();
						try
						{
							jo.put("Name", pName.getText().toString());
							jo.put("Payload", cPayload.getText().toString());
							jo.put("SNI", sni.getText().toString());
                      
                			jo.put("pInfo", pinfo.getText().toString());
							
							if (usessl.isChecked()){
								isSSL = true;
								jo.put("isSSL", isSSL);
							}else{
								isSSL = false;
								jo.put("isSSL", isSSL);
							}
							
							sp.edit().putString("Name", pName.getText().toString()).apply();
							sp.edit().putString("Payload", cPayload.getText().toString()).apply();
							sp.edit().putString("SNI", sni.getText().toString()).apply();
							sp.edit().putString("pInfo", pinfo.getText().toString());
                       
							sp.edit().putBoolean("isSSL", isSSL).apply();
							
								oca.onAdd(jo);

						}
						catch (JSONException e)
						{
							Toast.makeText(c,e.getMessage(),1).show();
						}
					}
				});
		}
		
		
		public void init()
		{
			a.create().show();
		}
	}
}
