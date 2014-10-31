package com.miturno.miturnotablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.turno.bean.bean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class PedirTurnoAcitivy extends Activity {

	String turnosEspera;
	String nombreDep;
	String nombreEmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Quita la barra de titulo
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pedir_turno_acitivy);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			nombreDep = bean.getNombreDependencia();
			nombreEmp = bean.getIdEmpresa();
		} catch (Exception e) {

		}

		peticionPedir();

	}

	public void peticionPedir() {
		HttpPost httppost;

		HttpResponse response;
		HttpClient httpclient;

		String htmlResponse = null;
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(
					"http://mvvtech.com/svascot/MiTurno/miturno/index.php/site/pedirTurno");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nombreDep", nombreDep));
			params.add(new BasicNameValuePair("nombreEmp", nombreEmp));

			httppost.setEntity(new UrlEncodedFormEntity(params));
			// Execute HTTP Post Request
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			htmlResponse = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		} catch (IOException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
		
		
		
		String[] turno = htmlResponse.split("-");
		String sTurnoPedido = turno[1];
		String Cod = turno[0];
		
//		Toast.makeText(getBaseContext(), "turno => "+ sTurnoPedido + " Codigo => " + Cod, Toast.LENGTH_SHORT)
//		.show();
		
//		String sTurnoActual = turno[2];

		TextView txtCod = (TextView) findViewById(R.id.txtCodigo);
		txtCod.setText(Cod);

		TextView txtTurnoPedido = (TextView) findViewById(R.id.txtTurno);
		txtTurnoPedido.setText(sTurnoPedido);

	}

	public void imprimir(View v) {
		// Aqui es donde le dice a la impresora que imprima

		Intent myIntent = new Intent(PedirTurnoAcitivy.this,
			ListDepActivity.class);
		PedirTurnoAcitivy.this.startActivity(myIntent);
		
		finish();
	}

}
