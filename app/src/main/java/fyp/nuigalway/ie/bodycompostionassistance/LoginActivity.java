package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText etEmailLogin = (EditText) findViewById(R.id.etEmailLogin);
        final EditText etPasswordLogin = (EditText) findViewById(R.id.etPasswordLogin);

        final Button bLogin = (Button) findViewById(R.id.bLogin);

        final TextView SignUpLink = (TextView) findViewById(R.id.tvSignUp);

        SignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(signupIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    final String email = etEmailLogin.getText().toString();
                    final String password = etPasswordLogin.getText().toString();

                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");



                            if (success) {

                                String firstname = jsonResponse.getString("firstname");
                                String surname = jsonResponse.getString("surname");


                                Intent intent = new Intent(LoginActivity.this, CreateMealActivity.class);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }


}
