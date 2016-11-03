package br.com.caelum.fj59.carangos.tasks;

import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android6275 on 03/11/16.
 */

public class BuscaLeiloesTask extends TimerTask{

    private CustomHandler handler;
    private Calendar horarioUltimaBusca;
    private CarangosApplication application;

    public BuscaLeiloesTask(CustomHandler handler, Calendar horarioUltimaBusca){
        this.horarioUltimaBusca = horarioUltimaBusca;
        this.handler = handler;
    }

    @Override
    public void run() {

        MyLog.i("----->>> EFETUANDO BUSCA!");
        WebClient client = new WebClient("leilao/leilaoid54635/"+
                                new SimpleDateFormat("ddMMyy-HHmmss").format(horarioUltimaBusca.getTime()), application);

        String json = client.get();

        MyLog.i("LANCES RECEBIDOS: "+json);

        Message message = handler.obtainMessage();
        message.obj = json;
        handler.sendMessage(message);

        horarioUltimaBusca = Calendar.getInstance();
    }

    public void executa(){
        Timer timer = new Timer();
        timer.schedule(this, 0, 30*1000);
    }
}
