package services;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.routines.UrlValidator;


@FacesValidator("imgValidator")
public class ImgValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String url = value.toString();
		UrlValidator urlValidator = new UrlValidator();
	    if (!urlValidator.isValid(url)) {
	    	FacesMessage msg = new FacesMessage("Password validation failed", "Invalid Password format");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(msg);
	    }
	}

}
