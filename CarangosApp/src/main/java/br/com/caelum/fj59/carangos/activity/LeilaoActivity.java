package br.com.caelum.fj59.carangos.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.modelo.Lance;
import br.com.caelum.fj59.carangos.tasks.BuscaLeiloesTask;
import br.com.caelum.fj59.carangos.tasks.CustomHandler;

/**
 * Created by android6275 on 03/11/16.
 */

public class LeilaoActivity extends ActionBarActivity {

    private List<Lance> lancesAgora = new ArrayList<Lance>();
    private Calendar horarioUltimaBusca = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leilao);

        ListView lancesList = (ListView) findViewById(R.id.lances_list);

        ArrayAdapter<Lance> adapter = new ArrayAdapter<Lance>(LeilaoActivity.this, android.R.layout.simple_list_item_1, lancesAgora);

        lancesList.setAdapter(adapter);

        CustomHandler handler = new CustomHandler(adapter, lancesAgora);

        new BuscaLeiloesTask(horarioUltimaBusca).executa();
    }
}
