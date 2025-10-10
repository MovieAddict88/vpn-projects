package com.mycompany.myapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.mycompany.mygen.R;

public class OutputDialog {
	private static EditText output;

	private AlertDialog.Builder a;

	private Context c;

	public OutputDialog(Context c) {
		a = new AlertDialog.Builder(c);
		this.c = c;
	}

	public void show(String json) {
		View v=LayoutInflater.from(c).inflate(R.layout.output_dialog, null);
		output = v.findViewById(R.id.output);
		output.setText(json);
		
		a.setView(v);
	}

	public void init() {
		a.create().show();
	}
}
