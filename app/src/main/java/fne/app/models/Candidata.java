package fne.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;

import java.util.List;

@Entity
public class Candidata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private String fotoURL;
    @Column
    private String colegioSecundario; // Colegio secundario
    @Column
    private Double puntajeTotal; // Campo para almacenar el puntaje total

    // Relación OneToMany: una candidata puede tener muchos votos
    @OneToMany(mappedBy = "candidata")
    private List<Voto> votos;
    
    @Column
    private boolean estado = true; // Agregado por defecto en true

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFotoURL() {
        return fotoURL;
    }

    public void setFotoURL(String fotoURL) {
        this.fotoURL = fotoURL;
    }

    public String getColegioSecundario() {
        return colegioSecundario;
    }

    public void setColegioSecundario(String colegioSecundario) {
        this.colegioSecundario = colegioSecundario;
    }

    public Double getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(Double puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }
    
    public boolean isEstado() {
        return estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
