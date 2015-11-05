package net.atos.si.ma.mt.astreinte.web.managed;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import net.atos.si.ma.mt.astreinte.model.Parameter;
import net.atos.si.ma.mt.config.dao.GenericDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("pconverter")
@Scope("session")
public class ParameterConverter implements Converter {
	@Autowired
	@Qualifier("parameterDAO")
	private GenericDAO<Parameter, Long> parameterDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		try {
			return parameterDAO.load(Long.decode(value));
		} catch (Throwable e) {
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value instanceof Parameter) {
			Parameter parameter = (Parameter) value;
			return parameter.getId().toString();
		}
		return null;
	}

}
