package com.example.gary.stockleaksapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    ProgressBar progressBar;
    ListView listView;

    List<Products> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewProducts);
        productList = new ArrayList<>();



        try {
            JSONObject object = new JSONObject("products");
            if (!object.getBoolean("error")) {
                Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                refreshHeroList(object.getJSONArray("products"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        readProducts();

    }


    private void readProducts() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_PRODUCTS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshHeroList(JSONArray products) throws JSONException {

        for (int i = 0; i < products.length(); i++) {
            JSONObject obj =products.getJSONObject(i);

            productList.add(new Products(
                    obj.getInt("id"),
                    obj.getString("productName"),
                    obj.getString("productCategory"),
                    obj.getString("sellType")
            ));
        }

        ProductAdapter adapter = new ProductAdapter(productList);
        listView.setAdapter(adapter);
    }


    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("products"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class ProductAdapter extends ArrayAdapter<Products> {
        List<Products> productList;

        public ProductAdapter(List<Products> productList) {
            super(MainActivity.this, R.layout.activity_product_list,productList);
            this.productList = productList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.activity_product_list, null, true);

            TextView textViewProduct = listViewItem.findViewById(R.id.textViewProduct);

            TextView textViewCategory = listViewItem.findViewById(R.id.textViewCategory);
            TextView textViewType = listViewItem.findViewById(R.id.textViewType);

            final Products product = productList.get(position);

            textViewProduct.setText(product.getProductName());
            textViewCategory.setText(product.getProductCategory());
            textViewType.setText(product.getSellType());

            return listViewItem;
        }
    }

}
