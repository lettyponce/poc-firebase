package com.example.lfuzzatti.poc1.service;

import com.example.lfuzzatti.poc1.model.Endereco;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostmonService {

    @GET("{cep}")
    Call<Endereco> getEndereco(@Path("cep") String cep);
}
