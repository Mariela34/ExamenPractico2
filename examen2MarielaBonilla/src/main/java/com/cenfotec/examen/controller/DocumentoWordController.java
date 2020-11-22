package com.cenfotec.examen.controller;

import com.cenfotec.examen.domain.*;
import com.cenfotec.examen.service.*;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/obtenerReporte")
public class DocumentoWordController {

    @Autowired
    WorkshopService workshopService;

    @Autowired
    ActividadService actividadService;

    @GetMapping(value = "/{id}")
    public void getGeneratedDocumentoWord(HttpServletResponse response, @PathVariable Long id) throws IOException{
        String word = "reporte-workshops.docx";
        Optional<Workshop> workshopOptional = workshopService.get(id);
        if(workshopOptional.isPresent()) {
            response.setHeader("Content-disposition", "attachment; filename=reporte-workshops.docx");
            Workshop workshop = getWorkshopGenerated(id);
            formatWordDocument(response, workshop);
        }
    }


    public void formatWordDocument(HttpServletResponse response, Workshop workshop) throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();

        titleRun.setText("REPORTE DEL WORKSHOP \"" + workshop.getName().toUpperCase() + "\"");
        titleRun.setBold(true);
        titleRun.setFontFamily("Arial");
        titleRun.setFontSize(20);
        titleRun.addBreak();
        titleRun.addBreak();
        titleRun.addBreak();

        XWPFParagraph titleName = document.createParagraph();
        titleName.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleNameRun = titleName.createRun();
        titleNameRun.setBold(true);
        titleNameRun.setFontSize(14);
        titleNameRun.setFontFamily("Arial");
        titleNameRun.setText("Nombre:  ");
        XWPFRun nameRun = titleName.createRun();
        nameRun.setBold(false);
        nameRun.setFontSize(14);
        nameRun.setText(workshop.getName());
        nameRun.addBreak();

        XWPFParagraph titleAutor = document.createParagraph();
        titleAutor.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleAutorRun = titleAutor.createRun();
        titleAutorRun.setBold(true);
        titleAutorRun.setFontSize(14);
        titleAutorRun.setFontFamily("Arial");
        titleAutorRun.setText("Autor:  ");
        XWPFRun autorRun = titleAutor.createRun();
        autorRun.setBold(false);
        autorRun.setFontSize(14);
        autorRun.setText(workshop.getAutor());
        autorRun.addBreak();

        XWPFParagraph titleCategory = document.createParagraph();
        titleCategory.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleCategoryRun = titleCategory.createRun();
        titleCategoryRun.setBold(true);
        titleCategoryRun.setFontSize(14);
        titleCategoryRun.setFontFamily("Arial");
        titleCategoryRun.setText("Categoria:  ");
        XWPFRun categoryRun = titleCategory.createRun();
        categoryRun.setBold(false);
        categoryRun.setFontSize(14);
        categoryRun.setText(workshop.getCategoria().getName());
        categoryRun.addBreak();


        XWPFParagraph titleObjective = document.createParagraph();
        titleObjective.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleObjectiveRun = titleObjective.createRun();
        titleObjectiveRun.setBold(true);
        titleObjectiveRun.setFontSize(14);
        titleObjectiveRun.setFontFamily("Arial");
        titleObjectiveRun.setText("Objectivo del taller:  ");
        XWPFRun objectiveRun = titleObjective.createRun();
        objectiveRun.setBold(false);
        objectiveRun.setFontSize(14);
        objectiveRun.setText(workshop.getObjective());
        objectiveRun.addBreak();

        /**
         * listar keywords
         **/
        String[] keywords = workshop.getKeywords().split(",");
        workshop.setKeywords( "1-"+keywords[0] + ", 2-" +keywords[1]+", 3-" + keywords[2]);

        XWPFParagraph titleKeywords = document.createParagraph();
        titleKeywords.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleKeywordsRun = titleKeywords.createRun();
        titleKeywordsRun.setBold(true);
        titleKeywordsRun.setFontSize(14);
        titleKeywordsRun.setFontFamily("Arial");
        titleKeywordsRun.setText("Palabras clave / Keywords:  ");
        XWPFRun keywordsRun = titleKeywords.createRun();
        keywordsRun.setBold(false);
        keywordsRun.setFontSize(14);
        keywordsRun.setText(workshop.getKeywords());
        keywordsRun.addBreak();


