package com.mycompany.myapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.mycompany.myapp.AESCrypt;
import com.mycompany.mygen.R;
import com.mycompany.myapp.adapter.PayloadSpinnerAdapter;
import com.mycompany.myapp.adapter.ServerSpinnerAdapter;
import com.mycompany.myapp.dialog.PassDialog;
import com.mycompany.myapp.dialog.PayloadDialog;
import com.mycompany.myapp.dialog.ReleaseNotes;
import com.mycompany.myapp.dialog.ReleaseNotes1;
import com.mycompany.myapp.dialog.SaveDialog;
import com.mycompany.myapp.dialog.ServerDialog;
import com.mycompany.myapp.dialog.Version;
import com.mycompany.myapp.listener.SpinnerListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

	final static int REQUEST_CODE = 333;

	private SharedPreferences sp;

	private Spinner a,b;

    private AlertDialog about;

    private FilePickerDialog dialog;

    private String v;

    private String n;
	
	private String m;
	
	CoordinatorLayout coordinatorLayout;

	private Context context; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		//SimpleProtect.init(this);
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
		
        setContentView(R.layout.activity_main);
		      Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		      setSupportActionBar(toolbar);
		  
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		a = (Spinner) findViewById(R.id.serverList);
		b = (Spinner) findViewById(R.id.payloadList);

		if (adapter() != null) {
			a.setAdapter(adapter());
		}


		if (adapterP() != null) {
			b.setAdapter(adapterP());
		} 
		setupJson();

		//SimpleProtect.MyProtect();
        try {
			JSONObject jsonObj = new JSONObject(ja().toString());
			String finalJson = jsonObj.toString(1);

			((TextView)findViewById(R.id.json)).setText(finalJson);
		} catch (JSONException e) {}
        //Runtime External storage permission for saving download files
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }
			  
    }

