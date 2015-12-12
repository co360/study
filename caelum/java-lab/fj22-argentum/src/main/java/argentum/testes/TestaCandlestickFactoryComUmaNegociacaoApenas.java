package argentum.testes;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import argentum.modelo.Candlestick;
import argentum.modelo.CandlestickFactory;
import argentum.modelo.Negociacao;

public class TestaCandlestickFactoryComUmaNegociacaoApenas {
    public static void main(String[] args) {
        Calendar hoje = Calendar.getInstance();

        Negociacao negociacao = new Negociacao(40.5, 100, hoje);

        List<Negociacao> negociacoes = Arrays.asList(negociacao);

        CandlestickFactory fabrica = new CandlestickFactory();
        Candlestick candle = fabrica.constroiCandleParaData(hoje, negociacoes);

        System.out.println(candle.getAbertura());
        System.out.println(candle.getFechamento());
        System.out.println(candle.getMinimo());
        System.out.println(candle.getMaximo());
        System.out.println(candle.getVolume());
    }
}
