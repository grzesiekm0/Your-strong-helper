package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;

public class UpdateExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner typeExercise;
    private Button addExercise;
    private EditText sersiesExercise, quantityExercise, nameExcercise;
    String type;

    private static String TAG = "UpdateExerciseActivity";

    private FirebaseFirestore db;

    private Exercise exer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise);

        exer = (Exercise) getIntent().getSerializableExtra("exercise");
        db = FirebaseFirestore.getInstance();

        nameExcercise = (EditText)findViewById(R.id.editTextNameExercise);
        typeExercise = (Spinner)findViewById(R.id.spinnerType);
        addExercise = (Button)findViewById(R.id.buttonAddExercise);
        sersiesExercise = (EditText)findViewById(R.id.editTextSeries);
        quantityExercise = (EditText)findViewById(R.id.editTextQuantity);


        nameExcercise.setText(exer.getName());
       //typeExercise.setT = type;
        sersiesExercise.setText(String.valueOf(exer.getSeries()));
       quantityExercise.setText(String.valueOf(exer.getQuantity()));


        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);

        //elementy w spinerze do wyświetlenia
        String[] elementy = {"Plecy", "Klata", "Barki", "Biceps", "Triceps"};
        //adapter który ustawia elementy w spinerze
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementy);
        //ustawienie adaptera
        typeExercise.setAdapter(adapter);
        //ustawienie sluchacza
        typeExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //przysloniecie metody wybierajacej pozycje
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {
                // ta metoda wykonuje się za każdym razem, gdy zostanie wybrany jakiś element z naszej listy
                switch((int)position)  //tutaj musimy przerzutować wartośc position na int, bo jest ona typu long, a typu long nie można używać do instrukcji switch
                {
                    case 0:
                        //plecy
                        //exer.setType('P');
                        type = "P";
                        break;
                    case 1:
                        //klata
                        //exer.setType('K');
                        type = "K";
                        break;
                    case 2:
                        //barki
                        //exer.setType('B');
                        type = "B";
                        break;
                    case 3:
                        //biceps
                        //exer.setType('C');
                        type = "C";
                        break;
                    case 4:
                        //triceps
                        //exer.setType('T');
                        type = "T";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // ta metoda wykonuje sie gdy lista zostanie wybrana, ale nie zostanie wybrany żaden element z listy

            }

        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, ExerciseActivity.class));
        return;
    }

    private boolean hasValidationErrors(String name, String type, int series, int quantity) {
        if (name.isEmpty()) {
            nameExcercise.setError("Name required");
            nameExcercise.requestFocus();
            return true;
        }

        if (type.isEmpty()) {
            ((TextView)typeExercise.getSelectedView()).setError("Error message");
            typeExercise.requestFocus();
            return true;
        }

        if (series == 0) {
            sersiesExercise.setError("Description required");
            sersiesExercise.requestFocus();
            return true;
        }

        if (quantity == 0) {
            quantityExercise.setError("Price required");
            quantityExercise.requestFocus();
            return true;
        }
        return false;
    }


    private void UpdateExercise() {
        String nameUpdate = nameExcercise.getText().toString().trim();
        String typeUpdate = type;
        int seriesUpdate = Integer.parseInt( sersiesExercise.getText().toString().trim() );
        int quantityUpdate = Integer.parseInt( quantityExercise.getText().toString().trim() );

        if (!hasValidationErrors(nameUpdate, typeUpdate, seriesUpdate, quantityUpdate)) {

            Exercise p = new Exercise(
                    nameUpdate,
                    typeUpdate,
                    seriesUpdate,
                    quantityUpdate
            );


            db.collection("exercise").document(exer.getId())
                    .update(
                            "name", p.getName(),
                            "type", p.getType(),
                            "series", p.getSeries(),
                            "quantity", p.getQuantity()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                            Toast.makeText(UpdateExerciseActivity.this, "Zaktualizowano dane", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                            Toast.makeText(UpdateExerciseActivity.this, "Coś poszło nie tak", Toast.LENGTH_LONG).show();

                        }
                    });

        }
    }

    private void deleteExercise() {
        db.collection("exercise").document(exer.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateExerciseActivity.this, "Usunięto ćwiczenie", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(UpdateExerciseActivity.this, ExerciseActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                UpdateExercise();
                break;
            case R.id.button_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure about this?");
                builder.setMessage("Deletion is permanent...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteExercise();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
        }
    }
}