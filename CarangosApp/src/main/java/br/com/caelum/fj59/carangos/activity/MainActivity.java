package br.com.caelum.fj59.carangos.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate{

    private EstadoMainActivity estado;
    private static final String ESTADO_ATUAL = "ESTADO ATUAL";
    private List<Publicacao> publicacoes;

    //Guardando o evento como atributo
    private EventoPublicacoesRecebidas evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.estado = EstadoMainActivity.INICIO;

        //Primeiro registrando a Activity como Observador
        this.evento = EventoPublicacoesRecebidas.registraObservador(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO "+this.estado);

        outState.putSerializable(ESTADO_ATUAL, this.estado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        MyLog.i("RESTAURANDO ESTADO!");
        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyLog.i("EXECUTANDO ESTADO "+this.estado);
        this.estado.executa(this);
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estadoMainActivity){
        this.estado = estadoMainActivity;
        this.estado.executa(this);
    }

    @Override
    public void lidaComRetorno(List<Publicacao> resultado) {

        CarangosApplication application = (CarangosApplication) getApplication();
        List<Publicacao> publicacoes = application.getPublicacoes();

        publicacoes.clear();
        publicacoes.addAll(resultado);

        this.estado = EstadoMainActivity.PRIMEIRAS_PUBLICACOES_RECEBIDAS;
        this.estado.executa(this);
    }

    @Override
    public void lidaComErro(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "Erro ao buscar dados ("+e+")",Toast.LENGTH_LONG).show();
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public void buscaPublicacoes(){
        new BuscaMaisPublicacoesTask(getCarangosApplication()).execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.evento.desregistra(getCarangosApplication());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem compras = menu.add("Compras");
        compras.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        String acaoCustomizada = getResources().getString(R.string.action_compra);
        Intent intent = new Intent(acaoCustomizada);

        compras.setIntent(intent);

        return true;
    }
}
