package com.kwabenaberko.openweathermap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kwabenaberko.*;
import com.kwabenaberko.openweathermaplib.Lang;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.models.threehourforecast.ThreeHourForecast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

     //Single Choice Button
    private Button btnDialog2;
    double lat[]={37.5514,37.422581,37.456349,37.823029,36.712332,36.565734,35.717595,34.848497,36.286007,35.195140,33.399171};//"서울", "경기", "인천", "강원", "충남", "충북", "전북", "전남","경북","경남","제주"
    double lot[]={126.9930,127.119839, 126.704912, 128.159377, 127.776542, 126.893100, 127.153075, 126.900110, 128.739132, 129.033288, 126.638893};//"서울", "경기", "인천", "강원", "충남", "충북", "전북", "전남","경북","경남","제주"
    double latinfo;
    double lotinfo;
    OpenWeatherMapHelper helper = new OpenWeatherMapHelper();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView weatherInfo=findViewById(R.id.textview);


        //Set API KEY
        helper.setApiKey("953ba9aa8b094fb1ee03bdb7a6948c5f");
        //Set Units
        helper.setUnits(Units.IMPERIAL);
        //Set lang
        helper.setLang(Lang.KOREAN);


        final List selectedItems = new ArrayList(); // 버튼 선언
       btnDialog2 = (Button) findViewById(R.id.btnDialog2);



        btnDialog2.setOnClickListener(new View.OnClickListener(){

            String address="";
            @Override public void onClick(View v) {

                final String[] items = new String[]{"서울", "경기", "인천", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"};
                final int[] selectedIndex = {0};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("지역을 선택하세요")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ;
                                address = items[selectedIndex[0]];
                                switch (address){
                                    case "서울":
                                        latinfo=lat[0];
                                        lotinfo=lot[0];
                                        break;
                                    case "경기":
                                        latinfo=lat[1];
                                        lotinfo=lot[1];
                                        break;
                                    case "인천":
                                        latinfo=lat[2];
                                        lotinfo=lot[2];
                                        break;
                                    case "강원":
                                        latinfo=lat[3];
                                        lotinfo=lot[3];
                                        break;
                                    case "충북":
                                        latinfo=lat[4];
                                        lotinfo=lot[4];
                                        break;
                                    case "충남":
                                        latinfo=lat[5];
                                        lotinfo=lot[5];
                                        break;
                                    case "전북":
                                        latinfo=lat[6];
                                        lotinfo=lot[6];
                                        break;
                                    case "전남":
                                        latinfo=lat[7];
                                        lotinfo=lot[7];
                                        break;
                                    case "경북":
                                        latinfo=lat[8];
                                        lotinfo=lot[8];
                                        break;
                                    case "경남":
                                        latinfo=lat[9];
                                        lotinfo=lot[9];
                                        break;
                                    case "제주":
                                        latinfo=lat[10];
                                        lotinfo=lot[10];
                                        break;

                                    default:

                                }
                                //위도 경도로 알아내기
                                helper.getThreeHourForecastByGeoCoordinates(latinfo,lotinfo, new OpenWeatherMapHelper.ThreeHourForecastCallback() {
                                    @Override
                                    public void onSuccess(ThreeHourForecast threeHourForecast) {

                                        weatherInfo.setText("City/Country: "+ address + "/" + threeHourForecast.getCity().getCountry() +"\n"
                                                +"Forecast Array Count: " + threeHourForecast.getCnt() +"\n"
                                                //For this example, we are logging details of only the first forecast object in the forecasts array
                                                +"First Forecast Date Timestamp: " + threeHourForecast.getThreeHourWeatherArray().get(0).getDt() +"\n"
                                                +"First Forecast Weather Description: " + threeHourForecast.getThreeHourWeatherArray().get(0).getWeatherArray().get(0).getDescription()+ "\n"
                                                +"First Forecast Max Temperature: " + threeHourForecast.getThreeHourWeatherArray().get(0).getMain().getTempMax()+"\n"
                                                +"First Forecast Wind Speed: " + threeHourForecast.getThreeHourWeatherArray().get(0).getWind().getSpeed() + "\n");
                                    }

                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        Log.v(TAG, throwable.getMessage());
                                    }
                                });

                                Toast.makeText(MainActivity.this, address+latinfo+lotinfo, Toast.LENGTH_SHORT).show();

                            }
                        }).create().show();
                //Instantiate class



            }
        });







     /*

        *//*
        This Example Only Shows how to get current weather by city name
        Check the docs for more methods [https://github.com/KwabenBerko/OpenWeatherMap-Android-Library/]

        *//*



        helper.getCurrentWeatherByCityName("Seoul", new OpenWeatherMapHelper.CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Log.v(TAG,
                        "Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                                +"Weather Description: " + currentWeather.getWeatherArray().get(0).getDescription() + "\n"
                                +"Temperature: " + currentWeather.getMain().getTempMax()+"\n"
                                +"Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );



            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });
*/





    }
}
