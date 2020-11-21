package com.cenfotec.examen.service;

import java.util.List;
import java.util.Optional;

import com.cenfotec.examen.domain.Workshop;

public interface WorkshopService {
	public void saveWorkshop(Workshop workshop);
	public Optional<Workshop> get(Long id);
	public List<Workshop> getAll();
	public Workshop addActivity(Workshop workshop);
}
