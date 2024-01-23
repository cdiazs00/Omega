package com.example.omega;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ElementsRepository {

    Executor executor = Executors.newSingleThreadExecutor();

    ElementsDataBase.ElementsDao elementsDao;

    ElementsRepository(Application application){
        elementsDao = ElementsDataBase.obtenerInstancia(application).obtenerElementsDao();
    }

    LiveData<List<Element>> obtener(){
        return elementsDao.obtener();
    }

    void insertar(Element element){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                elementsDao.insertar(element);
            }
        });
    }
}