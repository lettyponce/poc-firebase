package com.example.lfuzzatti.poc1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicamentos {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("medicamento")
    @Expose
    private String medicamento;

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("horario")
    @Expose
    private String horario;

    @SerializedName("observacao")
    @Expose
    private String observacao;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("lemebrete")
    @Expose
    private int lemebrete;

    public Medicamentos() {
    }

    public Medicamentos(String id, String medicamento, String data, String horario, String observacao, int status, int lemebrete) {
        this.id = id;
        this.medicamento = medicamento;
        this.data = data;
        this.horario = horario;
        this.observacao = observacao;
        this.status = status;
        this.lemebrete = lemebrete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLemebrete() {
        return lemebrete;
    }

    public void setLemebrete(int lemebrete) {
        this.lemebrete = lemebrete;
    }
}
