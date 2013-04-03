package com.renata.projetoandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UsuarioListaAdapter extends ArrayAdapter<Usuario>{

	private ArrayList<Usuario>usuarios;
	
	public UsuarioListaAdapter(Context context, int textViewResourceId, List<Usuario>usuarios) {
		super(context, textViewResourceId, usuarios);
		this.usuarios=(ArrayList<Usuario>) usuarios;
	}

	public View getView(int posicao, View convertView, ViewGroup parent){
		
		LinearLayout visor = (LinearLayout) convertView;
		
		if (visor == null){
			LayoutInflater visor_dois = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
			visor=(LinearLayout)visor_dois.inflate(R.layout.usuario_view,null);
		
			
		}
		
		Usuario usuario =usuarios.get(posicao);
		TextView nome = (TextView) visor.findViewById(R.id.editTextNome);
		if (usuario != null){
			nome.setText("Nome: "+usuario.getNome());
		}
		TextView senha = (TextView) visor.findViewById(R.id.editTextSenha);
		if (usuario != null){
			nome.setText("Senha: "+usuario.getSenha());
		}
		
		return visor;
		
	}
}
