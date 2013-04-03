package com.renata.projetoandroid;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AdicionarUsuarioActivity extends Activity implements OnClickListener{
	
	private static final int ok = 0;
	private static final int fail = 1;
	private ListView noteListView;
	private ProgressDialog pDialog;
	private EditText editTextNome;
	private EditText editTextSenha;
	private Button buttonCadastrarPost;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		editTextNome=(EditText)findViewById(R.id.editTextNome);
		editTextSenha=(EditText)findViewById(R.id.editTextSenha);
		buttonCadastrarPost=(Button)findViewById(R.id.buttonCadastrarPost);
		findViewById(R.id.buttonCadastrarPost).setOnClickListener(this);
	
	}

	
	
	
	private Handler handler = new Handler();
	
	public void handleMessage(android.os.Message msg){
		AlertDialog.Builder alerta = new AlertDialog.Builder(AdicionarUsuarioActivity.this);
		if((pDialog!=null)&&(pDialog.isShowing()))
			pDialog.dismiss();
		
		switch(msg.what){
		case ok:
			alerta.setMessage("Usuario inserido!");
			alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					finish();
				}
			});
			alerta.show();
			break;
		case fail:
			alerta.setTitle("##Aviso##");
			alerta.setMessage("Preencha os campos obrigatórios");
			alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			alerta.show();
			break;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.buttonCadastrarPost:
			buttonCadastrarPost.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String nome = editTextNome.getText().toString();
					String senha = editTextSenha.getText().toString();
					JSONObject json = new JSONObject();
					
					try {
						json.put("nome", nome);
						json.put("escudo", senha);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					ConexaoHttp.postRequest("http://bolaoshow.herokuapp.com/service/usuarios", json);
					
				}
					
					
				
			});
			pDialog= new ProgressDialog(this);
			pDialog.setMessage(getString(R.string.inserindo_usuario));
			pDialog.show();
			break;
		}
		
	}

}



