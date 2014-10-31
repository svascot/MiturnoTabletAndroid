package com.miturno.miturnotablet;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Quita la barra de titulo
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

	
	}
	
	public void callLisdEmpresas(){
		SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
		preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);  
			    
			    
		String empresa = preferencias.getString("Empresa", "");
		
		
			Intent myIntent = new Intent(MainActivity.this,
					ListEmpActivity.class);
			MainActivity.this.startActivity(myIntent);
			finish();
	}

	public void callListEmp(View v){
		
		AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
				MainActivity.this);

		// Setting Dialog Title
		alertDialog2.setTitle("Mi Turno");

		// Setting Dialog Message
		alertDialog2
				.setMessage("Una vez elegida una empresa no puede cambiarse hasta volver a abrir la aplicación.");

		// Setting Icon to Dialog
//		alertDialog2.setIcon(R.drawable.ic_launcher);

		// Setting Positive "Yes" Btn
		alertDialog2.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface dialog,
							int which) {
						// Write your code here to execute
						// after dialog
						callLisdEmpresas();
						
					}
				});
		// Showing Alert Dialog
		alertDialog2.show();
		
	}

}
