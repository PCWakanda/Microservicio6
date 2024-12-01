package org.example.microservicio6;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "urgencias")
public class Urgencias {
    @Id
    private int id;
    private int constantesVitales;
    private int pulso;
    private int azucar;
    private int ticksEnUrgencias;

    public Urgencias() {
        // No-arg constructor
    }

    public Urgencias(int id, int constantesVitales, int pulso, int azucar, int ticksEnUrgencias) {
        this.id = id;
        this.constantesVitales = constantesVitales;
        this.pulso = pulso;
        this.azucar = azucar;
        this.ticksEnUrgencias = ticksEnUrgencias;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConstantesVitales() {
        return constantesVitales;
    }

    public void setConstantesVitales(int constantesVitales) {
        this.constantesVitales = constantesVitales;
    }

    public int getPulso() {
        return pulso;
    }

    public void setPulso(int pulso) {
        this.pulso = pulso;
    }

    public int getAzucar() {
        return azucar;
    }

    public void setAzucar(int azucar) {
        this.azucar = azucar;
    }

    public int getTicksEnUrgencias() {
        return ticksEnUrgencias;
    }

    public void setTicksEnUrgencias(int ticksEnUrgencias) {
        this.ticksEnUrgencias = ticksEnUrgencias;
    }
}