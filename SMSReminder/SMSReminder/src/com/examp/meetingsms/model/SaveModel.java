package com.examp.meetingsms.model;

public class SaveModel {

	
		int _id;
	    String _title;
	    String _venue;
	    String _pointer;
	    String _day;
	    String _month;
	    String _year;
	    String _hour;
	    String _min;
	    
	      // Empty constructor
	      public SaveModel(){
	 
	       }


	   // constructor
	   public SaveModel(int id, String mid, String title, String venue, String Pointer, int date, int month, int year, int hour, int min){
	       this._id = id;
	       this._title = title;
	       this._venue = venue;
	   }
	}


