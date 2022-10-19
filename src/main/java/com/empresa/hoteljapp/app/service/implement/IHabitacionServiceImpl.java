package com.empresa.hoteljapp.app.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.hoteljapp.app.models.dao.IHabitacionDAO;
import com.empresa.hoteljapp.app.models.entities.Habitacion;
import com.empresa.hoteljapp.app.service.interfaces.IHabitacionService;

@Service
public class IHabitacionServiceImpl implements IHabitacionService{

	@Autowired
	private IHabitacionDAO habitacionDAO;

	@Override
	public List<Habitacion> findAllActivos() {
		return habitacionDAO.findAll();
	}

	@Override
	public List<Habitacion> findAllInactivos() {
		return habitacionDAO.findAllInactivos();
	}

	@Override
	public Habitacion findById(Long id) {
		return habitacionDAO.findById(id).orElse(null);
	}

	@Override
	public Habitacion save(Habitacion habitacion) {
		return habitacionDAO.save(habitacion);
	}

	@Override
	public Habitacion changeEstado(Habitacion habitacion) {
		return habitacionDAO.save(habitacion);
	}

	@Override
	public List<Habitacion> isExist(Habitacion habitacion) {
		return habitacionDAO.findByNombrePrecio(habitacion);
	}
}
