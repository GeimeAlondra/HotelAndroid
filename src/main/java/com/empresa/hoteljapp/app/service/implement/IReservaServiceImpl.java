package com.empresa.hoteljapp.app.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.hoteljapp.app.models.dao.IDetalleReservaDAO;
import com.empresa.hoteljapp.app.models.dao.IReservaDAO;
import com.empresa.hoteljapp.app.models.entities.DetalleReserva;
import com.empresa.hoteljapp.app.models.entities.Reserva;
import com.empresa.hoteljapp.app.service.interfaces.IReservaService;

@Service
public class IReservaServiceImpl implements IReservaService{
	
	@Autowired
	private IReservaDAO reservaDAO;
	
	@Autowired
	private IDetalleReservaDAO detalleReservaDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Reserva> findAll(Date fecha_registro) {
		return reservaDAO.findAllRecibidas();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reserva> findAllAceptadas(Date fecha_registro) {
		return reservaDAO.findAllAceptadas();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reserva> findAllCanceladas(Date fecha_registro) {
		return reservaDAO.findAllCanceladas();
	}

	@Transactional
	@Override
	public Reserva saveOrUpdate(Reserva reserva) {
		
		Reserva reservaPersisted = null;
		
		try {
			if(reserva.getId() == null) {
				
				List<DetalleReserva> detalleReserva = reserva.getDetalleReserva();
				reserva.setDetalleReserva(new ArrayList<DetalleReserva>());
			
				reservaPersisted = reservaDAO.save(reserva);
				
				//Recorriendo la coleccion de reserva
				for(DetalleReserva detalle: detalleReserva) {
					detalle.setReserva(reservaPersisted);
				}
				//Guardando el detalle de reserva
				detalleReservaDAO.saveAll(detalleReserva);
			
			}else {
				
				for(DetalleReserva detalle: reserva.getDetalleReserva()) {
					detalle.setReserva(reserva);
				}
				reservaDAO.save(reserva);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reservaPersisted;
	}

	@Override
	public Reserva changeState(Reserva reserva) {
		return reservaDAO.save(reserva);
	}

	@Override
	public Reserva findById(Long id) {
		return reservaDAO.findById(id).orElse(null);
	}

	@Override
	public List<Reserva> findAllRecibidasByUser(Long id) {
		// TODO Auto-generated method stub
		return reservaDAO.findAllRecibidasByUser(id);
	}

}
