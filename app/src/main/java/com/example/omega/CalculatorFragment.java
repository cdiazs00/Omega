package com.example.omega;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.omega.databinding.FragmentCalculatorBinding;

public class CalculatorFragment extends Fragment {
    private FragmentCalculatorBinding binding;
    private MortgageViewModel MortgageViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MortgageViewModel = new ViewModelProvider(this).get(MortgageViewModel.class);
    }

    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCalculatorBinding.inflate(inflater, container, false)).getRoot();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.calcular.setOnClickListener(view1 -> {
            double capital = Double.parseDouble(binding.capital.getText().toString());
            int plazo = Integer.parseInt(binding.plazo.getText().toString());
            MortgageViewModel.calcular(capital, plazo);
        });

        MortgageViewModel.cuota.observe(getViewLifecycleOwner(), cuota -> binding.cuota.setText(String.format("%.2f", cuota)));

        com.example.omega.MortgageViewModel.errorCapital.observe(getViewLifecycleOwner(), capitalMinimo -> {
            if (capitalMinimo != null) {
                binding.capital.setError("El capital no puede ser inferior a " + capitalMinimo + " euros");
            } else {
                binding.capital.setError(null);
            }
        });

        com.example.omega.MortgageViewModel.errorPlazos.observe(getViewLifecycleOwner(), plazoMinimo -> {
            if (plazoMinimo != null) {
                binding.plazo.setError("El plazo no puede ser inferior a " + plazoMinimo + " años");
            } else {
                binding.plazo.setError(null);
            }
        });

        MortgageViewModel.calculando.observe(getViewLifecycleOwner(), calculando -> {
            if (calculando) {
                binding.calcular.setVisibility(View.VISIBLE);
                binding.cuota.setVisibility(View.GONE);
            } else {
                binding.calcular.setVisibility(View.VISIBLE);
                binding.cuota.setVisibility(View.VISIBLE);
            }
        });
    }
}