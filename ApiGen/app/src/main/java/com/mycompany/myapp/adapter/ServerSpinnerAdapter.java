package com.mycompany.myapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mycompany.mygen.R;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONObject;

public class ServerSpinnerAdapter extends ArrayAdapter<JSONObject>
{

	private TextView tv;
	
	private ImageView im;
	
	private String path;
	public ServerSpinnerAdapter(Context c,ArrayList<JSONObject> a)
	{
		super(c,R.layout.server_spinner,a);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		return v(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return v(position, convertView, parent);
	}

	public void setPath(String path)
	{
		this.path=path;
	}
	
	private View v(int position, View c, ViewGroup parent)
	{
		c=LayoutInflater.from(getContext()).inflate(R.layout.server_spinner,parent,false);
		tv=c.findViewById(R.id.name);
		im=c.findViewById(R.id.icon);
		try
		{
			tv.setText(getItem(position).getString("Name"));
			getServerIcon(position, im);
		}
		catch (Exception e)
		{
			tv.setText(e.getMessage());
		}
		return c;
	}
	
	private void getServerIcon(int position, ImageView im) throws Exception {
		InputStream inputStream = getContext().getAssets().open("flags/" + getItem(position).getString("FLAG"));
		im.setImageDrawable(Drawable.createFromStream(inputStream, getItem(position).getString("FLAG")));
		if (inputStream != null) {
			inputStream.close();
		}
	}
	
	
}
