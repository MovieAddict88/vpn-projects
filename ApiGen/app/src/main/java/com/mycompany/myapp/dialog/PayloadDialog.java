package com.mycompany.myapp.dialog;

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
import com.mycompany.myapp.listener.SpinnerListener;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.RadioButton;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class PayloadDialog
{
	public static class Payload
	{
		private static EditText pName,cPayload, sni, dns, pstt, prtt ;

		private AlertDialog.Builder a;

		private Context c;

		private SharedPreferences sp;
		
		private static RadioButton usessl;
		private static RadioButton useWspayload;
		private static RadioButton useInject;
		private static RadioButton useDirect;
		private static RadioButton useSlow;
		private static RadioButton useHatok;
		private static CheckBox useWeb;
		
		
		

		private boolean isSlow;
		private boolean isDirect;
		private boolean isSSL;
		private boolean isInject;
		private boolean isPayloadSSL;
		private boolean isHatok;
		private boolean isWeb;
		
		
		private LinearLayout slowsettings;
		private LinearLayout websettings;
        TextView textView; 
		private static Button gen;
		private LinearLayout spy;
		private LinearLayout spi;
		
        

		
		
		
		
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
			dns = v.findViewById(R.id.dns);
			pstt = v.findViewById(R.id.pstt);		
			prtt = v.findViewById(R.id.prtt);		
			
			useWeb = v.findViewById(R.id.useWeb);
			useHatok = v.findViewById(R.id.useHatok);
			useSlow = v.findViewById(R.id.useSlow);
			usessl = v.findViewById(R.id.useSSL);
			useWspayload = v.findViewById(R.id.useWSpayload);
			useInject = v.findViewById(R.id.useInject);
			useDirect = v.findViewById(R.id.useDirect);
			useInject.setChecked(true);
			useWeb.setChecked(true);
			pstt.setEnabled(false);
			prtt.setEnabled(false);
			gen = v.findViewById(R.id.generate);
			websettings = v.findViewById(R.id.websettings);
			slowsettings = v.findViewById(R.id.slowsettings);
			
			spy = v.findViewById(R.id.spy);
			spi = v.findViewById(R.id.spi);
			slowsettings.setVisibility(View.GONE);
			//websettings.setVisibility(View.VISIBLE);
			spi.setVisibility(View.GONE);
			sni.setVisibility(View.GONE);
			
			useHatok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useHatok.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spi.setVisibility(View.VISIBLE);
                            sni.setVisibility(View.VISIBLE);
							gen.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
							useSlow.setChecked(false);
					     	usessl.setChecked(false);
					     	useInject.setChecked(false);
					      	useDirect.setChecked(false);
							useWspayload.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.VISIBLE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.VISIBLE);
						}
                    }
                });
			
			
			
			
			useSlow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useSlow.isChecked()){
                            cPayload.setVisibility(View.GONE);
							spy.setVisibility(View.GONE);
							spi.setVisibility(View.GONE);
                            sni.setVisibility(View.GONE);
							gen.setVisibility(View.GONE);  
							usessl.setChecked(false);
							useWeb.setChecked(false);
							useWspayload.setChecked(false);
							useDirect.setChecked(false);
							useInject.setChecked(false);
							useHatok.setChecked(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.VISIBLE);
							websettings.setVisibility(View.GONE);
						}

                    }
                });

			useInject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useInject.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
							spi.setVisibility(View.GONE);
                            sni.setVisibility(View.GONE);
							gen.setVisibility(View.VISIBLE);  
							usessl.setChecked(false);
							useWspayload.setChecked(false);
							useDirect.setChecked(false);
							useHatok.setChecked(false);
							useSlow.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.VISIBLE);
							//useWeb.setChecked(false);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.VISIBLE);
						}

                    }
                });

            usessl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(usessl.isChecked()){
                            cPayload.setVisibility(View.GONE);
							spy.setVisibility(View.GONE);
							gen.setVisibility(View.GONE);  
							sni.setVisibility(View.VISIBLE);
							spi.setVisibility(View.VISIBLE);
							useSlow.setChecked(false);
							useHatok.setChecked(false);
							useInject.setChecked(false);
							useWspayload.setChecked(false);
							useDirect.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.GONE);
                        }
                    }
                });
            useWspayload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useWspayload.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spi.setVisibility(View.VISIBLE);
                            sni.setVisibility(View.VISIBLE);
							gen.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
							useSlow.setChecked(false);
					     	usessl.setChecked(false);
							useHatok.setChecked(false);
					     	useInject.setChecked(false);
					      	useDirect.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.GONE);
						}
                    }
                });

			useDirect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useDirect.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
                            sni.setVisibility(View.GONE);
							gen.setVisibility(View.VISIBLE);
							spi.setVisibility(View.GONE);
							useSlow.setChecked(false);
							usessl.setChecked(false);
							useInject.setChecked(false);
							useHatok.setChecked(false);
							useWspayload.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.GONE);
						}

                    }
                });        
			useWeb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useWeb.isChecked()){                          
							//websettings.setVisibility(View.VISIBLE);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
						}else{
							//websettings.setVisibility(View.GONE);
							pstt.setEnabled(true);
							prtt.setEnabled(true);
						}

                    }
                });
				
			
			
       
			String [] list ={"SSH Injected Method","SSL Injected Method", "SSH Slowdns Method", "SSH Direct Method" };
			
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
			pstt = v.findViewById(R.id.pstt);		
			prtt = v.findViewById(R.id.prtt);		
			pinfo = v.findViewById(R.id.pInfo);
			
			useWeb = v.findViewById(R.id.useWeb);
			useSlow = v.findViewById(R.id.useSlow);
			useHatok = v.findViewById(R.id.useHatok);
			usessl = v.findViewById(R.id.useSSL);
			useWspayload = v.findViewById(R.id.useWSpayload);
			useInject = v.findViewById(R.id.useInject);
			useDirect = v.findViewById(R.id.useDirect);
			gen = v.findViewById(R.id.generate);
			dns = v.findViewById(R.id.dns);		
			slowsettings = v.findViewById(R.id.slowsettings);
			websettings = v.findViewById(R.id.websettings);
			spy = v.findViewById(R.id.spy);
			spi = v.findViewById(R.id.spi);
			
			
			useHatok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useHatok.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spi.setVisibility(View.VISIBLE);
                            sni.setVisibility(View.VISIBLE);
							gen.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
							useSlow.setChecked(false);
					     	usessl.setChecked(false);
					     	useInject.setChecked(false);
					      	useDirect.setChecked(false);
							useWspayload.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.VISIBLE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.VISIBLE);
						}
                    }
                });




			useSlow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useSlow.isChecked()){
                            cPayload.setVisibility(View.GONE);
							spy.setVisibility(View.GONE);
							spi.setVisibility(View.GONE);
                            sni.setVisibility(View.GONE);
							gen.setVisibility(View.GONE);  
							usessl.setChecked(false);
							useWeb.setChecked(false);
							useWspayload.setChecked(false);
							useDirect.setChecked(false);
							useInject.setChecked(false);
							useHatok.setChecked(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.VISIBLE);
							websettings.setVisibility(View.GONE);
						}

                    }
                });

			useInject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useInject.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
							spi.setVisibility(View.GONE);
                            sni.setVisibility(View.GONE);
							gen.setVisibility(View.VISIBLE);  
							usessl.setChecked(false);
							useWspayload.setChecked(false);
							useDirect.setChecked(false);
							useHatok.setChecked(false);
							useSlow.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.VISIBLE);
							//useWeb.setChecked(false);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.VISIBLE);
						}

                    }
                });

            usessl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(usessl.isChecked()){
                            cPayload.setVisibility(View.GONE);
							spy.setVisibility(View.GONE);
							gen.setVisibility(View.GONE);  
							sni.setVisibility(View.VISIBLE);
							spi.setVisibility(View.VISIBLE);
							useSlow.setChecked(false);
							useHatok.setChecked(false);
							useInject.setChecked(false);
							useWspayload.setChecked(false);
							useDirect.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.GONE);
                        }
                    }
                });
            useWspayload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useWspayload.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spi.setVisibility(View.VISIBLE);
                            sni.setVisibility(View.VISIBLE);
							gen.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
							useSlow.setChecked(false);
					     	usessl.setChecked(false);
							useHatok.setChecked(false);
					     	useInject.setChecked(false);
					      	useDirect.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.GONE);
						}
                    }
                });

			useDirect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useDirect.isChecked()){
                            cPayload.setVisibility(View.VISIBLE);
							spy.setVisibility(View.VISIBLE);
                            sni.setVisibility(View.GONE);
							gen.setVisibility(View.VISIBLE);
							spi.setVisibility(View.GONE);
							useSlow.setChecked(false);
							usessl.setChecked(false);
							useInject.setChecked(false);
							useHatok.setChecked(false);
							useWspayload.setChecked(false);
							useWeb.setChecked(true);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
							useWeb.setVisibility(View.GONE);
							slowsettings.setVisibility(View.GONE);
							websettings.setVisibility(View.GONE);
						}

                    }
                });        
			useWeb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton p1, boolean p2) {
                        if(useWeb.isChecked()){                          
							//websettings.setVisibility(View.VISIBLE);
							pstt.setEnabled(false);
							prtt.setEnabled(false);
						}else{
							//websettings.setVisibility(View.GONE);
							pstt.setEnabled(true);
							prtt.setEnabled(true);
						}

                    }
                });
		
			
			
			String [] list ={"SSH Injected Method","SSL Injected Method", "SSH Slowdns Method", "SSH Direct Method" };
			
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
				dns.setText(json.getString("Slowdns"));	
				pstt.setText(json.getString("WebProxy"));
				prtt.setText(json.getString("WebPort"));
				
				pinfo.setText(json.getString("pInfo"));
				
				useWeb.setChecked(json.getBoolean("isWeb"));
				useSlow.setChecked(json.getBoolean("isSlow"));
				usessl.setChecked(json.getBoolean("isSSL"));
				useWspayload.setChecked(json.getBoolean("isPayloadSSL"));
				useInject.setChecked(json.getBoolean("isInject"));
				useDirect.setChecked(json.getBoolean("isDirect"));
				useHatok.setChecked(json.getBoolean("isHatok"));
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
							jo.put("Slowdns", dns.getText().toString());
							jo.put("WebProxy", pstt.getText().toString());
							jo.put("WebPort", prtt.getText().toString());
							
							if (useHatok.isChecked()){
								isHatok = true;
								jo.put("isHatok", isHatok);
							}else{
								isHatok = false;
								jo.put("isHatok", isHatok);
							}
							
							if (usessl.isChecked()){
								isSSL = true;
								jo.put("isSSL", isSSL);
							}else{
								isSSL = false;
								jo.put("isSSL", isSSL);
							}
							if (useWspayload.isChecked()){
								isPayloadSSL = true;
								jo.put("isPayloadSSL", isPayloadSSL);
							}else{
								isPayloadSSL = false;
								jo.put("isPayloadSSL", isPayloadSSL);
							}
							if (useInject.isChecked()){
								isInject = true;
								jo.put("isInject", isInject);
							}else{
								isInject = false;
								jo.put("isInject", isInject);
							}

							if (useDirect.isChecked()){
								isDirect = true;
								jo.put("isDirect", isDirect);
							}else{
								isDirect = false;
								jo.put("isDirect", isDirect);
							}

							if (useSlow.isChecked()){
								isSlow = true;
								jo.put("isSlow", isSlow);
							}else{
								isSlow = false;
								jo.put("isSlow", isSlow);
							}
							if (useWeb.isChecked()){
								isWeb = false;
								jo.put("isWeb", isWeb);
							}else{
								isWeb = true;
								jo.put("isWeb", isWeb);
							}
							
							sp.edit().putString("Name", pName.getText().toString()).apply();
							sp.edit().putString("Payload", cPayload.getText().toString()).apply();
							sp.edit().putString("SNI", sni.getText().toString()).apply();
							sp.edit().putString("pInfo", pinfo.getText().toString());				
							sp.edit().putString("Slowdns", dns.getText().toString()).apply();
							sp.edit().putString("WebProxy", pstt.getText().toString()).apply();
							sp.edit().putString("WebPort", prtt.getText().toString()).apply();
							sp.edit().putBoolean("isSSL", isSSL).apply();
							sp.edit().putBoolean("isPayloadSSL", isPayloadSSL).apply();
							sp.edit().putBoolean("isInject", isInject).apply();
							sp.edit().putBoolean("isDirect", isDirect).apply();
							sp.edit().putBoolean("isSlow", isSlow).apply();
							sp.edit().putBoolean("isHatok", isHatok).apply();
							sp.edit().putBoolean("isWeb", isWeb).apply();
							
							
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
