package com.empresa.hoteljapp.app.service.interfaces;

import java.util.Date;
import java.util.List;

import com.empresa.hoteljapp.app.models.entities.Reserva;

public interface IReservaService {
	
	public List<Reserva> findAllRecibidasByUser (Long id);
	
	public List<Reserva> findAll(Date fecha_registro);
	
	public List<Reserva> findAllAceptadas(Date fecha_registro);
	
	public List<Reserva> findAllCanceladas(Date fecha_registro);
	
	public Reserva saveOrUpdate(Reserva reserva);
	
	public Reserva changeState(Reserva reserva);
	
	public Reserva findById(Long id);

}
