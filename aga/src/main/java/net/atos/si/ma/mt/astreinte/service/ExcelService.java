package net.atos.si.ma.mt.astreinte.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import org.primefaces.model.StreamedContent;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;

public interface ExcelService extends Serializable {
	static final String FILE_PATH = "/ASTREINTE.xlsx";
	// static final String FILE_PATH_TMP = "Astreinte.temp.xlsx";
	static final String FILE_PATH_R = "/ASTREINTE-RES.xlsx";
	static final String FILE_PATH_R_STT = "/ASTREINTE-RES-STT.xlsx";
	// static final String FILE_PATH_R_TMP = "ASTREINTE-RES.temp.xlsx";
	static final int QCREFROW = 5;
	static final int QCREFRCOL = 4;
	static final int ASTRROW = 11;
	static final int ASTRCOL = 0;

	static final int R_QCREFROW = 14;
	static final int R_QCREFRCOL = 3;
	static final int R_ASTRROW = 23;
	static final int R_ASTRCOL = 2;

	static final SimpleDateFormat formater = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm");

	StreamedContent  generateExcelAstrinte(Astreinte astreinte) throws Exception;
	StreamedContent  generateExcelForRessource(List<Intervention> interventions) throws Exception ;
	StreamedContent  generateExcelForRessourceSTT(Long idAstreinte,
	Integer idUser) throws Exception ;

}
