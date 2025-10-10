package com.mycompany.myapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mycompany.mygen.R;
import java.util.ArrayList;
import org.json.JSONObject;

public class PayloadSpinnerAdapter extends ArrayAdapter<JSONObject> {

	private TextView tv;

	private ImageView im;

	private String path;
	public PayloadSpinnerAdapter(Context c, ArrayList<JSONObject> a) {
		super(c, R.layout.payload_spinner, a);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return v(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return v(position, convertView, parent);
	}

	public void setPath(String path) {
		this.path = path;
	}

	private View v(int position, View c, ViewGroup parent) {
		c = LayoutInflater.from(getContext()).inflate(R.layout.payload_spinner, parent, false);
		tv = c.findViewById(R.id.pName);
		im = c.findViewById(R.id.pIcon);
		try {
			tv.setText(getItem(position).getString("Name"));
			getPayloadIcon(position, im);
			
		} catch (Exception e) {
			tv.setText(e.getMessage());
		}
		return c;
	}

	private void getPayloadIcon(int position, ImageView im) throws Exception {
		String name = getItem(position).getString("Name").toLowerCase();
		if (name.contains("globe")) {
			im.setImageResource(R.drawable.ic_globe);
		} else if (name.contains("smart")) {
			im.setImageResource(R.drawable.ic_smart);
		} else if (name.contains("gtm")) {
			im.setImageResource(R.drawable.ic_gtm);
		} else if (name.contains("tm")) {
			im.setImageResource(R.drawable.ic_tm);
		} else if (name.contains("tnt")) {
			im.setImageResource(R.drawable.ic_tnt);
		} else if (name.contains("sun")) {
			im.setImageResource(R.drawable.ic_sun);
		} else if (name.contains("sts")) {
			im.setImageResource(R.drawable.ic_sts);
		} else if (name.contains("ic")) {
		    im.setImageResource(R.drawable.icon);
	    } else if (name.contains("")) {
		    im.setImageResource(R.drawable.icon_speed);
	    }
		
	}

}
