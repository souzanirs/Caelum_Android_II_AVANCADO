package br.com.caelum.fj59.carangos.app;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.RegistraAparelhoTask;

/**
 * Created by android6275 on 31/10/16.
 */

public class CarangosApplication extends Application {

    private List<AsyncTask<?, ?, ?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();
    private List<Publicacao> publicacoes = new ArrayList<Publicacao>();
    private static final String REGISTRO = "registradoNoGcm";
    private static final String ID_DO_REGISTRO = "idDoRegistro";
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);

        registraNoGcm();
    }

    private void registraNoGcm() {
        if(!usuarioRegistrado()){
            new RegistraAparelhoTask(this).execute();
        } else {
            MyLog.i("---------------->>>APARELHO JÁ REGISTRADO: ID = "+preferences.getString(ID_DO_REGISTRO, null));
        }
    }

    private boolean usuarioRegistrado() {
        return preferences.getBoolean(REGISTRO, false);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        for(AsyncTask task : tasks){
            task.cancel(true);
        }
    }

    public void registra(AsyncTask<?, ?, ?> asyncTask){
        tasks.add(asyncTask);
    }

    public void desregistra(AsyncTask<?, ?, ?> asyncTask){
        tasks.remove(asyncTask);
    }

    public List<Publicacao> getPublicacoes(){
        return publicacoes;
    }

    public void lidaComRespostaDoRegistroNoServidor(String registro) {
        if(registro != null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTRO, true);
            editor.putString(ID_DO_REGISTRO, registro);
            editor.commit();
        }
    }
}
