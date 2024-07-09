package com.example.app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReporteActivity extends AppCompatActivity {

    EditText EtxtName, EtxtAge, EtxtSex, EtxtSexo, Etxtedad;
    Button BtnSave, BtnFin, BtnBorrar;

    String Nombre, Age, Sex, Sexo, Edad;

    int contadorRep=0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference miColeccion = db.collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);


        EtxtName = findViewById(R.id.EtxtName);
        EtxtAge = findViewById(R.id.EtxtAge);
        EtxtSex = findViewById(R.id.EtxtSex);
        EtxtSexo = findViewById(R.id.EtxtSexo);
        Etxtedad = findViewById(R.id.Etxtedad);

        BtnSave = findViewById(R.id.BtnSave);
        BtnFin = findViewById(R.id.BtnFin);
        BtnBorrar = findViewById(R.id.BtnBorrar);


        /*SharedPreferences preferences = getSharedPreferences("Datos", Context.MODE_PRIVATE);
        EtxtName.setText(preferences.getString("name", ""));
        EtxtAge.setText(preferences.getString("age", ""));
        EtxtSex.setText(preferences.getString("sex", ""));
        EtxtSexo.setText(preferences.getString("sexo", ""));
        Etxtedad.setText(preferences.getString("edad", ""));*/


        String ID_ACTIVIDAD_VP = getIntent().getStringExtra("ID_ACTIVIDAD");

        // Obtener el Intent que inició esta actividad
        Intent intent = getIntent();

        // Recuperar el ID único pasado como extra
        int idActividad = intent.getIntExtra("ID_ACTIVIDAD", -1); // -1 es un valor por defecto


        //Verifico si hay algo en el ID_ACTIVIDAD_VP para obtener los datos de la base de datos
        if(ID_ACTIVIDAD_VP != null){
            //Le paso lo que hay en ID_ACTIVIDAD_VP a mi funcion VistaPre
            VistaPre(ID_ACTIVIDAD_VP);
        }

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ContadorHome=getIntent().getIntExtra("ContadorHome", 0);
                Log.d(TAG, "ContadorHome: " + ContadorHome);
                contadorRep = ContadorHome;

                contadorRep=contadorRep+1;
                Log.d(TAG, "ContadorRep: " + contadorRep);

                Nombre = EtxtName.getText().toString();
                Age = EtxtAge.getText().toString();
                Sex = EtxtSex.getText().toString();
                Sexo =EtxtSexo.getText().toString();
                Edad = Etxtedad.getText().toString();



                //Verifico que no haya campos vacios
                if( !Nombre.isEmpty() & !Age.isEmpty() & !Sex.isEmpty() & !Sexo.isEmpty() & !Edad.isEmpty()){

           /* SharedPreferences preferences = getSharedPreferences("Datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name", EtxtName.getText().toString());
            editor.putString("age", EtxtAge.getText().toString());
            editor.putString("sex", EtxtSex.getText().toString());
            editor.putString("sexo", EtxtSexo.getText().toString());
            editor.putString("edad", Etxtedad.getText().toString());
            editor.apply();*/

                    /*-----------------------------------------------------------------------------------------------------------------------------*/
                    //Realizando pruebas con la base de datos Firebase Cloud Firestore
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name", EtxtName.getText().toString());
                    user.put("Age", EtxtAge.getText().toString());
                    user.put("Sex", EtxtSex.getText().toString());
                    user.put("Sexo", EtxtSexo.getText().toString());
                    user.put("Edad", Etxtedad.getText().toString());

                    String idPerzonalizado  = EtxtName.getText().toString() + "LO";                //Genera un ID Personalizado
                    DocumentReference nuevoDocumento = miColeccion.document(idPerzonalizado);;  //Referencia al documento

                    // Add a new document with a generated ID
            /*db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(ReporteActivity.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                            String clave = documentReference.getId();
                            Intent intent = new Intent(ReporteActivity.this, HomeActivity.class);
                            //Intent intent = new Intent(this, HomeActivity.class);
                            intent.putExtra("namee", EtxtName.getText().toString());
                            intent.putExtra("age", EtxtAge.getText().toString());
                            intent.putExtra("sex", EtxtSex.getText().toString());
                            intent.putExtra("sexo", EtxtSexo.getText().toString());
                            intent.putExtra("edad", Etxtedad.getText().toString());
                            intent.putExtra("ID_ACTIVIDAD", clave );
                            intent.putExtra("VP", "uno");

                            startActivity(intent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });*/

                    //Envia los datos a la base de datos Firestore
                    nuevoDocumento.set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Log.d(TAG, "DocumentSnapshot added with ID: " + idPerzonalizado);
                                    Toast.makeText(ReporteActivity.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                                    //String clave = documentReference.getId();
                                    Intent intent = new Intent(ReporteActivity.this, HomeActivity.class);
                                    //Intent intent = new Intent(this, HomeActivity.class);
                                    intent.putExtra("namee", EtxtName.getText().toString());
                                    intent.putExtra("age", EtxtAge.getText().toString());
                                    intent.putExtra("sex", EtxtSex.getText().toString());
                                    intent.putExtra("sexo", EtxtSexo.getText().toString());
                                    intent.putExtra("edad", Etxtedad.getText().toString());
                                    intent.putExtra("ID_ACTIVIDAD", idPerzonalizado );
                                    intent.putExtra("VP", "uno");
                                    intent.putExtra("contadorRep", contadorRep);

                                    startActivity(intent);

                                    // Cierra la actividad actual

                                    //finish();

                                    //Log.d(TAG, "finish() ");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Ocurrió un error al guardar el documento
                                    // Maneja el error según tus necesidades
                                }
                            });

                    /*------------------------------------------------------------------------------------------------------------------------------------*/


                } else{
                    Toast.makeText(ReporteActivity.this, "Complete todos los campos", Toast.LENGTH_LONG).show();}

            }
        });

        BtnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EtxtName.setText("");
                EtxtAge.setText("");
                EtxtSex.setText("");
                EtxtSexo.setText("");
                Etxtedad.setText("");

            }
        });

        BtnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });


    }


    public void next(){
        Intent intent = new Intent(this, EnvioActivity.class);
        startActivity(intent);
    }

    public void VistaPre(String ID_ACTIVIDAD){  //Funcion para obtener los datos de la base de datos Firestore
        //Obtengo un solo dato de la base de datos Firestore
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
                        EtxtName.setText(document.getString("Name"));
                        EtxtAge.setText(document.getString("Age"));
                        EtxtSex.setText(document.getString("Sex"));
                        EtxtSexo.setText(document.getString("Sexo"));
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