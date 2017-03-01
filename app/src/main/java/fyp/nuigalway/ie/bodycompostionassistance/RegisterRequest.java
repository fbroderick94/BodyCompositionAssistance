package fyp.nuigalway.ie.bodycompostionassistance;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fergalbroderick on 28/02/2017.
 */

public class RegisterRequest extends StringRequest {


    private static final String REGISTER_REQUEST_URL = "http://danu6.it.nuigalway.ie/fbroderick/Register.php";
    private Map<String, String> params;


    public RegisterRequest(String firstname, String surname, String email, String password, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("surname", surname);
        params.put("email", email);
        params.put("password", password);
    }


    public Map<String, String> getParams()
    {
        return params;

    }
 }
