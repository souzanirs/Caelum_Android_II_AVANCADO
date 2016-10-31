package br.com.caelum.fj59.carangos.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate{


    private EstadoMainActivity estado;
    private static final String ESTADO_ATUAL = "ESTADO ATUAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.estado = EstadoMainActivity.INICIO;
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

    /*public List<Publicacao> getPublicacoes() {
        return this.publicacoes;
    }*/

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
        new BuscaMaisPublicacoesTask(this).execute();
    }
}
