package com.example.sarav.radius;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView text,text1;
    private ImageView img;
    List<listview> adList;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlist);

        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        img = findViewById(R.id.img);
        adList = new ArrayList<>();
        listView = findViewById(R.id.list);


        JSONObject obj = new JSONObject();

            String URL = "https://raw.githubusercontent.com/iranjith4/radius-intern-mobile/master/users.json";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            final String mRequestBody = obj.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
                try {
                    JSONObject res = new JSONObject(response);

                        JSONArray arr = res.getJSONArray("results");

                        for(int j=0;j<arr.length();j++){
                            JSONObject jo = arr.getJSONObject(j);
                            JSONObject jn = jo.getJSONObject("name");
                            JSONObject jd = jo.getJSONObject("dob");
                            JSONObject jp = jo.getJSONObject("picture");

                            String name ;
                            name = jn.getString("title") + " " + jn.getString("first") + " " + jn.getString("last");

                            adList.add(new listview(jp.getString("large"), name,jd.getString("age")));

                        }
                    Adapter adapter = new Adapter(this, R.layout.activity_main, adList);

                    listView.setAdapter(adapter);

                    ListAdapter listadp = listView.getAdapter();
                    if (listadp != null) {
                        int totalHeight = 0;
                        for (int i = 0; i < listadp.getCount(); i++) {
                            View listItem = listadp.getView(i, null, listView);
                            listItem.measure(0, 0);
                            totalHeight += listItem.getMeasuredHeight();
                        }
                        ViewGroup.LayoutParams params = listView.getLayoutParams();
                        params.height = totalHeight + (listView.getDividerHeight() * (listadp.getCount() - 1));
                        listView.setLayoutParams(params);
                        listView.requestLayout();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, error -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show()) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }


                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
////                        Map<String, String> headers = new HashMap<>();
////                        headers.put("x-auth",tok);
////                        return headers;
//                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {

                        try {
                            responseString = new String(response.data, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);

    }


}

