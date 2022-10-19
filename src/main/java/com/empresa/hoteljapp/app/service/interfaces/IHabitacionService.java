package com.empresa.hoteljapp.app.service.interfaces;

import java.util.List;

import com.empresa.hoteljapp.app.models.entities.Habitacion;

public interface IHabitacionService {
	
	public List<Habitacion> findAllActivos();
	
	public List<Habitacion> findAllInactivos();
	
	public Habitacion findById(Long id);
	
	public Habitacion save(Habitacion habitacion);

	public Habitacion changeEstado(Habitacion habitacion);

	public List<Habitacion> isExist(Habitacion habitacion);
}

