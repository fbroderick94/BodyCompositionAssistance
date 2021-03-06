package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etFname = (EditText) findViewById(R.id.etFname);
        final EditText etSname = (EditText) findViewById(R.id.etSname);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname = etFname.getText().toString();
                final String surname = etSname.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                               // Toast.makeText(this, getString(R.string.RegistrationSuccessful), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("tagconvertstr", "["+response+"]");
                        }
                    }
                };


                if(!isValidEmail(email))
                {
                    etEmail.setError("Invalid email");
                }
                else if(!isValidPassword(password))
                {
                    etPassword.setError("Invalid password, Must be at least 6 characters");
                }
                else 
                {
                    RegisterRequest registerRequest = new RegisterRequest(firstname, surname, email, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });

    }

    public static boolean isValidPassword(String password)
    {
        if(password!=null && password.length()>5){
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isValidEmail(String email)
    {
        if(email!=null)
        {
        String EMAIL_PATT = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATT);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
        }
        return false;


    }
}
