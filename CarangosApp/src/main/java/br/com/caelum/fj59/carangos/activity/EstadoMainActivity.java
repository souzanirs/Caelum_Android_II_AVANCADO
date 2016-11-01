package br.com.caelum.fj59.carangos.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;

/**
 * Created by android6275 on 31/10/16.
 */

public enum EstadoMainActivity {

    INICIO{
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
            activity.alteraEstadoEExecuta(EstadoMainActivity.AGUARDANDO_PUBLICACOES);
        }
    },
    AGUARDANDO_PUBLICACOES{
        @Override
        public void executa(MainActivity activity) {
            ProgressFragment progress = ProgressFragment.comMensagem(R.string.carregando);
            this.colocaFragmentNaTela(activity, progress);
        }
    },
    PRIMEIRAS_PUBLICACOES_RECEBIDAS{
        @Override
        public void executa(MainActivity activity) {
            ListaDePublicacoesFragment publicacoesFragment = new ListaDePublicacoesFragment();
            this.colocaFragmentNaTela(activity, publicacoesFragment);
        }
    },
    PULL_TO_REFRESH_REQUISITADO{
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
        }
    };

    void colocaFragmentNaTela(MainActivity activity, Fragment fragment){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_principal, fragment);
        transaction.commit();
    }

    public abstract void executa(MainActivity activity);
}