//	public void saveJson(View v) {
//		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//				== PackageManager.PERMISSION_DENIED) {
//				Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
//				String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//				requestPermissions(permissions, 1);
//			}
//		}
//		new SaveDialog((Context)this, this.ja());
//
//	}
    

	public void editServer(final int position) {

		ServerDialog.Server a=new ServerDialog.Server(MainActivity.this);
		try {
			final JSONArray ja=new JSONArray(sp.getString("ServerList", "[]"));
			a.edit(ja.getJSONObject(position));
			a.onServerAdd(new SpinnerListener()
				{
					@Override
					public void onAdd(JSONObject json) {
						try {
							String[] ob={"Name","FLAG", "ServerIP", "ServerPort", "SSLPort","ProxyPort", "ServerUser", "ServerPass", "Sfrep", "sInfo" ,"Slowchave",  "Nameserver", "servermessage",  };
							for (int i=0;i < ob.length;i++) {
								ja.getJSONObject(position).remove(ob[i]);
							}
							for (int i=0;i < json.length();i++) {
								ja.getJSONObject(position).put(ob[i], json.getString(ob[i]));
							}
							sp.edit().putString("ServerList", ja.toString()).apply();
							setupJson();

						} catch (JSONException e) {
							Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
						}
					}
				});
			a.init();
		} catch (JSONException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
		}
	}

	public void deleteServer(final int position) {

		try {
			JSONArray ja=new JSONArray(sp.getString("ServerList", "[]"));
			ja.remove(position);
			sp.edit().putString("ServerList", ja.toString()).apply();
			setupJson();

		} catch (JSONException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
		}

	}

	public void add(View v) {

		ServerDialog.Server a=new ServerDialog.Server(this);
		a.add();
		a.onServerAdd(new SpinnerListener()
			{
				@Override
				public void onAdd(JSONObject json) {
					try {
						JSONArray ja=new JSONArray(sp.getString("ServerList", "[]"));
						ja.put(json);
						sp.edit().putString("ServerList", ja.toString()).apply();
						setupJson();

					} catch (JSONException e) {
						Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
					}
				}
			});
		a.init();
	}

	public void editPayload(final int position) {

		PayloadDialog.Payload a=new PayloadDialog.Payload(MainActivity.this);
		try {
			final JSONArray ja=new JSONArray(sp.getString("PayloadList", "[]"));
			a.edit(ja.getJSONObject(position));
			a.onPayloadAdd(new SpinnerListener()
				{
					@Override
					public void onAdd(JSONObject json) {
						try {
							String[] ob={"Name","Payload", "SNI", "pInfo" , "Slowdns", "WebProxy", "WebPort" , "isSSL", "isPayloadSSL","isSlow", "isHatok", "isInject","isDirect","isWeb"};
							for (int i=0;i < ob.length;i++) {
								ja.getJSONObject(position).remove(ob[i]);
							}
							for (int i=0;i < json.length();i++) {
								ja.getJSONObject(position).put(ob[i], json.getString(ob[i]));
							}
							sp.edit().putString("PayloadList", ja.toString()).apply();
							setupJson();

						} catch (JSONException e) {
							Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
						}
					}
				});
			a.init();
		} catch (JSONException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
		}
	}

	public void deletePayload(final int position) {

		try {
			JSONArray ja=new JSONArray(sp.getString("PayloadList", "[]"));
			ja.remove(position);
			sp.edit().putString("PayloadList", ja.toString()).apply();
			setupJson();

		} catch (JSONException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
		}

	}

	public void addPayload(View v) {

		PayloadDialog.Payload a=new PayloadDialog.Payload(this);
		a.add();
		a.onPayloadAdd(new SpinnerListener()
			{
				@Override
				public void onAdd(JSONObject json) {
					try {
						JSONArray ja=new JSONArray(sp.getString("PayloadList", "[]"));
						ja.put(json);
						sp.edit().putString("PayloadList", ja.toString()).apply();
						setupJson();

					} catch (JSONException e) {
						Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
					}
				}
			});
		a.init();
	}

	public void edit(View v) {
		editServer(a.getSelectedItemPosition());
	}

	public void del(View v) {
		AlertDialog dialog=new AlertDialog.Builder(this)
			.setTitle("Delete Configuration")
			.setMessage("Are you sure you want to delete?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dia, int which) {
					deleteServer(a.getSelectedItemPosition());
				}
			})
			.setNegativeButton("No", null)
			.create();
		dialog.show();

	}

	public void editPayload(View v) {
		editPayload(b.getSelectedItemPosition());
	}

	public void deletePayload(View v) {
		AlertDialog dialog=new AlertDialog.Builder(this)
			.setTitle("Delete Configuration")
			.setMessage("Are you sure you want to delete?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dia, int which) {
					deletePayload(b.getSelectedItemPosition());
				}
			})
			.setNegativeButton("No", null)
			.create();
		dialog.show();
	}


	private JSONObject ja() {
		String ja=sp.getString("Configuration", "{}");
		try {

			JSONArray a=new JSONArray(sp.getString("ServerList", "[]"));
			JSONArray b=new JSONArray(sp.getString("PayloadList", "[]"));
			return new JSONObject(ja).put("Version", this.v).put("ReleaseNotes", this.n).put("ReleaseNotes1", this.m).put("Servers", a).put("Networks", b);
		} catch (JSONException e) {
			return null;
		}
	}


    private ServerSpinnerAdapter adapter() {
		ArrayList<JSONObject> al=new ArrayList<JSONObject>();
		ServerSpinnerAdapter ad=new ServerSpinnerAdapter(this, al);
		ad.setPath("servers");
		try {
			for (int i=0;i < ja().getJSONArray("Servers").length();i++) {
				JSONArray ja=ja().getJSONArray("Servers");
				al.add(ja.getJSONObject(i));
			}
			return ad;
		} catch (Exception e) {
			return null;
		}
	}

	private PayloadSpinnerAdapter adapterP() {
		ArrayList<JSONObject> al=new ArrayList<JSONObject>();
		PayloadSpinnerAdapter ad=new PayloadSpinnerAdapter(this, al);
		ad.setPath("networks");
		try {
			for (int i=0;i < ja().getJSONArray("Networks").length();i++) {
				JSONArray ja=ja().getJSONArray("Networks");
				al.add(ja.getJSONObject(i));
			}
			return ad;
		} catch (Exception e) {
			return null;
		}





		//	return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.abc_menu, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Menu Itens
		switch (item.getItemId()) {
			case R.id.item1:
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
					if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
						== PackageManager.PERMISSION_DENIED) {
						Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
						String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
						requestPermissions(permissions, 1);
					}
				}
				new SaveDialog((Context)this, this.ja());
		
				break;
			
			case R.id.item3:
				AlertDialog dialog=new AlertDialog.Builder(this)
					.setTitle("Restaura A's Configurações")
					.setMessage("isso Linpa Todas as Configurações,incerto os servidores e payload")
					.setPositiveButton("Linpa", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dia, int which) {
							sp.edit().clear().apply();
							recreate();
						}
					})
					.setNegativeButton("Não", null)
					.create();
				dialog.show();
				break;
				
			case R.id.item4:
				setupImport();
				break;
				
			case R.id.item5:
				new PassDialog(this,"baby");
				break;
				
			case R.id.item6:
				new Version(this,"1");
				break;
				
			case R.id.item7:
				new ReleaseNotes(this,"Test Update");
				break;
				
			case R.id.item8:
				new ReleaseNotes1(this,"Announcement");
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void setupImport() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }

        DialogProperties properties=new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.extensions = new String[] {".json", ".JSON"};
        properties.root = Environment.getExternalStorageDirectory();
        dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select a File");
        dialog.setPositiveBtnName("Salva");
        dialog.setNegativeBtnName("Cancel");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {

                @Override
                public void onSelectedFilePaths(final String[] files) {
                    for (String path:files) {
                        File file=new File(path);
                        if (file.getName().endsWith(".json") || file.getName().endsWith(".JSON")) {
                            final String data = inet(file.getAbsolutePath());
                            if (TextUtils.isEmpty(data)) {
                                Toast.makeText(getApplicationContext(), "Empty Data!", Toast.LENGTH_LONG).show();


                            } else {

								//    String string = sp.getString("pass", ""); 
                                try {
                                    final JSONObject obj = new JSONObject(AESCrypt.decrypt(sp.getString("pass", ""), data));

                                    final JSONArray server = obj.getJSONArray("Servers");
                                    final JSONArray payload = obj.getJSONArray("Networks");
                                    String version = obj.getString("Version");
                                    String notes = obj.getString("ReleaseNotes");
									String notes1 = obj.getString("ReleaseNotes1");

                                    sp.edit().putString("ServerList" ,server.toString()).apply();
                                    sp.edit().putString("PayloadList", payload.toString()).apply();
                                    sp.edit().putString("Version" ,version).apply();
                                    sp.edit().putString("ReleaseNotes", notes).apply();
									sp.edit().putString("ReleaseNotes1", notes1).apply();

									setupJson();

                                    Toast.makeText(getApplicationContext(), "Imported successfully!", Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {

                                } catch (GeneralSecurityException e) {

                                    Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }}}            
            });

        dialog.show();
    }
    private void setupJson() {

        v = sp.getString("Version", "");

        n = sp.getString("ReleaseNotes", "");
		
		m = sp.getString("ReleaseNotes1", "");

        try {
            JSONObject jsonObj = new JSONObject(ja().toString());
            String finalJson = jsonObj.toString(1);

            ((TextView)findViewById(R.id.json)).setText(finalJson);
        } catch (JSONException e) {}

        if (adapter() != null) {
            a.setAdapter(adapter());
        }


        if (adapterP() != null) {
            b.setAdapter(adapterP());
        }
    }
    private String inet(String Path) {
        try
        {
            InputStream openRawResource = new FileInputStream(Path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            for (int read = openRawResource.read(); read != -1; read = openRawResource.read())
            {
                byteArrayOutputStream.write(read);
            }
            openRawResource.close();
            return byteArrayOutputStream.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }return null;
    }    
    @Override 
    protected void onResume() { 
		super.onResume();
        Timer timer2 = new Timer(); 
        TimerTask qw = new TimerTask() {

            @Override
            public void run() {
                MainActivity mainActivity = MainActivity.this; 
                Runnable ja = new Runnable() { 


					@Override
					public void run() {

						if (sp.getString("isChanged", "").equals("yes")) { 
							setupJson(); 
							sp.edit().putString("isChanged", "no").apply(); 
						} 
					} 
				}; 
                mainActivity.runOnUiThread(ja); 
            } 
        }; 
        timer2.schedule(qw, (long) 0, (long) 500); 
    }
    
}
