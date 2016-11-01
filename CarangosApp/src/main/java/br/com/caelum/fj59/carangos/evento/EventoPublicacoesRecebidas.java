package br.com.caelum.fj59.carangos.evento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;

/**
 * Created by android6275 on 31/10/16.
 */

public class EventoPublicacoesRecebidas extends BroadcastReceiver {

    private static final String RETORNO = "RETORNO";
    private static final String SUCESSO = "SUCESSO";
    private static final String PUBLICACOES_RECEBIDAS = "PUBLICAÇÕES RECEBIDAS";
    private BuscaMaisPublicacoesDelegate delegate;

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean deuCerto = intent.getBooleanExtra(SUCESSO, false);
        MyLog.i("RECEBI O EVENTO!!! DEU CERTO?"+deuCerto);

        if(deuCerto){

            delegate.lidaComRetorno((List<Publicacao>) intent.getSerializableExtra(RETORNO));

        } else {

            delegate.lidaComErro((Exception) intent.getSerializableExtra(RETORNO));

        }
    }

    public static EventoPublicacoesRecebidas registraObservador(BuscaMaisPublicacoesDelegate delegate){

        EventoPublicacoesRecebidas receiver = new EventoPublicacoesRecebidas();
        receiver.delegate = delegate;

        LocalBroadcastManager.getInstance(delegate.getCarangosApplication())
                .registerReceiver(receiver, new IntentFilter(PUBLICACOES_RECEBIDAS));

        return receiver;
    }

    public static void notifica(Context context, Serializable resultado, boolean sucesso){

        Intent intent = new Intent(PUBLICACOES_RECEBIDAS);

        intent.putExtra(RETORNO, resultado);
        intent.putExtra(SUCESSO, sucesso);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    public void desregistra(CarangosApplication carangosApplication) {
        LocalBroadcastManager.getInstance(carangosApplication).unregisterReceiver(this);
    }
}
