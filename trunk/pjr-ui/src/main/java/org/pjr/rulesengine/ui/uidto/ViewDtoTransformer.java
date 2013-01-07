package org.pjr.rulesengine.ui.uidto;

import org.pjr.rulesengine.dbmodel.Model;
import org.springframework.stereotype.Component;

/**
 * The Class ViewDtoTransformer.
 *
 * @author Sudhakar
 */
@Component
public class ViewDtoTransformer {
	public static ModelDto convertToUI(Model model){
		ModelDto dto=null;
		if(null!=model){
			dto=new ModelDto();
			dto.setModel_id(model.getModel_id());
			dto.setModel_class_name(model.getModel_class_name());
		}
		return dto;
	}
	public static Model convertFromUI(ModelDto model){
		Model dbo=null;
		if(null!=model){
			dbo=new Model();
			dbo.setModel_id(model.getModel_id());
			dbo.setModel_class_name(model.getModel_class_name());
		}
		return dbo;
	}
}
