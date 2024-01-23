package com.example.omega;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = { Element.class }, version = 1, exportSchema = false)
public abstract class ElementsDataBase extends RoomDatabase {

    private static volatile ElementsDataBase INSTANCIA;
    public abstract ElementsDao obtenerElementsDao();

    static ElementsDataBase obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (ElementsDataBase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context, ElementsDataBase.class, "elements.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCIA;
    }

    @Dao
    interface ElementsDao {
        @Query("SELECT * FROM Element")
        LiveData<List<Element>> obtener();

        @Insert
        void insertar(Element elemento);
    }
}