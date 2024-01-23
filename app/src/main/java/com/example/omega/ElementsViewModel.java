package com.example.omega;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ElementsViewModel extends AndroidViewModel {

    ElementsRepository elementsRepository;

    public ElementsViewModel(@NonNull Application application) {
        super(application);

        elementsRepository = new ElementsRepository(application);
    }

    LiveData<List<Element>> obtener(){
        return elementsRepository.obtener();
    }

    void insertar(Element element){
        elementsRepository.insertar(element);
    }
}