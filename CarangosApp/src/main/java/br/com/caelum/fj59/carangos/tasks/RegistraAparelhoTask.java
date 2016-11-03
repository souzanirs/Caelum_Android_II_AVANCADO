package br.com.caelum.fj59.carangos.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android6275 on 03/11/16.
 */

public class RegistraAparelhoTask extends AsyncTask<Void, Void, String> {

    private CarangosApplication app;

    public RegistraAparelhoTask(CarangosApplication app){
        this.app = app;
    }

    @Override
    protected String doInBackground(Void... params) {
        String registrationId = null;

        try{
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.app);
            registrationId = gcm.register(Constantes.GCM_SERVER_ID);

            MyLog.i("APARELHO REGISTRADO COM ID: "+registrationId);

            String email = InformacoesDoUsuario.getEmail(this.app);
            String url = "device/register/"+email+"/"+registrationId;
            WebClient client = new WebClient(url);
            client.post();

        } catch (IOException e) {
            MyLog.i("PROBLEMA NO REGISTRO DO APARELHO NO SERVER! "+e.getMessage());
        }

        return registrationId;
    }

    @Override
    protected void onPostExecute(String s) {
        app.lidaComRespostaDoRegistroNoServidor(s);
    }
}
