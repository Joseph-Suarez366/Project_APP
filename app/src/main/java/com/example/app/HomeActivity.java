package com.example.app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionRef = db.collection("users");

    TextView Etxtname,Etxtage, Etxtsex, Etxtcqc, Etxtedad, EtxtMultiLine;
    Button BtnLout, BtnReport, BtnBorrar, BtnGet;

    CardView VistaPrevia, VistaPrevia1;

    int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Inicializamos los componentes
        Etxtname = findViewById(R.id.Etxtname);
        Etxtage = findViewById(R.id.Etxtage);
        Etxtsex = findViewById(R.id.Etxtsex);
        Etxtcqc = findViewById(R.id.Etxtcqc);
        Etxtedad = findViewById(R.id.Etxtedad);
        EtxtMultiLine = findViewById(R.id.EtxtMultiLine);

        BtnLout = findViewById(R.id.BtnLout);
        BtnReport = findViewById(R.id.BtnReport);
        BtnBorrar = findViewById(R.id.BtnBorrar);
        BtnGet = findViewById(R.id.BtnGet);

        VistaPrevia = findViewById(R.id.VistaPrevia);
        VistaPrevia1 = findViewById(R.id.VistaPrevia1);

        //Obtenemos los datos enviados desde ReporteActuvity
        /*String name = getIntent().getStringExtra("namee");
        Etxtname.setText(name);

        String age = getIntent().getStringExtra("age");
        Etxtage.setText(age);

        String sex = getIntent().getStringExtra("sex");
        Etxtsex.setText(sex);

        String sexo = getIntent().getStringExtra("sexo");
        Etxtcqc.setText(sexo);

        String edad = getIntent().getStringExtra("edad");
        Etxtedad.setText(edad);*/


        String VP = getIntent().getStringExtra("VP");
        String ID_ACTIVIDAD = getIntent().getStringExtra("ID_ACTIVIDAD");
        Log.d(TAG, "ID_ACTIVIDAD-Home: " + ID_ACTIVIDAD);

        ArrayList<String> miArrayList = new ArrayList<>();
        miArrayList.add(ID_ACTIVIDAD);
        Log.d(TAG, "Arryalist: " + miArrayList.get(0));
        //Log.d(TAG, "Arryalist: " + miArrayList.get(1));

        int ContadorHome;
        ContadorHome = getIntent().getIntExtra("contadorRep", 0);
        //int c = Integer.parseInt(ContadorHome);
        Log.d(TAG, "ContadorHome: " + ContadorHome);
        //Log.d(TAG, "Contadorr: " + c);

        if(ContadorHome == 1 && Objects.equals(VP, "uno")){
            VistaPrevia.setVisibility(View.VISIBLE);
            VistaPre(ID_ACTIVIDAD);

        }

        if(ContadorHome > 1){
            Log.d(TAG, "ContadorHome: " + ContadorHome);
            VistaPrevia1.setVisibility(View.VISIBLE);
            VistaPre(ID_ACTIVIDAD);
        }

        //Eventos onClick, para saber que hacer al momento de dale click a un boton
        VistaPrevia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rep1(ID_ACTIVIDAD);
            }
        });

        BtnLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomeActivity.this, "Sesion Cerrada!!", Toast.LENGTH_SHORT).show();
                Logout();
            }
        });

        BtnReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                if(VistaPrevia.getVisibility() == View.VISIBLE) {

                    // Crear un nuevo Intent para la misma actividad
                    Intent intent = new Intent(HomeActivity.this, ReporteActivity.class);
                    // Pasar el ID único como extra en el Intent
                    intent.putExtra("ID_ACTIVIDAD", contador);
                    //Enviamos el contadorhome a reporte activity
                    intent.putExtra("ContadorHome", ContadorHome);

                    Toast.makeText(HomeActivity.this, "Reporte generado!!" + contador, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "If_ID_ACTIVIDAD: " + contador);

                    //inicia la actividad
                    startActivity(intent);


                }else{
                // Pasar el ID único como extra en el Intent
                Intent intent = new Intent(HomeActivity.this, ReporteActivity.class);

                Toast.makeText(HomeActivity.this, "Reporte generado!!" + contador, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "else_ID_ACTIVIDAD: " + contador);

                //inicia la actividad
                startActivity(intent);

                }
            }
        });

        //Boton para eliminar los datos del cardview o tarjeya de vistaprevia
        BtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etxtname.setText("");
                Etxtage.setText("");
                Etxtsex.setText("");
                Etxtcqc.setText("");
                Etxtedad.setText("");

                VistaPrevia.setVisibility(View.GONE);

            }
        });

        //Boton para obtener los datos de la base de datos en Firestore
        BtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Obtener un solo dato de la base de datos Firestore
               String claveActividad = getIntent().getStringExtra("ID_ACTIVIDAD");
                Log.d(TAG, "Document Mio data: " + claveActividad);

                if(claveActividad != null){
                    DocumentReference docRef = db.collection("users").document(claveActividad);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    VistaPrevia.setVisibility(View.VISIBLE);
                                    Etxtname.setText(document.getString("Name"));
                                    Etxtage.setText(document.getString("Age"));
                                    Etxtsex.setText(document.getString("Sex"));
                                    Etxtcqc.setText(document.getString("Sexo"));
                                    Etxtedad.setText(document.getString("Edad"));

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }else{
                    Toast.makeText(HomeActivity.this, "No hay documento por obtener.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No such document");
                }
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
                //Obtener TODOS los datos de la base de datos Firestore
              /*  collectionRef.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (DocumentSnapshot document : list) {
                                        // Aquí puedes obtener los datos del documento
                                        //txtMultiLine.setText(document.getId());
                                        Log.d("Firestore", document.getId() + " => " + document.getData());
                                        stringBuilder.append(document.getId()).append("\n\n").toString();

                                    // Asigna la cadena de texto al TextView
                                        EtxtMultiLine.setText(stringBuilder.toString());
                                    }
                                } else {
                                    Log.d("Firestore", "No data found");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Firestore", "Error getting documents: ", e);
                            }
                        }); */
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------


            }
        });

    }
    //Funcion para cerrar cesion
    private void Logout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Funcion para que el CardView(VistaPrevia) vaya al reporte al que le esta haciendo vistapreva
    public void Rep1(String ID_ACTIVIDAD){
        Intent intent = new Intent(this, ReporteActivity.class);
        intent.putExtra("ID_ACTIVIDAD", ID_ACTIVIDAD);
        startActivity(intent);
    }

    public void VistaPre(String ID_ACTIVIDAD){

        Log.d(TAG, "Document Mio data: " + ID_ACTIVIDAD);
        DocumentReference docRef = db.collection("users").document(ID_ACTIVIDAD);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        //VistaPrevia.setVisibility(View.VISIBLE);
                        Etxtname.setText(document.getString("Name"));
                        Etxtage.setText(document.getString("Age"));
                        Etxtsex.setText(document.getString("Sex"));
                        Etxtcqc.setText(document.getString("Sexo"));
                        Etxtedad.setText(document.getString("Edad"));

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}