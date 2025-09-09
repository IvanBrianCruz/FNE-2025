package fne.app.models;

import jakarta.persistence.*;



@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double puntaje; // Puntaje otorgado por el jurado

    // Relación ManyToOne: un voto pertenece a un jurado
    @ManyToOne
    @JoinColumn(name = "jurado_id")
    private Jurado jurado;

    // Relación ManyToOne: un voto es para una candidata
    @ManyToOne
    @JoinColumn(name = "candidata_id")
    private Candidata candidata;
    
    @Column
    private boolean estado = true; // Agregado por defecto en true

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public Jurado getJurado() {
        return jurado;
    }

    public void setJurado(Jurado jurado) {
        this.jurado = jurado;
    }

    public Candidata getCandidata() {
        return candidata;
    }

    public void setCandidata(Candidata candidata) {
        this.candidata = candidata;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
