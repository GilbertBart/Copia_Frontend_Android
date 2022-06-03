package edu.upc.eetac.dsa;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import edu.upc.eetac.dsa.databinding.ActivityRegisterBinding;
import edu.upc.eetac.dsa.models.*;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultActivity extends AppCompatActivity {


    private TextView dateText, titleText, messageText, senderText;
    private Button consultBtn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        dateText = findViewById(R.id.consult_date);
        titleText =  findViewById(R.id.consult_title);
        messageText = findViewById(R.id.message);
        senderText = findViewById(R.id.sender);
        consultBtn =  findViewById(R.id.consultButton);
        apiInterface = Api.getClient();
        validateSender();
        consultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateSender()==true) {
                    consult();
                }
                else
                    Toast.makeText(getApplicationContext(), "Incorrect format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateSender() {
        String sender = senderText.getText().toString().trim();

        if (sender.isEmpty()){
            senderText.setError("Field can not be empty");
            return false;
        }

        else
            return true;
    }


    private void consult() {

        String date = dateText.getText().toString().trim();
        String title = titleText.getText().toString().trim();
        String message = messageText.getText().toString().trim();
        String sender = senderText.getText().toString().trim();

        apiInterface.consult(new Consult(date,title,message,sender)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String c = Integer.toString(response.code());
                Toast.makeText(getApplicationContext(), c + ": " + response.message(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error22", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
