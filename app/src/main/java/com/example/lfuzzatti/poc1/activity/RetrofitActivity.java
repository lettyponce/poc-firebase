package com.example.lfuzzatti.poc1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lfuzzatti.poc1.R;
import com.example.lfuzzatti.poc1.model.Endereco;
import com.example.lfuzzatti.poc1.service.PostmonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    private EditText edCep;
    private Button btSend;
    private TextView tvDados;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        edCep = (EditText) findViewById(R.id.edCep);
        btSend = (Button) findViewById(R.id.btSend);
        tvDados = (TextView) findViewById(R.id.tvDados);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obterEndereco(edCep.getText().toString());
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.postmon.com.br/v1/cep/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private void obterEndereco(String cep) {

        PostmonService service = retrofit.create(PostmonService.class);

        Call<Endereco> call = service.getEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();

                    String dadosEndereco = "Cidade: "+ endereco.getCidade() + "\n" +
                            "Bairro: "+ endereco.getBairro() + "\n" +
                            "Logradouro: "+ endereco.getLogradouro();

                    tvDados.setText(dadosEndereco);
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this,
                        "Não foi possivel realizar a requisicao",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
