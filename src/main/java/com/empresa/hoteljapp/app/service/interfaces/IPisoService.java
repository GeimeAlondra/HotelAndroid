package com.empresa.hoteljapp.app.service.interfaces;

import java.util.List;

import com.empresa.hoteljapp.app.models.entities.Piso;

public interface IPisoService {
		
	public List<Piso> findAll();
	
	public Piso findById(Long id);
	
	public Piso save(Piso piso);
	
	public void delete(Long id);
	
	public List<Piso> fyndByNombre(String nombre);
}
