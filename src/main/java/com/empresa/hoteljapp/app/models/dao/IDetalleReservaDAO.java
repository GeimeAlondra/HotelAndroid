package com.empresa.hoteljapp.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.hoteljapp.app.models.entities.DetalleReserva;
//import com.empresa.hoteljapp.app.models.entities.Reserva;

public interface IDetalleReservaDAO extends CrudRepository<DetalleReserva, Long>{

}
