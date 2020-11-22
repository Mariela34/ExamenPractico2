package com.cenfotec.examen.controller;

import com.cenfotec.examen.domain.Actividad;
import com.cenfotec.examen.domain.Categoria;
import com.cenfotec.examen.domain.Workshop;
import com.cenfotec.examen.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workshopsjson")
public class ListJSONController {

    @Autowired
    WorkshopService workService;

    @GetMapping
    public List<Workshop> getWorkshops(){
        List<Workshop> ws = new ArrayList<Workshop>();
        if(workService.getAll() != null){
            for(Workshop w:workService.getAll()){
                int horas = 0, minutos = 0, segundos = 0;
                for(Actividad a : w.getActividades()){
                    LocalTime t = a.getTime();
                    horas += t.getHour();
                    minutos += t.getMinute();
                    segundos += t.getSecond();
                }
                w.setTiempoDuracion(WorkshopController.obtenerTiempoDuracion(horas,minutos,segundos));
                Workshop wFinal = new Workshop();
                wFinal.setName(w.getName());
                wFinal.setAutor(w.getAutor());
                Categoria c = new Categoria();
                c.setName(w.getCategoria().getName());
                wFinal.setCategoria(c);
                wFinal.setKeywords(w.getKeywords());
                wFinal.setId(w.getId());
                wFinal.setObjective(w.getObjective());
                wFinal.setTiempoDuracion(w.getTiempoDuracion());
                ws.add(wFinal);
            }
            return ws;
        }

        return null;
    }



}
