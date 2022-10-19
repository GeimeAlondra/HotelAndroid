package com.empresa.hoteljapp.app.service.implement;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.hoteljapp.app.models.dao.ITipoHabitacionDAO;
import com.empresa.hoteljapp.app.models.entities.TipoHabitacion;
import com.empresa.hoteljapp.app.service.interfaces.ITipoHabitacionService;

@Service
public class ITipoHabitacionServiceImpl implements ITipoHabitacionService{
	@Autowired
	private ITipoHabitacionDAO tipoHabitacionDAO;

	@Override
	public List<TipoHabitacion> findAll() {
		return (List<TipoHabitacion>)tipoHabitacionDAO.findAll();
	}

	@Override
	public TipoHabitacion findById(Long id) {
		return tipoHabitacionDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoHabitacion save(TipoHabitacion tipoHabitacion) {
		return tipoHabitacionDAO.save(tipoHabitacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		tipoHabitacionDAO.deleteById(id);

		
	}

	@Override
	public List<TipoHabitacion> fyndByNombre(String nombre) {
		return tipoHabitacionDAO.findByNombreIgnoreCase(nombre);
	}

}
