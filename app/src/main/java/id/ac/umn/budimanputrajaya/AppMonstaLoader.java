package id.ac.umn.budimanputrajaya;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AppMonstaLoader {
    /* The interface which will be notified when the POJOApplication API are downloaded */
    public interface AppMonstaListener<AnyType>{
        void onAppDownloaded(AnyType a);
        void onErrorDownloading(String errorMessage);
    }

    private static AppMonstaLoader appMonstaLoader = null; // Singleton instance of this class
    private RequestQueue queue; // RequestQueue Volley-Library
    private Context context; // Activity context
    private String apiKey = "2344d5bb2eddd7668e080dbcd944f98dbc1012f9";

    private AppMonstaLoader(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    /* Initializing AppMonstaLoader object if null, and return Singleton object */
    public static synchronized AppMonstaLoader getInstance(Context context){
        if(appMonstaLoader == null)
            appMonstaLoader = new AppMonstaLoader(context);
        return appMonstaLoader;
    }

    /* Fetching app data, parameter listener of array of POJO POJOApplication */
    public void getDetailsApps(final AppMonstaListener<ArrayList<POJOApplication>> listener){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2); // 2 hari yang lalu
        calendar.add(Calendar.MONTH, 1); // karena bulannya mulai dari 0, jadi perlu +1
        String date = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);

        String requestUrl = "https://api.appmonsta.com/v1/stores/android/details.json";
        Uri uri = Uri.parse(requestUrl).buildUpon()
                .appendQueryParameter("country", "US")
                .appendQueryParameter("date", date)
                .build();

        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.equals(""))
                        throw new JSONException("Null Data Response from API");

                    response = response.replaceAll(System.lineSeparator(), ",");
                    response = "[" + response + "]";
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<POJOApplication> apps = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length() - 1; i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String description = (obj.has("translated_description") ? obj.getString("translated_description") : obj.getString("description"));
                        description = String.valueOf(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));

                        POJOApplication app = new POJOApplication(
                                obj.getString("id"),
                                obj.getString("app_name"),
                                description,
                                obj.getString("downloads"),
                                obj.getString("icon_url"),
                                obj.getString("store_url"),
                                obj.getString("version"),
                                obj.getString("price"),
                                obj.getString("publisher_name"),
                                obj.getString("publisher_url"),
                                String.valueOf(obj.getDouble("all_rating"))
                        );
                        apps.add(app);
                    }
                    listener.onAppDownloaded(apps);
                }
                catch(JSONException e){
                    Log.e("onResponseError", e.getMessage());
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorDownloading(""+error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String creds = String.format("%s:%s", apiKey, "X");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);

                return params;
            }
        };

        queue.add(request);
    }
}
