package com.example.omega;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.omega.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private ElementsViewModel elementsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        elementsViewModel = new ViewModelProvider(requireActivity()).get(ElementsViewModel.class);

        Button calcularButton = view.findViewById(R.id.calcular);
        calcularButton.setOnClickListener(v -> agregarElemento());

        return view;
    }

    private void agregarElemento() {
        String mes = binding.mes.getText().toString();
        String ano = binding.ano.getText().toString();
        String cuotaStr = binding.cuota.getText().toString();

        if (!mes.isEmpty() && !ano.isEmpty() && !cuotaStr.isEmpty()) {
            double cuota = Double.parseDouble(cuotaStr);
            Element newElement = new Element(mes, ano, cuota);

            elementsViewModel.insertar(newElement);

            binding.mes.setText("");
            binding.ano.setText("");
            binding.cuota.setText("");
        }
    }
}