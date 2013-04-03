package com.renata.projetoandroid;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

public class UsuarioView extends Activity {

	private static final int fail_Jason = 0;
	private static final int falha = 1;
	private ListView noteListView;
	private ProgressDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usuario_view);

		dialog = new ProgressDialog(this);
		dialog.setMessage(getString(R.string.carregando));
		dialog.show();
		
		new LoadUsuarioAsyncTask().execute();

	}

	private Handler handler = new Handler() {
		public void handlerMessage(android.os.Message msg) {
			
			AlertDialog.Builder alerta = new AlertDialog.Builder(
					UsuarioView.this);
			if ((dialog != null) && (dialog.isShowing()))
				dialog.dismiss();
			switch (msg.what) {
			case fail_Jason:
				alerta.setTitle("Aviso");
				alerta.setMessage("Erro de conexão");
				alerta.setPositiveButton("ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialogo,
									int arg1) {
								// TODO Auto-generated method stub
								dialogo.dismiss();
								finish();
							}
						});

				alerta.show();
				break;

			case falha:
				alerta.setTitle("Aviso");
				alerta.setMessage("Nenhum usuario cadastrado");
				alerta.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialogo,
									int arg1) {
								dialogo.dismiss();
								finish();
							}
						});
				alerta.show();

				break;
			}
		};

	};

	private class LoadUsuarioAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String url = "http://bolaoshow.herokuapp.com/service/usuarios";
			return ConexaoHttp.getRequest(url);
		}

		@Override
		protected void onPostExecute(String resultado) {
			super.onPostExecute(resultado);
			dialog.dismiss();

			if (resultado != null) {
				List<Usuario> listaUsuario = new ArrayList<Usuario>();
				Usuario usuario = null;
				try {
					JSONObject resultadoJason = new JSONObject(resultado);
					JSONArray jsonArray = resultadoJason
							.getJSONArray("usuario");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject objetoJ = jsonArray.getJSONObject(i);

						usuario = new Usuario(null, null, 0);

						try {
							usuario.setId(objetoJ.getInt("usuario"));
						} catch (JSONException e) {
							usuario.setId(-1);
						}
						try {
							usuario.setNome(objetoJ.getString("nome"));

						} catch (JSONException e) {
							usuario.setNome(" ");
						}
						try {
							usuario.setSenha(objetoJ.getString("senha"));

						} catch (JSONException e) {
							usuario.setSenha("");
						}
						//
						listaUsuario.add(usuario);
					}

					UsuarioListaAdapter adapter = new UsuarioListaAdapter(UsuarioView.this, R.layout.usuario_view, listaUsuario);
					noteListView = (ListView) findViewById(R.id.usuario_v);
					noteListView.setAdapter(adapter);

				} catch (JSONException e) {
					handler.sendEmptyMessage(fail_Jason);
				}
			} else {
				handler.sendEmptyMessage(falha);
			}
		}

	}

}