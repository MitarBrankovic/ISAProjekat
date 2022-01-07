package com.BookingApp.dto;

import java.util.List;
import com.BookingApp.model.BoatAppointmentReport;
import com.BookingApp.model.FishingAppointmentReport;
import com.BookingApp.model.CottageAppointmentReport;

public class ReportsClientDto {

	public List<CottageAppointmentReport> cottageReports;
	public List<BoatAppointmentReport> boatReports;
	public List<FishingAppointmentReport> fishingReports;
	
	public ReportsClientDto() {}

	public ReportsClientDto(List<CottageAppointmentReport> cottageReports, List<BoatAppointmentReport> boatReports,
			List<FishingAppointmentReport> fishingReports) {
		super();
		this.cottageReports = cottageReports;
		this.boatReports = boatReports;
		this.fishingReports = fishingReports;
	}
	
	
	
}
