package com.example.lfuzzatti.poc1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vacina {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("dataAplicacao")
    @Expose
    private String dataAplicacao;

    @SerializedName("vacina")
    @Expose
    private String vacina;

    @SerializedName("dataProximaAplicacao")
    @Expose
    private String dataProxAplicacao;

    @SerializedName("localAplicacao")
    @Expose
    private String localAplicacao;

    @SerializedName("medico")
    @Expose
    private String medico;

    @SerializedName("observacao")
    @Expose
    private String observacao;

    public Vacina() {
    }

    public Vacina(String id, String dataAplicacao, String vacina, String dataProxAplicacao, String localAplicacao, String medico, String observacao) {
        this.id = id;
        this.dataAplicacao = dataAplicacao;
        this.vacina = vacina;
        this.dataProxAplicacao = dataProxAplicacao;
        this.localAplicacao = localAplicacao;
        this.medico = medico;
        this.observacao = observacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(String dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public String getVacina() {
        return vacina;
    }

    public void setVacina(String vacina) {
        this.vacina = vacina;
    }

    public String getDataProxAplicacao() {
        return dataProxAplicacao;
    }

    public void setDataProxAplicacao(String dataProxAplicacao) {
        this.dataProxAplicacao = dataProxAplicacao;
    }

    public String getLocalAplicacao() {
        return localAplicacao;
    }

    public void setLocalAplicacao(String localAplicacao) {
        this.localAplicacao = localAplicacao;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Vacina{" +
                "id='" + id + '\'' +
                ", dataAplicacao='" + dataAplicacao + '\'' +
                ", vacina='" + vacina + '\'' +
                ", dataProxAplicacao='" + dataProxAplicacao + '\'' +
                ", localAplicacao='" + localAplicacao + '\'' +
                ", medico='" + medico + '\'' +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
