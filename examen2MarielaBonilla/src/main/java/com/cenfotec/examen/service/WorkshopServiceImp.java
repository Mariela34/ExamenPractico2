package com.cenfotec.examen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenfotec.examen.domain.Workshop;
import com.cenfotec.examen.repo.*;

@Service
public class WorkshopServiceImp implements WorkshopService {

	@Autowired 
	WorkshopRepository workshopRepo;

	public WorkshopServiceImp() {
		
	}
	
	@Override
	public void saveWorkshop(Workshop workshop) {
		workshopRepo.save(workshop);
	}

	@Override
	public Optional<Workshop> get(Long id) {
		return workshopRepo.findById(id);
	}

	@Override
	public List<Workshop> getAll() {
		return workshopRepo.findAll();
	}
	
	@Override
	public Workshop addActivity(Workshop workshop) {
		Workshop work = new Workshop();
		work = workshopRepo.save(workshop);
		return work;
	}
}
