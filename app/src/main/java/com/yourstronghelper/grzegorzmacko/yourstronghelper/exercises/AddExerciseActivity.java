package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;

import java.util.HashMap;
import java.util.Map;

public class AddExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddExerciseActivity";
    Spinner typeExercise;
    Button addExercise;
    EditText sersiesExercise, quantityExercise, nameExcercise;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Exercise exer;

    //variables for save
    String name;
    String type;
    int series;
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        nameExcercise = (EditText)findViewById(R.id.editTextNameExercise);
        typeExercise = (Spinner)findViewById(R.id.spinnerType);
        addExercise = (Button)findViewById(R.id.buttonAddExercise);
        sersiesExercise = (EditText)findViewById(R.id.editTextSeries);
        quantityExercise = (EditText)findViewById(R.id.editTextQuantity);

        findViewById(R.id.buttonSaveExercise).setOnClickListener(this);

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


    private boolean hasValidationErrors(String name, String type, int series, int quantity) {
        if (name.isEmpty()) {
            nameExcercise.setError("Wymagana nazwa");
            nameExcercise.requestFocus();
            return true;
        }

        if (type.isEmpty()) {
            ((TextView)typeExercise.getSelectedView()).setError("Wymagany typ");
            typeExercise.requestFocus();
            return true;
        }

        if (series == 0) {
            sersiesExercise.setError("Wymagane serie");
            sersiesExercise.requestFocus();
            return true;
        }

        if (quantity == 0) {
            quantityExercise.setError("Wymagane powtórzenia");
            quantityExercise.requestFocus();
            return true;
        }
        return false;
    }


    private void save(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String nameSave = nameExcercise.getText().toString().trim();
        String typeSave = type;
        int seriesSave = Integer.parseInt( sersiesExercise.getText().toString().trim() );
        int quantitySave = Integer.parseInt( quantityExercise.getText().toString().trim() );

        if (!hasValidationErrors(nameSave, typeSave, seriesSave, quantitySave)) {

            exer = new Exercise(
                    user.getUid(),
                    nameSave,
                    typeSave,
                    seriesSave,
                    quantitySave
            );
            db.collection("exercise")
                    .add(exer)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(AddExerciseActivity.this, "Dodano cwiczenie", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddExerciseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.w(TAG, "Error adding document", e);

                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonSaveExercise:
                save();
                break;
            /*case R.id.textview_view_products:
                startActivity(new Intent(this, ProductsActivity.class));
                break;*/
        }

    }
}
