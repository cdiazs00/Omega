package com.example.omega;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Element {
    @PrimaryKey(autoGenerate = true)
    int id;
    String mes;
    int ano;
    double cuota;

    public Element() {
    }

    public Element(String mes, String ano, double cuota) {
        this.mes = mes;
        this.ano = Integer.parseInt(ano);
        this.cuota = cuota;
    }
}