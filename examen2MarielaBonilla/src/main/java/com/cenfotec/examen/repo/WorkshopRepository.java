package com.cenfotec.examen.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cenfotec.examen.domain.*;;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

}
