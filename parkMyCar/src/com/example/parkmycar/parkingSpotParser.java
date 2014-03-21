package com.example.parkmycar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class parkingSpotParser {

	String title;
	public double longy, latty, onC, resP, pr, dis ;
	
	parkingSpotParser( String tit, String lonG, String lattY, String c, String rp, String p ){
		
		title = tit ;
		longy = Double.parseDouble( lonG )  ;
		latty = Double.parseDouble( lattY ) ;
		onC   = Double.parseDouble(c)       ;
		resP  = Double.parseDouble( rp )    ;
		pr    =  Double.parseDouble( p )    ;
		dis   =                       0     ;
		
	}
	
	public static void parse(InputStream inputStream, 	ArrayList<parkingSpotParser> ar ) throws IOException {
		
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);
			
		while( reader.readLine() != null ){
			ar.add(new parkingSpotParser(reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine() )) ; // need to work on this
		}
		
	}

	public String toString() {
		return title + "\n Price: $" + pr + "\tDistance: " + dis + " m";
	}
	
	public void setDis( double d ) {
		
		this.dis = d ;
		
	}

}
