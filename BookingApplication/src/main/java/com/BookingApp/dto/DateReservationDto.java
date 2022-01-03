package com.BookingApp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class DateReservationDto {
	
	//@JsonFormat(pattern = "dd.MM.yyyy. HH:mm:ss")
	//@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	//@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDate datePick;
	public int time;
	
	public DateReservationDto() {}
	
	public DateReservationDto(LocalDate datePick, int time) {
		super();
		this.datePick = datePick;
		this.time = time;
	}
	
	
}
