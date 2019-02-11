package com.laapp.lookingaround;

import android.widget.EditText;
import android.widget.RatingBar;

import com.laapp.lookingaround.model.Banco;

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final RatingBar campoNota;

    public FormularioHelper(FormularioActivity activity ) {
        campoNome = activity.findViewById(R.id.nome_banco);
        campoEndereco = activity.findViewById(R.id.endereco_banco);
        campoNota = activity.findViewById(R.id.nota_banco);
    }

    public Banco pegaBanco() {
        Banco banco = new Banco();
        banco.setNome(campoNome.getText().toString());
        banco.setEndereco(campoEndereco.getText().toString());
        banco.setNota(Double.valueOf(campoNota.getProgress()));
        return banco;
    }
}
