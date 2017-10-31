package com.example.masterdetailexample.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.masterdetailexample.R;
import com.example.masterdetailexample.api.WeatherAdapter;
import com.example.masterdetailexample.model.DailyForecast;
import com.example.masterdetailexample.model.WeatherInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;


public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private static final String TAG =  CitiesAdapter.class.getSimpleName ();
    private List<City> values;

    private Context mCotext;
    public CitiesAdapter(List<City> cities) {
       values =cities;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView description;
        public ImageView weatherIcon;
        public TextView maxtemp;
        public TextView mintemp;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.cityName);
            description = (TextView) v.findViewById(R.id.description);
            weatherIcon = (ImageView) v.findViewById(R.id.weatherIcon);
            maxtemp = (TextView)v.findViewById (R.id.maxtemp);
            mintemp = (TextView)v.findViewById (R.id.mintemp);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mCotext = parent.getContext ().getApplicationContext ();
        View v = inflater.inflate(R.layout.city_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final City city = values.get(position);

        WeatherAdapter.getInstance().getCurrentWeatherForCity (Long.parseLong (city.getId ().trim ()),1)
                .observeOn(AndroidSchedulers.mainThread ())
                .subscribeOn (Schedulers.io ())
                .subscribe (new DisposableSubscriber<WeatherInfo> ( ) {
                    @Override
                    public void onNext(WeatherInfo info) {

                        if( info != null) {
                            if( info.getCity () != null) {
                                holder.name.setText (info.getCity ().getName ( ));
                            }
                            if( info.getWeather () != null && info.getWeather ().size () > 0) {
                                DailyForecast dailyForecast = info.getWeather ( ).get (0);
                                if( dailyForecast != null) {

                                    if( dailyForecast.getWeather () != null && dailyForecast.getWeather ().size () >0) {
                                        holder.description.setText (dailyForecast.getWeather ( ).get (0).getMain ());

                                        String iconUrl = "http://openweathermap.org/img/w/"
                                                +dailyForecast.getWeather ( ).get (0).getIcon ()+".png";
                                        Picasso.with (mCotext).load (iconUrl).into (holder.weatherIcon);
                                    }
                                    holder.mintemp.setText (dailyForecast.getTemp ( ).getMin ( ).toString ( ));
                                    holder.maxtemp.setText (dailyForecast.getTemp ( ).getMax ( ).toString ( ));
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

        holder.name.setText(city.getName ());
        holder.itemView.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("city_weather_click");
                // You can also include some extra data.
                intent.putExtra("city_id", city.getId ());
                LocalBroadcastManager.getInstance(mCotext).sendBroadcast(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
