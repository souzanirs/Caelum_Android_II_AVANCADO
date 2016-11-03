package br.com.caelum.fj59.carangos.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by android6275 on 03/11/16.
 */

public class GCMBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("CHEGOU A MENSAGEM DO GCM!");
    }
}
