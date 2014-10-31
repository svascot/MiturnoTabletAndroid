package com.miturno.miturnotablet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.turno.bean.bean;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Build;

public class ListDepActivity extends Activity {
	private String jsonResult;
	private String url = "http://mvvtech.com/svascot/MiTurno/miturno/index.php/site/listDep";
	private LinearLayout contenedor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Quita la barra de titulo
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_dep);
		contenedor = (LinearLayout) findViewById(R.id.conteneder);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
StrictMode.setThreadPolicy(policy);

		
		accessWebService();

	}

	public void callPedirTurno() {
		Intent myIntent = new Intent(ListDepActivity.this,
				PedirTurnoAcitivy.class);

		ListDepActivity.this.startActivity(myIntent);
		
		finish();

	}

	// Async Task to access the web
	private class JsonReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {

				List<NameValuePair> parametros = new ArrayList<NameValuePair>();
				parametros.add(new BasicNameValuePair("nombreEp", bean
						.getIdEmpresa()));

				httppost.setEntity(new UrlEncodedFormEntity(parametros));

				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		@Override
		protected void onPostExecute(String result) {
			ListDrwaer();
		}
	}// end async task

	public void accessWebService() {
		JsonReadTask task = new JsonReadTask();
		// passes values for the urls string array
		task.execute(new String[] { url });
	}

	// build hash set for list view
	public void ListDrwaer() {

		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("listDep");

			for (int i = 0; i < jsonMainNode.length(); i++) {

				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String name = jsonChildNode.optString("Dependencia");
				String number = jsonChildNode.optString("Turnos");

				String outPut = name.toUpperCase() + " \n " + number
						+ " turnos esperando";

				Button boton = new Button(this);
				boton.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT,
						LinearLayout.LayoutParams.FILL_PARENT, 1));

				boton.setTextSize(20);

				boton.setTextColor(Color.parseColor("#ffa800"));

				if (i % 2 == 0) {
					boton.setBackgroundColor(Color.parseColor("#282828"));
				} else {
					boton.setBackgroundColor(Color.parseColor("#464646"));
				}

				boton.setText(outPut);

				final String pasarNombre = name;

				boton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						bean.setNombreDependencia(pasarNombre);
						callPedirTurno();

					}
				});

				contenedor.addView(boton);

			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}

	}

}
