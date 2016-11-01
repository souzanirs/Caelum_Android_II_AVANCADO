package br.com.caelum.fj59.carangos.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.EstadoMainActivity;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by erich on 9/11/13.
 */

public class ListaDePublicacoesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView publicacoesList;
    private PublicacaoAdapter adapter;
    private SwipeRefreshLayout swipe;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        swipe = (SwipeRefreshLayout) inflater.inflate(R.layout.publicacoes_list, container, false);

        swipe.setOnRefreshListener(this);

        publicacoesList = (ListView) swipe.findViewById(R.id.publicacoes_list);

        activity = (MainActivity) this.getActivity();

        CarangosApplication application = activity.getCarangosApplication();

        List<Publicacao> publicacoes = application.getPublicacoes();

        adapter = new PublicacaoAdapter(getActivity(), publicacoes);

        publicacoesList.setAdapter(adapter);

        swipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light);

        return swipe;

    }

    @Override
    public void onRefresh() {
        MyLog.i("PULL TO REPRESH INICIADO");
        activity.alteraEstadoEExecuta(EstadoMainActivity.PULL_TO_REFRESH_REQUISITADO);
    }

    @Override
    public void onPause() {
        super.onPause();

        swipe.setRefreshing(false);
        swipe.clearAnimation();
    }
}
