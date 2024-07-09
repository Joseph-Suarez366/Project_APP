package com.example.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class EnvioActivity extends AppCompatActivity {

    TextView TxtMl;
    EditText EtxtCorreo;
    Button BtnAdd, btnEliminar;
    private List<String> emails = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio);

        TxtMl = findViewById(R.id.TxtMl);
        EtxtCorreo = findViewById(R.id.EtxCorreo);
        BtnAdd = findViewById(R.id.BtnAdd);
        btnEliminar = findViewById(R.id.btnEliminar);

        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLastEmail();
            }
        });


    }



    private void Add() {

        String email = EtxtCorreo.getText().toString().trim();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emails.add(email); // Agrega el email a la lista
            EtxtCorreo.setText(""); // Limpia el EditText
            updateEmailsTextView(); // Actualiza el TextView
        } else {
            EtxtCorreo.setError("Invalid email");
        }
        btnEliminar.setVisibility(emails.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void deleteLastEmail() {
        if (!emails.isEmpty()) {
            emails.remove(emails.size() - 1); // Elimina el último email agregado
            updateEmailsTextView(); // Actualiza el TextView
        }

        // Muestra u oculta el botón de eliminación según si hay correos para eliminar
        btnEliminar.setVisibility(emails.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void updateEmailsTextView() {
        StringBuilder sb = new StringBuilder();
        for (String email  : emails) {
            sb.append(email).append("\n"); // Agrega cada email a StringBuilder

        }
        TxtMl.setText(sb.toString()); // Muestra los emails en el TextView
}


}