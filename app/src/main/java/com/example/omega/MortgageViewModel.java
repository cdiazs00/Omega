package com.example.omega;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MortgageViewModel extends AndroidViewModel {

    private final Executor executor;
    private final MortgageSimulator simulador;

    MutableLiveData<Double> cuota = new MutableLiveData<>();
    static MutableLiveData<Double> errorCapital = new MutableLiveData<>();
    static MutableLiveData<Integer> errorPlazos = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public MortgageViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new MortgageSimulator();
    }

    public void calcular(double capital, int plazo) {
        final MortgageSimulator.Solicitud solicitud = new MortgageSimulator.Solicitud(capital, plazo);

        executor.execute(() -> {
            try {
                simulador.calcular(solicitud, new MortgageSimulator.Callback() {
                    @Override
                    public void cuandoEsteCalculadaLaCuota(double cuotaResultante) {
                        errorCapital.postValue(null);
                        errorPlazos.postValue(null);
                        cuota.postValue(cuotaResultante);
                    }

                    @Override
                    public void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo) {
                        errorCapital.postValue(capitalMinimo);
                    }

                    @Override
                    public void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo) {
                        errorPlazos.postValue(plazoMinimo);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }
                });
            } catch (Exception e) {
                Log.e("MiHipotecaViewModel", "Error al calcular", e);
            }
        });
    }
}