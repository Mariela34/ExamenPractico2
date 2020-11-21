package com.cenfotec.examen.service;

import java.util.List;
import java.util.Optional;

import com.cenfotec.examen.domain.Actividad;

public interface ActividadService {
	public Actividad saveActividad(Actividad actividad);
	public Optional<Actividad> get(Long id);
	public List<Actividad> getAll();
}
