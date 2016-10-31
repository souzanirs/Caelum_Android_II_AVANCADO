package br.com.caelum.fj59.carangos.tasks;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by android6275 on 31/10/16.
 */

public interface BuscaMaisPublicacoesDelegate {
    void lidaComRetorno(List<Publicacao> retorno);
    void lidaComErro(Exception e);

    CarangosApplication getCarangosApplication();
}
