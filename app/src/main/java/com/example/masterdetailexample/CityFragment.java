package com.example.masterdetailexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.masterdetailexample.api.WeatherAdapter;
import com.example.masterdetailexample.model.DailyForecast;
import com.example.masterdetailexample.model.Temp;
import com.example.masterdetailexample.model.WeatherInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Show the weather for some city
 */
public class CityFragment extends Fragment {

    private static final String TAG = CityFragment.class.getSimpleName();
    public static final String FRAG_TAG = "CityFragment" ;

    private String cityId = null;

    @BindView(R.id.cityName)
    TextView name;

    @BindView(R.id.description)
    TextView desription;

    @BindView(R.id.weatherIcon)
    ImageView icon;

    @BindView(R.id.maxtemp)
    TextView maxTemp;

    @BindView(R.id.mintemp)
    TextView minTemp;

    public static CityFragment newInstance(String cityId) {
        Bundle args = new Bundle();
        args.putString ("id", cityId);
        CityFragment fragment =  new CityFragment ();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.city_layout, container, false);

        cityId = getArguments ().getString ("id");

        WeatherAdapter.getInstance().getCurrentWeatherForCity (Long.parseLong (cityId.trim ()),16)
                .observeOn(AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io ())
                .subscribe (new DisposableSubscriber<WeatherInfo> ( ) {
                    @Override
                    public void onNext(WeatherInfo info) {

                        if( info != null) {
                            if( info.getCity () != null) {
                                name.setText (info.getCity ().getName ( ));
                            }
                            if( info.getWeather () != null && info.getWeather ().size () > 0) {
                                DailyForecast dailyForecast = info.getWeather ( ).get (0);
                                if( dailyForecast != null) {

                                    if( dailyForecast.getWeather () != null && dailyForecast.getWeather ().size () >0) {
                                        desription.setText (dailyForecast.getWeather ( ).get (0).getMain ());

                                        String iconUrl = "http://openweathermap.org/img/w/"
                                                +dailyForecast.getWeather ( ).get (0).getIcon ()+".png";
                                        Picasso.with (getContext ()).load (iconUrl).into (icon);
                                    }
                                    minTemp.setText (dailyForecast.getTemp ( ).getMin ( ).toString ( ));
                                    maxTemp.setText (dailyForecast.getTemp ( ).getMax ( ).toString ( ));
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG,t.toString ());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"Completed subsribe");
                    }
                });

        ButterKnife.bind (this,view);
        return view;
    }

}
