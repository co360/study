package argentum.indicadores;

import argentum.modelo.SerieTemporal;

public class IndicadorAbertura implements Indicador {

    @Override
    public double calcula(int posicao, SerieTemporal serie) {
        return serie.getCandle(posicao)
                    .getAbertura();
    }
}