        /**
         * Tiempo de duracion
         */
        XWPFParagraph titleDuracion = document.createParagraph();
        titleDuracion.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleDuracionRun = titleDuracion.createRun();
        titleDuracionRun.setBold(true);
        titleDuracionRun.setFontSize(14);
        titleDuracionRun.setFontFamily("Arial");
        titleDuracionRun.setText("Tiempo de duracion total de actividades:  ");
        XWPFRun duracionRun = titleDuracion.createRun();
        duracionRun.setBold(false);
        duracionRun.setFontSize(14);
        duracionRun.setText(workshop.getTiempoDuracion());
        duracionRun.addBreak();


        XWPFParagraph titleActividades = document.createParagraph();
        titleActividades.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleActividadesRun = titleActividades.createRun();
        titleActividadesRun.setBold(true);
        titleActividadesRun.setFontSize(14);
        titleActividadesRun.setFontFamily("Arial");
        titleActividadesRun.setText("Lista de actividades:  ");
        titleActividadesRun.addBreak();

        XWPFTable tActividades = document.createTable();
        tActividades.setCellMargins(1, 400, 1, 400);
        XWPFTableRow headerRow = tActividades.getRow(0);

        //Nombre
        XWPFParagraph theadNombre = headerRow.getCell(0).addParagraph();
        theadNombre.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun theadNRun = theadNombre.createRun();
        theadNRun.setBold(true);
        theadNRun.setFontSize(12);
        theadNRun.setFontFamily("Arial");
        theadNRun.setColor("FFFFFF");
        theadNRun.setText("Nombre");
        headerRow.getCell(0).getCTTc().addNewTcPr().addNewShd().setFill("30887d");

        //Descripcion
        headerRow.addNewTableCell();
        XWPFParagraph theadDescrip = headerRow.getCell(1).addParagraph();
        XWPFRun theadDRun = theadDescrip.createRun();
        theadDRun.setBold(true);
        theadDRun.setFontSize(12);
        theadDRun.setFontFamily("Arial");
        theadDRun.setColor("FFFFFF");
        theadDRun.setText("Descripción");
        headerRow.getCell(1).getCTTc().addNewTcPr().addNewShd().setFill("30887d");


        //Texto
        headerRow.addNewTableCell();
        XWPFParagraph theadText = headerRow.getCell(2).addParagraph();
        XWPFRun theadTRun = theadText.createRun();
        theadTRun.setBold(true);
        theadTRun.setFontSize(12);
        theadTRun.setFontFamily("Arial");
        theadTRun.setColor("FFFFFF");
        theadTRun.setText("Texto");
        headerRow.getCell(2).getCTTc().addNewTcPr().addNewShd().setFill("30887d");

        //Time
        headerRow.addNewTableCell();
        XWPFParagraph theadTime = headerRow.getCell(3).addParagraph();
        XWPFRun theadTiRun = theadTime.createRun();
        theadTiRun.setBold(true);
        theadTiRun.setFontSize(12);
        theadTiRun.setFontFamily("Arial");
        theadTiRun.setColor("FFFFFF");
        theadTiRun.setText("Tiempo");
        headerRow.getCell(3).getCTTc().addNewTcPr().addNewShd().setFill("30887d");

        if (workshop.getActividades() == null || workshop.getActividades().isEmpty()){
            XWPFTableRow tableRow = tActividades.createRow();
            tableRow.getCell(0).setText("No hay actividades para este workshop");
        }else{
            int cont = 1;
            for (Actividad a:workshop.getActividades()){
                a.setTiempoTemporal(WorkshopController.obtenerTiempoDuracion(
                        a.getTime().getHour(), a.getTime().getMinute(), a.getTime().getSecond()));
                XWPFTableRow tableRow = tActividades.createRow();
                tableRow.getCell(0).setText(a.getName());
                tableRow.getCell(1).setText(a.getDescription());
                tableRow.getCell(2).setText(a.getText());
                tableRow.getCell(3).setText(a.getTiempoTemporal());

                cont++;
            }

        }

        document.write(response.getOutputStream());
    }



    public String convertTextFileToString(String fileName) {
        try (Stream<String> stream
                     = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {

            return stream.collect(Collectors.joining(" "));
        } catch (IOException | URISyntaxException e) {
            return null;
        }
    }


    public Workshop getWorkshopGenerated(Long id){
        int horas = 0, minutos = 0, segundos = 0;
        Optional<Workshop> workshopOptional = workshopService.get(id);
        if(workshopOptional.isPresent()) {
            Workshop w = workshopOptional.get();
            for (Actividad a : w.getActividades()) {
                LocalTime t = a.getTime();
                horas += t.getHour();
                minutos += t.getMinute();
                segundos += t.getSecond();
            }
            w.setTiempoDuracion(WorkshopController.obtenerTiempoDuracion(horas, minutos, segundos));

            return w;
        }
        return null;
    }


}
