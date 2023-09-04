package com.example.conteo_electoral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Variables
    private EditText editTextElectores;
    private TextView textViewResultado;
    private int votosCandidato1, votosCandidato2, votosCandidato3;
    private int electoresRestantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciar variables
        editTextElectores = findViewById(R.id.editTextElectores);
        textViewResultado = findViewById(R.id.textViewResultado);
    }

    //Metodo para iniciar las elecciones
    public void iniciarElecciones(View view) {
        votosCandidato1 = 0;
        votosCandidato2 = 0;
        votosCandidato3 = 0;

        // Ingresar cantidad de electores
        int numElectores = Integer.parseInt(editTextElectores.getText().toString());
        electoresRestantes = numElectores;

        ingresarEdades(numElectores);
    }

    //Metodo para convertir edad a entero
    public void iniciarIngresoEdades(View view) {
        int numElectores = Integer.parseInt(editTextElectores.getText().toString());
        ingresarEdades(numElectores);
    }

    //Metodo para ingresar las edades ya convertidas a entero
    private void ingresarEdades(int numElectores) {
        electoresRestantes = numElectores;
        solicitarEdad();
    }

    //Metodo donde se solicita edad
    private void solicitarEdad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edad del Elector");
        builder.setMessage("Ingrese la edad del elector:");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            int edad = Integer.parseInt(input.getText().toString());

            if (edad < 18) {
                mostrarMensaje("El elector es menor de edad.");
            } else {
                electoresRestantes--;
                //solicitarVoto();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void mostrarMensaje(String mensaje) {
        textViewResultado.setText(mensaje);
    }
}