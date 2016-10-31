package br.com.caelum.fj59.carangos.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;

/**
 * Created by erich on 9/11/13.
 */
public class ListaDePublicacoesFragment extends Fragment {

    private ListView publicacoesList;
    private PublicacaoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        publicacoesList = (ListView) inflater.inflate(R.layout.publicacoes_list, container, false);

        MainActivity activity = (MainActivity) this.getActivity();
        CarangosApplication application = activity.getCarangosApplication();

        adapter = new PublicacaoAdapter(getActivity(), application.getPublicacoes());
        publicacoesList.setAdapter(adapter);
        return publicacoesList;

    }
}
