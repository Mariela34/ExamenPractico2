package com.cenfotec.examen.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

import javax.persistence.*;

@Entity
@Table(name="actividad")
public class Actividad {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="nombre")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="texto")
	private String text;
	
	@Column(name="tiempo")
	@DateTimeFormat(pattern = "hh:mm:ss")
	private LocalTime time;

	@Transient
	private String tiempoTemporal;
	
	@ManyToOne
    @JoinColumn(name="id_workshop", nullable=false)
	private Workshop workshop;
	
	public Actividad(){
		
	}

	public Actividad(String name, String description, String text, LocalTime time) {
		this.name = name;
		this.description = description;
		this.text = text;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	public String getTiempoTemporal() {
		return tiempoTemporal;
	}

	public void setTiempoTemporal(String tiempoTemporal) {
		this.tiempoTemporal = tiempoTemporal;
	}

	@Override
	public String toString() {
		return "Actividad{" +
				"id:" + id +
				", name:'" + name + '\'' +
				", description:'" + description + '\'' +
				", text:" + text + '\'' +
				", tiempoTemporal:'" + tiempoTemporal + '\'' +
				'}';
	}
}
