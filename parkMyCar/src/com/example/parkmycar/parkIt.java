package com.example.parkmycar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/*
 *  This class receives data from the MainActivity and Calculates the points within a radius.
 *  Points are located in parking_spots
 */

public class parkIt extends ListActivity{
	
	protected void onCreate( Bundle savedInstanceState ){
		
		super.onCreate( savedInstanceState ) ;
		
		// Declarations ------------------------------------------------------------------------------------------------------------------------------------------------
		InputStream is = getResources().openRawResource(R.raw.spots);
		ArrayList<parkingSpotParser> ar = new ArrayList<parkingSpotParser>();
		double data[] ;
		
		//Get data from parent intent -------------------------------------------------------------------------------------------
		Intent intent = getIntent() ;
		data = intent.getDoubleArrayExtra( MainActivity.QUESTIONS ) ;
	
		// Make list of parking spots
		try {
				parkingSpotParser.parse(is, ar);
			} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(this, "Didn't work :( " , Toast.LENGTH_LONG).show();
			} catch( Exception ob) { Toast.makeText(this, ob.toString() , Toast.LENGTH_LONG).show(); }
		
		// Sort List
		ar = sortByPrice    ( data[ 2 ], ar ) ;
		ar = sortByDistance ( data[ 3 ], ar ) ;
		ar = sortByRest     ( data[ 1 ], ar ) ;
		ar = sortByCampus   ( data[ 0 ], ar ) ;
		
		//Make actual List
		setListAdapter( new ArrayAdapter<parkingSpotParser>(this, R.layout.list, ar)) ;
		ListView list = getListView() ;
		list.setTextFilterEnabled( true ) ;
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				// @TODO
				// This is where the magic happens!
				// Figure out which spot was chosen
				// Get the long/lat of the spot and the long/lat of the person
				// send intent to google maps.
				
				Toast.makeText(getApplicationContext(), ((TextView) arg1).getText().toString(), Toast.LENGTH_LONG).show() ;
				
			}
		}) ;
		
	}
	
	public ArrayList<parkingSpotParser> sortByPrice( double userP, ArrayList<parkingSpotParser> data ) {
	
		if( userP == -1 ) return data ;
		
		int i ;
		
		ArrayList<parkingSpotParser> out = new ArrayList<parkingSpotParser>() ;
		
		Collections.sort(data, new Comparator<parkingSpotParser>(){

			@Override
			public int compare(parkingSpotParser lhs, parkingSpotParser rhs) {
				
				
				if      ( lhs.pr >  rhs.pr ) return -1 ;
				else if ( lhs.pr == rhs.pr ) return  0 ;
				else                         return  1 ;
				
			}
			
			
		}) ;
		
		for ( i = 0 ; i < data.size() ; i ++ ) {
			
			if (data.get(i).pr < userP ) out.add( data.get( i ) ) ;
			
		}
		
		return out ;
		
	}
	
	public ArrayList<parkingSpotParser> sortByDistance( double userD, ArrayList<parkingSpotParser> data ) {
	
		if( userD == -1 ) return data ;
		
		int i ;
		
		ArrayList<parkingSpotParser> out = new ArrayList<parkingSpotParser>() ;
		
		Collections.sort(data, new Comparator<parkingSpotParser>(){

			@Override
			public int compare(parkingSpotParser lhs, parkingSpotParser rhs) {
				
				double distance1, distance2 ;
				
				distance1 = makeDistance( lhs.latty, lhs.longy ) ;
				distance2 = makeDistance( rhs.latty, rhs.longy ) ;
				
				lhs.setDis( distance1 ) ;
				rhs.setDis( distance2 ) ;
				
				if      ( distance1 >  distance2 ) return -1 ;
				else if ( distance1 == distance2 ) return  0 ;
				else                               return  1 ;
				
			}
			
			
		}) ;
		
		for ( i = 0 ; i < data.size() ; i ++ ) {
			
			if (data.get(i).dis < userD ) out.add( data.get( i ) ) ;
			
		}
		
		return out ;
		
	}
	
	public ArrayList<parkingSpotParser> sortByCampus( double userC, ArrayList<parkingSpotParser> data ) {
			
	   int i ;
		
	   ArrayList<parkingSpotParser> out = new ArrayList<parkingSpotParser>() ;
	   
	   if( userC == 0 ) return  data ;
	   
	   for ( i = 0 ; i < data.size(); i ++ ) {
		   
		   if ( data.get( i ).onC == 1 ) {
			  
			  out.add(data.get(i)) ; 
			   
		   }
		   
	   }
	   
	   return out ;
		
	}
	
	public ArrayList<parkingSpotParser> sortByRest( double userC, ArrayList<parkingSpotParser> data ) {
		
		   int i ;
			
		   ArrayList<parkingSpotParser> out = new ArrayList<parkingSpotParser>() ;
		   
		   if( userC == 0 ) return  data ;
		   
		   for ( i = 0 ; i < data.size(); i ++ ) {
			   
			   if ( data.get( i ).resP == 1 ) {
				  
				  out.add(data.get(i)) ; 
				   
			   }
			   
		   }
		   
		   return out ;
			
		}

	public double makeDistance( double latitude, double longitude ){ 
		
		Location current = new Location("Point A") ; 
		// @Todo
		// Get current GPS location, if, not possible, use this default location ( East Campus UML ) 
		current.setLatitude(42.654788 ) ; current.setLongitude( -71.326708 ) ;
		
		Location destination = new Location("Point B") ;
		destination.setLatitude( latitude ) ; destination.setLongitude( longitude ) ; 
		
		return current.distanceTo(destination);

	}
	
}
