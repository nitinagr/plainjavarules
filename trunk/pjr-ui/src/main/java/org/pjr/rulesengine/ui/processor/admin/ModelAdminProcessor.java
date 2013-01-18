/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.List;

import org.pjr.rulesengine.TechnicalException;

import org.pjr.rulesengine.ui.uidto.ModelDto;

/**
 * The Interface OperatorAdminProcessor.
 *
 * @author Anubhab
 */

public interface ModelAdminProcessor {

	/**
	 * Method to insert rows into the RE_MODEL_CLASS table.
	 *
	 * @param modelDtoList the Model dto list
	 * @return the int
	 * @throws TechnicalException the technical exception
	 * @author  Anubhab
	 */
	public int insertModel(List<ModelDto> modelDtoList) throws TechnicalException;

	/**
	 * Method to update the rows of RE_MODEL_CLASS table.
	 *
	 * @param modelDtoList the Model dto list
	 * @return true, if successful
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public boolean updateModel(List<ModelDto> modelDtoList) throws TechnicalException;

	/**
	 * Method to delete rows of RE_MODEL_CLASS table.
	 *
	 * @param ModelId the operator id
	 * @return the int
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public int deleteModel(String modelId) throws TechnicalException;

	/**
	 * Fetch all Models.
	 *
	 * @return the list
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public List<ModelDto> fetchAllModels() throws TechnicalException;

	/**
	 * Fetch operator.
	 *
	 * @param string the operator id
	 * @return the Model dto
	 * @throws TechnicalException the technical exception
	 * @author  Sudhakar (Infosys)
	 */
	public ModelDto fetchModel(String string) throws TechnicalException;

	public ModelDto isModelAlreadyExists(String name) throws TechnicalException;
	
	public List<String> fetchAttributesByName(String modelId) throws TechnicalException;
	
	public List<String> fetchSubrulesByName(String modelId) throws TechnicalException;
	
	public List<String> fetchRulesByName(String modelId) throws TechnicalException;
}
