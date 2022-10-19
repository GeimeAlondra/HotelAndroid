package com.empresa.hoteljapp.app.service.interfaces;

import java.util.List;

import com.empresa.hoteljapp.app.models.entities.TipoHabitacion;


public interface ITipoHabitacionService {
	
	public List<TipoHabitacion> findAll();
	
	public TipoHabitacion findById(Long id);
	
	public TipoHabitacion save(TipoHabitacion tipoHabitacion);
	
	public void delete(Long id);
	
	public List<TipoHabitacion> fyndByNombre(String nombre);

}
