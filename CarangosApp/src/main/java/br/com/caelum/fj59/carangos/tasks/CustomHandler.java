package br.com.caelum.fj59.carangos.tasks;

import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import java.util.Collections;
import java.util.List;
import br.com.caelum.fj59.carangos.converter.LanceConverter;
import br.com.caelum.fj59.carangos.modelo.Lance;

/**
 * Created by android6275 on 03/11/16.
 */

public class CustomHandler extends Handler {

    private ArrayAdapter<Lance> adapter;
    private List<Lance> lancesAgora;

    public CustomHandler(ArrayAdapter<Lance> arrayAdapter, List<Lance> lances){
        this.adapter = arrayAdapter;
        this.lancesAgora = lances;
    }

    @Override
    public void handleMessage(Message msg) {
        String json = (String) msg.obj;
        List<Lance> novosLances = new LanceConverter().converte(json);

        lancesAgora.addAll(novosLances);
        adapter.notifyDataSetChanged();

        Collections.sort(lancesAgora);
    }
}
