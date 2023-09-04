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
                solicitarVoto();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void mostrarMensaje(String mensaje) {
        textViewResultado.setText(mensaje);
    }

    // Metodo para solicitar voto a los usuarios
    private void solicitarVoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Voto del Elector");
        builder.setMessage("Ingrese el voto del elector (1, 2 o 3):");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            int voto = Integer.parseInt(input.getText().toString());
            switch (voto) {
                case 1:
                    votosCandidato1++;
                    break;
                case 2:
                    votosCandidato2++;
                    break;
                case 3:
                    votosCandidato3++;
                    break;
                default:
                    mostrarMensaje("Voto inválido.");
                    break;
            }

            if (electoresRestantes > 0) {
                solicitarEdad();
            } else {
                mostrarResultado();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private int determinarGanador() {
        int maxVotos = Math.max(votosCandidato1, Math.max(votosCandidato2, votosCandidato3));
        if (maxVotos == votosCandidato1) {
            return 1;
        } else if (maxVotos == votosCandidato2) {
            return 2;
        } else {
            return 3;
        }
    }

    private int obtenerVotos(int candidato) {
        switch (candidato) {
            case 1:
                return votosCandidato1;
            case 2:
                return votosCandidato2;
            case 3:
                return votosCandidato3;
            default:
                return 0;
        }
    }

    private void mostrarResultado() {
        int candidatoGanador = determinarGanador();
        String mensajeResultado = "El Candidato #" + candidatoGanador + " es elegido con " + obtenerVotos(candidatoGanador) + " votos.";
        textViewResultado.setText(mensajeResultado);
    }
}