package org.example.microservicio6;

public class Paciente {
    private int id;
    private int constantesVitales;
    private int pulso;
    private int azucar;

    public Paciente(int id, int constantesVitales, int pulso, int azucar) {
        this.id = id;
        this.constantesVitales = constantesVitales;
        this.pulso = pulso;
        this.azucar = azucar;
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
}