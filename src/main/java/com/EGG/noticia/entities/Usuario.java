package com.EGG.noticia.entities;

import java.util.Date;
import com.EGG.noticia.enums.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    private String email;
    private String password;
    private Date fechaAlta;
    private Rol rol;
    private Boolean activo;

}
