package com.empresa.hoteljapp.app.service.implement;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.hoteljapp.app.models.dao.IPisoDAO;
import com.empresa.hoteljapp.app.models.entities.Piso;
import com.empresa.hoteljapp.app.service.interfaces.IPisoService;

@Service
public class IPisoServiceImpl implements IPisoService{
	
	@Autowired
	private IPisoDAO pisoDAO;


	@Override
	public Piso findById(Long id) {
		return pisoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Piso save(Piso piso) {
		return pisoDAO.save(piso);
	}

	@Override
	public List<Piso> findAll() {
		return (List<Piso>)pisoDAO.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		pisoDAO.deleteById(id);
		
	}

	@Override
	public List<Piso> fyndByNombre(String nombre) {
		return pisoDAO.findByNombreIgnoreCase(nombre);
	}

}
