package com.example.omega;

public class MortgageSimulator {

    public static class Solicitud {
        public double capital;
        public int plazo;

        public Solicitud(double capital, int plazo) {
            this.capital = capital;
            this.plazo = plazo;
        }
    }

    interface Callback {
        void cuandoEsteCalculadaLaCuota(double cuota);
        void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo);
        void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    public void calcular(Solicitud solicitud, Callback callback) {
        callback.cuandoEmpieceElCalculo();

        double interes;
        double capitalMinimo;
        int plazoMinimo;

        try {
            interes = 0.01605;
            capitalMinimo = 1000;
            plazoMinimo = 2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        boolean error = false;
        if (solicitud.capital < capitalMinimo) {
            callback.cuandoHayaErrorDeCapitalInferiorAlMinimo(capitalMinimo);
            error = true;
        }

        if (solicitud.plazo < plazoMinimo) {
            callback.cuandoHayaErrorDePlazoInferiorAlMinimo(plazoMinimo);
            error = true;
        }

        if(!error) {
            callback.cuandoEsteCalculadaLaCuota(solicitud.capital * interes / 12 / (1 - Math.pow(1 + (interes / 12), -solicitud.plazo * 12)));
        }

        callback.cuandoFinaliceElCalculo();
    }
}