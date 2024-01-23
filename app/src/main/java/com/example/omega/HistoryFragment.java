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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omega.databinding.FragmentHistoryBinding;
import com.example.omega.databinding.ViewholderElementBinding;

import java.util.List;

public class HistoryFragment extends Fragment {

    private ElementsAdapter elementsAdapter;
    static RecyclerView recyclerView;

    FragmentHistoryBinding binding;

    static class ElementViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderElementBinding binding;

        public ElementViewHolder(ViewholderElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class ElementsAdapter extends RecyclerView.Adapter<ElementViewHolder> {

        List<Element> elements;

        @NonNull
        @Override
        public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementViewHolder(ViewholderElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
            if (elements != null && position < elements.size()) {
                Element element = elements.get(position);
                holder.binding.mes.setText(element.mes);
                holder.binding.ano.setText(""+element.ano);
                holder.binding.cuota.setText(String.valueOf(element.cuota));
            }
        }

        @Override
        public int getItemCount() {
            return elements != null ? elements.size() : 0;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void establecerLista(List<Element> elements) {
            this.elements = elements;
            notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elementsAdapter = new ElementsAdapter();
        ElementsViewModel elementsViewModel = new ViewModelProvider(requireActivity()).get(ElementsViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(elementsAdapter);

        elementsViewModel.obtener().observe(getViewLifecycleOwner(), elements -> elementsAdapter.establecerLista(elements));
    }
}