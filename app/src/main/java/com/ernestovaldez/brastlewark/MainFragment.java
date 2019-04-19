package com.ernestovaldez.brastlewark;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    View view;
    String jsonResponse;

    JSONObject json;
    JSONArray jsonArray;
    String jsonRoot = "Brastlewark";
    FloatingActionButton fab;
    LinearLayout fab1;
    LinearLayout fab2;
    LinearLayout fab3;
    LinearLayout fab4;
    LinearLayout fab5;
    LinearLayout fab6;
    LinearLayout fab7;
    LinearLayout fab8;

    LinearLayout floatingMenu;

    RecyclerView recycler;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    Gnome gnome;
    List<Gnome> itemList;
    List<Gnome> searchList;
    Boolean isFABOpen = false;
    public String m_Text;
    public String s_Text;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_main, container,false);

        fab = view.findViewById(R.id.floatingActionButton);
        fab1 = view.findViewById(R.id.fabCont1);
        fab2 = view.findViewById(R.id.fabCont2);
        fab3 = view.findViewById(R.id.fabCont3);
        fab4 = view.findViewById(R.id.fabCont4);
        fab5 = view.findViewById(R.id.fabCont5);
        fab6 = view.findViewById(R.id.fabCont6);
        fab7 = view.findViewById(R.id.fabCont7);
        fab8 = view.findViewById(R.id.fabCont8);



        floatingMenu = view.findViewById(R.id.floatingMenu);
        floatingMenu.setVisibility(View.INVISIBLE);

        fab.setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }


        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog(getString(R.string.find1), findClass.ID);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog(getString(R.string.find2), findClass.NAME);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog("Enter minimum age", findClass.AGE);
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog("Enter minimum weight", findClass.Weight);
            }
        });

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog("Enter minimum height", findClass.Height);
            }
        });

        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog(getString(R.string.find6), findClass.HAIR_COLOR);
            }
        });

        fab7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog(getString(R.string.find7), findClass.PROFESSIONS);
            }
        });

        fab8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findDialog(getString(R.string.find8), findClass.FRIENDS);
            }
        });

        Toast.makeText(getContext(), isConnected(getContext())?"Connected":"No Connected", Toast.LENGTH_LONG).show();

        if (isConnected(getContext())) {
            new HttpAsyncTask(jsonResponse).execute("https://raw.githubusercontent.com/rrafols/mobile_test/master/data.json");
        }else {
            Toast.makeText(getContext(), "Error downloading information!", Toast.LENGTH_LONG).show();
            return view;
        }

        return view;
    }

    private void showFABMenu(){
        isFABOpen=true;
        floatingMenu.setVisibility(View.VISIBLE);
        floatingMenu.animate().translationY(-getResources().getDimension(R.dimen.standard_1));

    }

    public void closeFABMenu(){
        isFABOpen=false;
        floatingMenu.animate().translationY(0);
        floatingMenu.setVisibility(View.INVISIBLE);

    }

    private static String GET(String url){
        HttpURLConnection urlConnection;
        try {

            URL urlString = new URL(url);
            urlConnection = (HttpURLConnection) urlString.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            if(reader != null) {
                return convertInputStreamToString(in);
            } else {
                return "Did not work!";}

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return null;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line;
        StringBuilder result = new StringBuilder();
        while((line = bufferedReader.readLine()) != null)
            result.append(line);
        inputStream.close();

        return result.toString();

    }

    private boolean isConnected(@NonNull Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        String Response;

        HttpAsyncTask(String Response) {
            this.Response = Response;
        }

        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            parseJson(result);
        }
    }

    private void parseJson(String data){

        try {
            json = new JSONObject(data);
            jsonArray = json.getJSONArray(jsonRoot);

            recycler = view.findViewById(R.id.recycler_view);
            recycler.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this.getContext());
            recycler.setLayoutManager(layoutManager);

            itemList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                    gnome = new Gnome();
                    gnome.Id = String.valueOf(i);
                    gnome.Name = getName(i);
                    gnome.Photo = getPhoto(i);
                    gnome.Age = getAge(i);
                    gnome.Weight = getWeight(i);
                    gnome.Height = getHeight(i);
                    gnome.HairColor = getHairColor(i);
                    gnome.Professions = getProfessions(i);
                    gnome.Friends = getFriends(i);
                    gnome.ProfessionsList = getProfessionsList(i);
                    gnome.FriendsList = getFriendsList(i);

                    itemList.add(gnome);
                }

            mAdapter = new ListAdapter(itemList);
            recycler.setAdapter(mAdapter);
            fab.setEnabled(true);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getById(int index){
        try {
            return jsonArray.getJSONObject(index).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getName(int index){

        try {
            return jsonArray.getJSONObject(index).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getPhoto(int index){
        try {
            return jsonArray.getJSONObject(index).getString("thumbnail");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAge(int index){
        try {
            return jsonArray.getJSONObject(index).getString("age");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getWeight(int index){
        try {
            return jsonArray.getJSONObject(index).getString("weight");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getHeight(int index){
        try {
            return jsonArray.getJSONObject(index).getString("height");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getHairColor(int index){
        try {
            return jsonArray.getJSONObject(index).getString("hair_color");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> getProfessionsList(int index) throws JSONException {
        List<String> tempList = new ArrayList<>();

        JSONArray professions = jsonArray.getJSONObject(index).getJSONArray("professions");
        for (int i = 0; i < professions.length(); i++) {
            tempList.add(professions.getString(i));
        }
        return tempList;
    }

    private String getProfessions(int index){
        StringBuilder str = new StringBuilder();
        try {
            List<String> listProfessions = getProfessionsList(index);

            for (int i=0; i<listProfessions.size(); i++) {
                str.append(listProfessions.get(i)).append(", ");
            }

            return str.toString();

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> getFriendsList(int index) throws JSONException {
        List<String> tempList = new ArrayList<>();

        JSONArray friends = jsonArray.getJSONObject(index).getJSONArray("friends");
        for (int i = 0; i < friends.length(); i++) {
            tempList.add(friends.getString(i));
        }
        return tempList;
    }

    private String getFriends(int index){
        StringBuilder str = new StringBuilder();
        try {
            List<String> listProfessions = getFriendsList(index);

            for (int i=0; i<listProfessions.size(); i++) {
                str.append(listProfessions.get(i)).append(", ");
            }

            return str.toString();

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void searchById(@NonNull int index){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (gnome.Id.equals(String.valueOf(index))) {
                count++;
                searchList.add(gnome);
            }
        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Found: " + String.valueOf(count) + " gnome", Toast.LENGTH_LONG).show();
    }

    private void searchByName(@NonNull String name){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (gnome.Name.toLowerCase().contains(name.toLowerCase())) {
                count++;
                searchList.add(gnome);
            }

        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Name Found: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }

    private void searchByAge(@NonNull Integer minAge, @NonNull Integer maxAge){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (Integer.valueOf(gnome.Age) > minAge && Integer.valueOf(gnome.Age) < maxAge) {
                count++;
                searchList.add(gnome);
            }

        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Age Found: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }

    private void searchByHairColor(@NonNull String color){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (gnome.HairColor.toLowerCase().contains(color.toLowerCase())) {
                count++;
                searchList.add(gnome);
            }

        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Hair Color Found: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }

    private void searchByWeight(@NonNull Double minWeight, @NonNull Double maxWeight){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (Double.valueOf(gnome.Weight) > minWeight && Double.valueOf(gnome.Weight) < maxWeight) {
                count++;
                searchList.add(gnome);
            }

        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Weight Found: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }

    private void searchByHeight(@NonNull Double minHeight, @NonNull Double maxHeight){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (Double.valueOf(gnome.Height) > minHeight && Double.valueOf(gnome.Height) < maxHeight) {
                count++;
                searchList.add(gnome);
            }

        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Height Found: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }

    private void searchByProfession(@NonNull String profession){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (gnome.Professions.toLowerCase().contains(profession.toLowerCase())) {
                count++;
                searchList.add(gnome);
            }

        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Professions Found: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }

    private void searchByFriends(@NonNull String friend){
        int count = 0;
        searchList = new ArrayList<>();

        for (Gnome gnome : itemList) {
            if (gnome.Friends.toLowerCase().contains(friend.toLowerCase())) {
                count++;
                searchList.add(gnome);
            }

        }

        mAdapter = new ListAdapter(searchList);
        recycler.setAdapter(mAdapter);

        Toast.makeText(this.getContext(), "Friends Found: " + String.valueOf(count), Toast.LENGTH_LONG).show();
    }

    private enum findClass{
        ID, NAME, HAIR_COLOR, PROFESSIONS, FRIENDS, AGE, Weight, Height
    }

    public void findDialog(final String title, final findClass type ){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText input = new EditText(getContext());

//        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                m_Text = input.getText().toString();

                switch (type){
                    case ID:
                        searchById(Integer.valueOf(m_Text));
                        break;
                    case NAME:
                        searchByName(m_Text);
                        break;
                    case HAIR_COLOR:
                        searchByHairColor(m_Text);
                        break;
                    case PROFESSIONS:
                        searchByProfession(m_Text);
                        break;
                    case FRIENDS:
                        searchByFriends(m_Text);
                        break;
                    case AGE:
                        findDialog2("Enter maximum age", findClass.AGE);
                        break;
                    case Weight:
                        findDialog2("Enter maximum weight", findClass.AGE);
                        break;
                    case Height:
                        findDialog2("Enter maximum height", findClass.AGE);
                        break;
                    default:
                        break;
                }

                closeFABMenu();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void findDialog2(String title, final findClass type ){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText input = new EditText(getContext());

//        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                s_Text = input.getText().toString();

                switch (type){
                    case AGE:
                        searchByAge(Integer.valueOf(m_Text), Integer.valueOf(s_Text));
                        break;
                    case Weight:
                        searchByWeight(Double.valueOf(m_Text), Double.valueOf(s_Text));
                        break;
                    case Height:
                        searchByHeight(Double.valueOf(m_Text), Double.valueOf(s_Text));
                        break;
                    default:
                        break;
                }

                closeFABMenu();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

}
