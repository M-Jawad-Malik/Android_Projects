package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //https://jsonplaceholder.typicode.com/todos/1
    private RequestQueue requestQueue;// Decalred to handle request in case of any sudden abrupt
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        if (InternetConnection.checkConnection(this)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos/1", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try{
                    Log.d("id12", "Response:" + response.getString("title"));
                }catch (JSONException e){
                        e.getStackTrace();
                          }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("id12", "ErrorResponse:" + error.toString());
                }
            });
//            requestQueue.add(jsonObjectRequest);
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("arr","Response:"+response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("arrError","Error:"+error.toString());
                }
            });
            requestQueue.add(jsonArrayRequest);
        } else {

            Toast.makeText(this,"Internet Not Connected",Toast.LENGTH_LONG).show();
        }

    }
}