package com.example.myrecipe.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {
//    String  weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=";
//    String weatherURL2 = "&appid=071c3ffca10be01d334505630d2c1a9c";
//
//    String iconURL1 = "https://openweathermap.org/img/wn/";
//    String iconURL2 = "@2x.png";
//
//    String url = "http://gd.geobytes.com/AutoCompleteCity?&q=";


    public static final ExecutorService networkingExecutor = Executors.newFixedThreadPool(4);
    static Handler networkHander = new Handler(Looper.getMainLooper());

    public interface NetworkingListener{
        void APINetworkListner(String jsonString);
        void APINetworkingListerForImage(Bitmap image);
    }

    public NetworkingListener getListener() {
        return listener;
    }

    public void setListener(NetworkingListener listener) {
        this.listener = listener;
    }

    NetworkingListener listener;


    public void fetchRecipeName(String text){
        String completeURL = "https://recipesapi2.p.rapidapi.com/recipes/tomato%20soup?maxRecipes=2";
        Map<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-host", "yummly2.p.rapidapi.com");
        headers.put("x-rapidapi-key", "d6d9d51538msh4e0d05aa4407252p19dab2jsne4e1fd1bc902");
        connect(completeURL, headers);
    }


    public void fetchRecipeData(String name){

        String completeURL = "https://recipesapi2.p.rapidapi.com/recipes/" + name+"?maxRecipes=5";
        Map<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-host", "recipesapi2.p.rapidapi.com");
        headers.put("x-rapidapi-key", "d6d9d51538msh4e0d05aa4407252p19dab2jsne4e1fd1bc902");
        connect(completeURL, headers);
    }

//    public void getImageData(String icon){
//        String completeURL = iconURL1 + icon + iconURL2;
//        networkingExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL urlObj = new URL(completeURL);
//                    InputStream in = ((InputStream)urlObj.getContent());
//                    Bitmap imageData = BitmapFactory.decodeStream(in);
//                    networkHander.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            listener.APINetworkingListerForImage(imageData);
//                        }
//                    });
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    public void getImageData2(String completeURL){
        networkingExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlObj = new URL(completeURL);
                    InputStream in = ((InputStream)urlObj.getContent());
                    Bitmap imageData = BitmapFactory.decodeStream(in);
                    networkHander.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.APINetworkingListerForImage(imageData);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // tor
    private void connect(String url){
        networkingExecutor.execute(new Runnable() {
            String jsonString = "";
            @Override
            public void run() {

                HttpURLConnection httpURLConnection = null;
                try {
                    URL urlObject = new URL(url);
                    httpURLConnection = (HttpURLConnection) urlObject.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    int statues = httpURLConnection.getResponseCode();

                    if ((statues >= 200) && (statues <= 299)) {
                        InputStream in = httpURLConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(in);
                        int read = 0;
                        while ((read = inputStreamReader.read()) != -1) {// json integers ASCII
                            char c = (char) read;
                            jsonString += c;
                        }

                        final String finalJson = jsonString;
                        networkHander.post(new Runnable() {
                            @Override
                            public void run() {
                                //send data to main thread
                                listener.APINetworkListner(finalJson);
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }


    // tor
    private void connect(String url, Map<String, String> headers){
        networkingExecutor.execute(new Runnable() {
            String jsonString = "";
            @Override
            public void run() {

                HttpURLConnection httpURLConnection = null;
                try {
                    URL urlObject = new URL(url);
                    httpURLConnection = (HttpURLConnection) urlObject.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");

                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
                    }

                    int statues = httpURLConnection.getResponseCode();

                    if ((statues >= 200) && (statues <= 299)) {
                        InputStream in = httpURLConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(in);
                        int read = 0;
                        while ((read = inputStreamReader.read()) != -1) {// json integers ASCII
                            char c = (char) read;
                            jsonString += c;
                        }

                        final String finalJson = jsonString;
                        networkHander.post(new Runnable() {
                            @Override
                            public void run() {
                                //send data to main thread
                                listener.APINetworkListner(finalJson);
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }

}
