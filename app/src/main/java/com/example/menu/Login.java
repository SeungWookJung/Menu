package com.example.menu;

import android.os.AsyncTask;



public class Login extends AsyncTask<String,Void,String> {

    public String result;

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String url = "http://116.126.79.199"

        return result;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
