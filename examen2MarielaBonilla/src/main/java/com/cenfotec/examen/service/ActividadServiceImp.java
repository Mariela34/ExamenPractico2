package com.cenfotec.examen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cenfotec.examen.domain.Actividad;
import com.cenfotec.examen.repo.*;

import org.springframework.stereotype.*;

@Service
public class ActividadServiceImp implements ActividadService {
	
	@Autowired 
	ActividadRepository actividadRepo;
	
	public ActividadServiceImp() {
		
	}

	@Override
	public Actividad saveActividad(Actividad actividad) {
		return actividadRepo.save(actividad);
	}

	@Override
	public Optional<Actividad> get(Long id) {
		return actividadRepo.findById(id);
	}

	@Override
	public List<Actividad> getAll() {
		return actividadRepo.findAll();
	}

}
