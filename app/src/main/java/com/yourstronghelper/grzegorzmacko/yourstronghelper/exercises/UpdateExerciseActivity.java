package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;
    private EditText editTextQty;

    private Spinner typeExercise;
    private Button addExercise;
    private EditText sersiesExercise, quantityExercise, nameExcercise;
    String type;


    private FirebaseFirestore db;

    private Exercise exer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise);

        exer = (Exercise) getIntent().getSerializableExtra("exercise");
        db = FirebaseFirestore.getInstance();

       /* editTextName = findViewById(R.id.edittext_name);
        editTextBrand = findViewById(R.id.edittext_brand);
        editTextDesc = findViewById(R.id.edittext_desc);
        editTextPrice = findViewById(R.id.edittext_price);
        editTextQty = findViewById(R.id.edittext_qty);
*/
        nameExcercise = (EditText)findViewById(R.id.editTextNameExercise);
        typeExercise = (Spinner)findViewById(R.id.spinnerType);
        addExercise = (Button)findViewById(R.id.buttonAddExercise);
        sersiesExercise = (EditText)findViewById(R.id.editTextSeries);
        quantityExercise = (EditText)findViewById(R.id.editTextQuantity);

        /*editTextName.setText(product.getName());
        editTextBrand.setText(product.getBrand());
        editTextDesc.setText(product.getDescription());
        editTextPrice.setText(String.valueOf(product.getPrice()));
        editTextQty.setText(String.valueOf(product.getQty()));*/

        nameExcercise.setText(exer.getName()); //nameExcercise.getText().toString().trim();
       //typeExercise.setT = type;
        sersiesExercise.setText(String.valueOf(exer.getSeries())); //= Integer.parseInt( sersiesExercise.getText().toString().trim() );
       quantityExercise.setText(String.valueOf(exer.getQuantity()));// = Integer.parseInt( quantityExercise.getText().toString().trim() );


        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
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
                            Toast.makeText(UpdateExerciseActivity.this, "Zaktualizowano dane", Toast.LENGTH_LONG).show();
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