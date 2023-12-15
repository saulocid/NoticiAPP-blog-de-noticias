package com.EGG.noticia.entities;

import java.util.ArrayList;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Periodista extends Usuario{
    
    private ArrayList<Noticia> misNoticias;
    private Integer sueldoMensual;

}
