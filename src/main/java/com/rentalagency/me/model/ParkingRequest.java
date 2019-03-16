	package com.rentalagency.me.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ParkingRequest extends Request {
	/*
	 * Parking Spot Assigned x & y coordinate
	 * Start & End Time used to estimate cost
	 * userid binding agent between it & user
	 */

	private rowSpot rsp;
	private colSpot csp;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "start_time", nullable = true, length = 10)
	private Date startTime;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_time", nullable = true, length = 10)
	private Date endTime;
	
	
	// Assign Parking Spot 1A, 4E, etc
	public enum rowSpot{
		ONE("ONE"),TWO("TWO"),THREE("THREE"),FOUR("FOUR"),FIVE("FIVE"),
		SIX("SIX"),SEVEN("SEVEN"),EIGHT("EIGHT"),NINE("NINE");
		
		private String name;
		
		rowSpot(String name){
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}		
	
	}
	
	public enum colSpot{
		A("A"),B("B"),C("C"),D("D"),E("E"),F("F"),G("G");
		
		private String name;
		
		colSpot (String name) {
			this.name= name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
		
	}


	public rowSpot getRsp() {
		return rsp;
	}

	public void setRsp(rowSpot rsp) {
		this.rsp = rsp;
	}

	public colSpot getCsp() {
		return csp;
	}

	public void setCsp(colSpot csp) {
		this.csp = csp;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	// Requires endTime & minimum time charged 60 minutes
	public long duration() throws IllegalArgumentException {
		if(endTime != null) {
			long d = endTime.getMinutes() - startTime.getMinutes();
			if (d < 60) 
				return 60L;
			else
				return d;
		}
		throw new IllegalArgumentException("Parking Spot Meter still running");
		
	}
	
	
	
	

}
