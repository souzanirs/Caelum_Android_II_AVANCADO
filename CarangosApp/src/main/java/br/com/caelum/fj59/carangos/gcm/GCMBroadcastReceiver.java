package br.com.caelum.fj59.carangos.gcm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.LeilaoActivity;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by android6275 on 03/11/16.
 */

public class GCMBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String mensagem = (String) intent.getExtras().getSerializable("message");

        MyLog.i("----->>> MENSAGEM COM CONTEUDO: "+mensagem);

        Intent irParaLeilao = new Intent(context, LeilaoActivity.class);

        PendingIntent acaoPendente = PendingIntent.getActivity(context, 123, irParaLeilao, PendingIntent.FLAG_CANCEL_CURRENT);

        irParaLeilao.putExtra("idDaNotificacao",Constantes.ID_NOTIFICACAO);

        Notification notificacao = new Notification.Builder(context)
                .setContentTitle("Um novo leilão começou!")
                .setContentText("Leilão: "+mensagem)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(acaoPendente)
                .setAutoCancel(true)
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        manager.notify(Constantes.ID_NOTIFICACAO, notificacao);

    }
}