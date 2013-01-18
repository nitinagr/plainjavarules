/**
 * @author Anubhab
 * Created: Jan 7, 2013
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.daos.ModelClassDao;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.ui.uidto.ModelDto;
import org.pjr.rulesengine.ui.uidto.ViewDtoTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The ModelAdminProcessorImpl.
 *
 * @author Anubhab(Infosys)
 *
 */
@Component(value="modelAdminProcessor")
@Transactional(propagation=Propagation.REQUIRED)
public class ModelAdminProcessorImpl implements ModelAdminProcessor {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(ModelAdminProcessorImpl.class);
			
	@Autowired
	private ModelClassDao modelDao;
	
	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor#insertModel(java.util.List)
	 */
	@Override
	public int insertModel(List<ModelDto> modelDtoList) throws TechnicalException {
		log.debug("Entered insertModel processor");
		List<Model> models=new ArrayList<Model>();
		if(null!=modelDtoList && !modelDtoList.isEmpty()){
			for(ModelDto temp:modelDtoList){
				Model model=ViewDtoTransformer.convertFromUI(temp);
				models.add(model);
			}
			int j=0;
			int[] i=modelDao.insertIntoModelClass(models);
			if(null!=i && i.length!=0){
				j=i.length;
			}
			return j;
		} else {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor#updateModel(java.util.List)
	 */
	@Override
	public boolean updateModel(List<ModelDto> modelDtoList) throws TechnicalException {
		log.debug("Entered updateModel processor");
		boolean result=false;
		if(null!=modelDtoList && !modelDtoList.isEmpty()){
			List<Model> models=new ArrayList<Model>();
			
			for(ModelDto temp:modelDtoList){
				Model model=ViewDtoTransformer.convertFromUI(temp);
				models.add(model);
			}
			result=modelDao.updateModels(models);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor#deleteModel(java.lang.String)
	 */
	@Override
	public int deleteModel(String modelId) throws TechnicalException {
		log.debug("Entered deleteModel processor");
		if(!StringUtils.isBlank(modelId)){
			List<String> modelIds=new ArrayList<String>();
			modelIds.add(modelId);
			int j=0;
			int[] i=modelDao.deleteFromModel(modelIds);
			if(null!=i && i.length!=0){
				j=i.length;
			}
			return j;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor#fetchAllModels()
	 */
	@Override
	public List<ModelDto> fetchAllModels() throws TechnicalException {
		log.debug("Entered fetchAllModels processor");
		List<Model> listt=modelDao.fetchAllModels();
		List<ModelDto> returningList=new ArrayList<ModelDto>();
		for(Model temp:listt){
			ModelDto dto=ViewDtoTransformer.convertToUI(temp);
			returningList.add(dto);
		}
		return returningList;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.processor.admin.ModelAdminProcessor#fetchModel(java.lang.String)
	 */
	@Override
	public ModelDto fetchModel(String string) throws TechnicalException {
		log.debug("Entered fetchModel processor");
		if(!StringUtils.isEmpty(string)){
			Model db=modelDao.fetchModel(string);
			ModelDto dto=ViewDtoTransformer.convertToUI(db);
			return dto;
		}
		return null;
	}

	@Override
	public ModelDto isModelAlreadyExists(String name) throws TechnicalException {
		log.debug("Entered isModelAlreadyExists processor");
		if(!StringUtils.isEmpty(name)){
			Model db=modelDao.isModelNameAlreadyExists(name);
			ModelDto dto=ViewDtoTransformer.convertToUI(db);
			return dto;
		}
		return null;
	}

	@Override
	public List<String> fetchAttributesByName(String modelId) throws TechnicalException  {
		List<String> attrNames=modelDao.fetchAttributeNames(modelId);
		return attrNames;
	}

	@Override
	public List<String> fetchSubrulesByName(String modelId) throws TechnicalException {
		List<String> subNames=modelDao.fetchSubruleNames(modelId);
		return subNames;
	}

	@Override
	public List<String> fetchRulesByName(String modelId) throws TechnicalException {
		List<String> ruleNames=modelDao.fetchRuleNames(modelId);
		return ruleNames;
	}

}
