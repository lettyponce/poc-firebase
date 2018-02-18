package com.example.lfuzzatti.poc1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pet {

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("raca")
    @Expose
    private String raca;

    @SerializedName("sexo")
    @Expose
    private String sexo;

    @SerializedName("dataNasc")
    @Expose
    private String dataNasc;

    @SerializedName("proprietario")
    @Expose
    private String proprietario;

    public Pet() {
    }

    public Pet(String nome, String raca, String sexo, String dataNasc, String proprietario) {
        this.nome = nome;
        this.raca = raca;
        this.sexo = sexo;
        this.dataNasc = dataNasc;
        this.proprietario = proprietario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }
}
