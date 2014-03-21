package com.example.parkmycar;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String QUESTIONS = "com.example.parkmycar.questions" ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	public double getPrice(){
		
		String p ;
		
		EditText priceT = (EditText) findViewById(R.id.price);
		
		p = priceT.getText().toString() ;
		
		if(!p.equals(""))
		return Double.parseDouble(p) ;
		else
			return -1.0;
		
	}
	
	public double getDistance(){
		
		String p ;
		
		EditText distT = (EditText) findViewById(R.id.distance);
			
		p = distT.getText().toString() ;
		
		if(!p.equals(""))
		return Double.parseDouble(p) ;
		else
			return -1.0;
		
	}

	public double onCampus(){
		
		CheckBox box = (CheckBox) findViewById(R.id.checkBox1);
		
		if( box.isChecked() )  return 1.0 ;
		else                   return 0.0 ;
		
	}
	
	public double resPark(){
		
		CheckBox box = (CheckBox) findViewById(R.id.checkBox2);
		
		if( box.isChecked() )  return 1.0 ;
		else                   return 0.0 ;
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void parkIt( View view ){
		
		double data[] = new double[]{
				
				onCampus(),
				resPark(),
				getPrice(),
				getDistance()		
		};
		
		
		Intent intent = new Intent(this, parkIt.class ) ;
		
													// Send Data to Map activity
		intent.putExtra(QUESTIONS, data ) ;                                         
		startActivity( intent ) ;
		
	}
	
}
