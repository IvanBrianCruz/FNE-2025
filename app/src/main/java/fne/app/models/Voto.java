package fne.app.models;

import jakarta.persistence.*;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // --- CAMPOS MODIFICADOS ---
    // Se eliminó el campo "puntaje" y se reemplazó por tres puntajes individuales.
    @Column
    private Double puntaje1;

    @Column
    private Double puntaje2;

    @Column
    private Double puntaje3;

    // Nuevo campo para almacenar la suma de los 3 puntajes de este voto.
    @Column
    private Double subtotal;

    // --- RELACIONES (Sin cambios) ---
    @ManyToOne
    @JoinColumn(name = "jurado_id")
    private Jurado jurado;

    @ManyToOne
    @JoinColumn(name = "candidata_id")
    private Candidata candidata;
    
    @Column
    private boolean estado = true;

    /**
     * Este método se ejecuta automáticamente antes de guardar (Persist) o actualizar (Update)
     * la entidad en la base de datos.
     * Se encarga de sumar los tres puntajes para calcular y establecer el subtotal.
     */
    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        double p1 = (this.puntaje1 != null) ? this.puntaje1 : 0.0;
        double p2 = (this.puntaje2 != null) ? this.puntaje2 : 0.0;
        double p3 = (this.puntaje3 != null) ? this.puntaje3 : 0.0;
        this.subtotal = p1 + p2 + p3;
    }

    // --- Getters y Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPuntaje1() {
        return puntaje1;
    }

    public void setPuntaje1(Double puntaje1) {
        this.puntaje1 = puntaje1;
    }

    public Double getPuntaje2() {
        return puntaje2;
    }

    public void setPuntaje2(Double puntaje2) {
        this.puntaje2 = puntaje2;
    }

    public Double getPuntaje3() {
        return puntaje3;
    }

    public void setPuntaje3(Double puntaje3) {
        this.puntaje3 = puntaje3;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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
