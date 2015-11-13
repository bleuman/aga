package net.atos.si.ma.mt.astreinte.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import net.atos.si.ma.mt.astreinte.model.Astreinte;
import net.atos.si.ma.mt.astreinte.model.Intervention;
import net.atos.si.ma.mt.astreinte.model.Parameter;
import net.atos.si.ma.mt.astreinte.model.STTIntervention;
import net.atos.si.ma.mt.astreinte.model.Utilisateur;
import net.atos.si.ma.mt.astreinte.service.ExcelService;
import net.atos.si.ma.mt.config.dao.GenericDAO;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExcelServiceImpl implements ExcelService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8960084337336221751L;

	@Autowired
	@Qualifier("parameterDAO")
	private GenericDAO<Parameter, Long> parameterDAO;

	@Autowired
	@Qualifier("utilisateurDAO")
	private GenericDAO<Utilisateur, Integer> utilisateurDAO;

	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	@Override
	public StreamedContent generateExcelAstrinte(Astreinte astreinte)
			throws Exception {

		Workbook workbook = WorkbookFactory.create(this.getClass()
				.getResourceAsStream(FILE_PATH));

		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
		Sheet sheet = workbook.getSheetAt(0);

		int actualRow = ASTRROW;
		boolean qcset = false;
		boolean resset = false;
		String qc = "";
		String res = "";
		sheet.getRow(QCREFROW).getCell(QCREFRCOL)
				.setCellValue(astreinte.getRef());
		sheet.getRow(QCREFROW - 4).getCell(QCREFRCOL)
				.setCellValue(astreinte.getEntite());
		sheet.getRow(QCREFROW - 1).getCell(QCREFRCOL)
				.setCellValue(astreinte.getDemandeur());
		for (Intervention intervention : astreinte.getInterventions()) {

			if (!resset) {

				res = intervention.getUtilisateur().getNom()
						.replaceAll(" ", "_");

				resset = true;
			}

			Date d = intervention.getDateD();
			Date f = intervention.getDateF();

			sheet.getRow(actualRow)
					.getCell(ASTRCOL)
					.setCellValue(
							intervention.getTypeIntervention().getId()
									.equals(25l) ? "non" : "oui");

			sheet.getRow(actualRow).getCell(ASTRCOL + 1)
					.setCellValue(intervention.getUtilisateur().getNom());
			sheet.getRow(actualRow).getCell(ASTRCOL + 2)
					.setCellValue(intervention.getUtilisateur().getPrenom());

			sheet.getRow(actualRow).getCell(ASTRCOL + 3).setCellValue(d);
			sheet.getRow(actualRow).getCell(ASTRCOL + 4).setCellValue(f);

			evaluator.evaluateFormulaCell(sheet.getRow(actualRow).getCell(
					ASTRCOL + 3));
			evaluator.evaluateFormulaCell(sheet.getRow(actualRow).getCell(
					ASTRCOL + 4));
			evaluator.evaluateFormulaCell(sheet.getRow(actualRow).getCell(
					ASTRCOL + 5));

			actualRow++;
		}

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");
		externalContext
				.setResponseHeader("Content-Disposition",
						"attachment;filename=Astreinte_" + astreinte.getRef()
								+ ".xlsx");
		// workbook.write(externalContext.getResponseOutputStream());
		// context.responseComplete(); // Prevent JSF from performing
		// navigation.
		// externalContext.getResponseOutputStream().close();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
				outputStream.toByteArray());
		return new DefaultStreamedContent(arrayInputStream,
				"application/vnd.ms-excel", "Astreinte_" + astreinte.getRef()
						+ ".xlsx");

	}

	@Override
	public StreamedContent generateExcelForRessource(
			List<Intervention> interventions) throws Exception {
		Workbook workbook = WorkbookFactory.create(this.getClass()
				.getResourceAsStream(FILE_PATH_R));

		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
		Sheet sheet = workbook.getSheetAt(0);

		int actualRow = R_ASTRROW;
		boolean qcset = false;
		boolean resset = false;
		String qc = "";
		String res = "";
		for (Intervention intervention : interventions) {
			if (!qcset) {

				qc = intervention.getAstreinte().getRef();
				sheet.getRow(R_QCREFROW).getCell(R_QCREFRCOL).setCellValue(qc);

				qcset = true;
			}
			if (!resset) {

				res = intervention.getUtilisateur().getNom()
						.replaceAll(" ", "_");
				sheet.getRow(8)
						.getCell(3)
						.setCellValue(
								intervention.getUtilisateur().getPrenom()
										+ " "
										+ intervention.getUtilisateur()
												.getNom());
				sheet.getRow(7).getCell(3)
						.setCellValue(intervention.getUtilisateur().getDas());

				resset = true;
			}

			Date d = intervention.getDateD();
			Date f = intervention.getDateF();

			sheet.getRow(actualRow)
					.getCell(R_ASTRCOL)
					.setCellValue(
							intervention.getTypeIntervention().getStringValue());

			sheet.getRow(actualRow).getCell(R_ASTRCOL + 1).setCellValue(d);
			sheet.getRow(actualRow).getCell(R_ASTRCOL + 2).setCellValue(f);

			actualRow++;
		}
		//
		// FacesContext context = FacesContext.getCurrentInstance();
		// ExternalContext externalContext = context.getExternalContext();
		// externalContext.responseReset();
		// externalContext.setResponseContentType("application/vnd.ms-excel");
		// externalContext.setResponseHeader("Content-Disposition",
		// "attachment;filename=Astreinte_" + res + "_" + qc + ".xlsx");
		// workbook.write(externalContext.getResponseOutputStream());
		// context.responseComplete();
		// externalContext.getResponseOutputStream().close();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
				outputStream.toByteArray());
		return new DefaultStreamedContent(arrayInputStream,
				"application/vnd.ms-excel", "Astreinte_" + res + "_" + qc
						+ ".xlsx");

	}

	@Override
	public StreamedContent generateExcelForRessourceSTT(Long idAstreinte,
			Integer idUser) throws Exception {
		Workbook workbook = WorkbookFactory.create(this.getClass()
				.getResourceAsStream(FILE_PATH_R_STT));

		Sheet sheet = workbook.getSheetAt(0);

		int actualRow = R_ASTRROW;
		boolean qcset = false;
		boolean resset = false;
		String qc = "";
		String res = "";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", idUser);
		params.put("astreinte", idAstreinte);
		List<Object> interventionsSTTs = parameterDAO.executeNamedQuery(
				"sttintervention-by-user-astreinte", params);
		for (Object object : interventionsSTTs) {
			if (object instanceof STTIntervention) {
				STTIntervention sttIntervention = (STTIntervention) object;

				System.out.println(sttIntervention);
				if (!qcset) {

					qc = sttIntervention.getId().getRef();
					sheet.getRow(R_QCREFROW).getCell(R_QCREFRCOL)
							.setCellValue(qc);

					qcset = true;
				}
				if (!resset) {
					Utilisateur utilisateur = utilisateurDAO.load(idUser);
					res = utilisateur.getNom().replaceAll(" ", "_");
					sheet.getRow(8)
							.getCell(3)
							.setCellValue(
									utilisateur.getPrenom() + " "
											+ utilisateur.getNom());
					sheet.getRow(7).getCell(3)
							.setCellValue(utilisateur.getDas());

					resset = true;
				}
				sheet.getRow(actualRow).getCell(R_ASTRCOL-1)
				.setCellValue(sttIntervention.getId().getRef());
				sheet.getRow(actualRow).getCell(R_ASTRCOL)
						.setCellValue(sttIntervention.getId().getJi());
				sheet.getRow(actualRow).getCell(R_ASTRCOL + 1)
						.setCellValue(sttIntervention.getId().getTypelib());

				sheet.getRow(actualRow).getCell(R_ASTRCOL + 2)
						.setCellValue(timeFormat.format(sttIntervention.getId().getSintd()));
				sheet.getRow(actualRow).getCell(R_ASTRCOL + 3)
						.setCellValue(timeFormat.format(sttIntervention.getId().getSintf()));

				sheet.getRow(actualRow).getCell(R_ASTRCOL + 4)
						.setCellValue(timeFormat.format(sttIntervention.getId().getDureesi()));
				sheet.getRow(actualRow).getCell(R_ASTRCOL + 5)
						.setCellValue(sttIntervention.getId().getRate());
				sheet.getRow(actualRow).getCell(R_ASTRCOL + 6)
						.setCellValue(sttIntervention.getId().getChargesi());
				actualRow++;
			}
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
				outputStream.toByteArray());
		return new DefaultStreamedContent(arrayInputStream,
				"application/vnd.ms-excel", "Astreinte_STT_" + res + "_" + qc
						+ ".xlsx");

	}

}
