package br.com.caelum.fj59.carangos.app;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by android6275 on 31/10/16.
 */

public class CarangosApplication extends Application {

    private List<AsyncTask<?, ?, ?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();
    private List<Publicacao> publicacoes = new ArrayList<Publicacao>();

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


}
