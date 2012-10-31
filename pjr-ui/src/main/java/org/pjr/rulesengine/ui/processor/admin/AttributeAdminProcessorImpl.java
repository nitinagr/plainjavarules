/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.dbmodel.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.pjr.rulesengine.ui.daos.AttributeDao;
import org.pjr.rulesengine.ui.uidto.AttributeDto;

/**
 * @author Anubhab(Infosys)
 *
 */
@Component(value="attributeAdminProcessor")
@Transactional(propagation=Propagation.REQUIRED)
public class AttributeAdminProcessorImpl implements AttributeAdminProcessor {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(AttributeAdminProcessorImpl.class);

	@Autowired
	private AttributeDao attributeDao;

	@Override
	public int insertAttribute(List<AttributeDto> attributeDtoList) throws TechnicalException {
		log.debug("Entering processor's insertAttribute..");
		int[] i=null;
		int rows=0;
		List<Attribute> attributeList=new ArrayList<Attribute>();

		if(null!=attributeDtoList && !attributeDtoList.isEmpty()){
			log.debug("Converting UITO object to DTO object");
			for(AttributeDto temp:attributeDtoList){
				Attribute op=DataTransformer.convert(temp);
				attributeList.add(op);
			}
			try{
				i=attributeDao.insertIntoAttribute(attributeList);
				rows=i.length;
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return rows;
	}
	@Override
	public boolean updateAttribute(List<AttributeDto> attributeDtoList) throws TechnicalException {
		log.debug("Entering processor's updateAttribute..");
		boolean result=false;
		List<Attribute> attributeList=new ArrayList<Attribute>();

		if(null!=attributeDtoList && !attributeDtoList.isEmpty()){
			log.debug("Converting UITO object to DTO object");
			for(AttributeDto temp:attributeDtoList){
				Attribute op=DataTransformer.convert(temp);
				attributeList.add(op);
			}
			try{
				result=attributeDao.updateAttribute(attributeList);
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return result;
	}
	@Override
	public int deleteAttribute(String attributeId) throws TechnicalException {
		log.debug("Entering processor's deleteAttribute..");
		int[] i=null;
		int rows=0;
		List<String> attributeIds=new ArrayList<String>();
		attributeIds.add(attributeId);
		if(null!=attributeIds && attributeIds.size()>0){
			try{
				i=attributeDao.deleteAttribute(attributeIds);
				if(null!=i)	rows=i.length;
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return rows;
	}
	@Override
	public List<AttributeDto> fetchAllAttributes() throws TechnicalException {
		log.debug("Entering processor's fetchAllAttributes..");
		List<AttributeDto> opToList=new ArrayList<AttributeDto>();
		List<Attribute> opList=null;
		try{
			opList = attributeDao.fetchAllAttributes();
			if(null!=opList && opList.size()>0) {
				for(Attribute temp:opList){
					opToList.add(DataTransformer.convertToUI(temp));
				}
			}
		}catch(Exception e){
			throw new TechnicalException(e);
		}
		return opToList;
	}
	@Override
	public AttributeDto fetchAttribute(String attributeId) throws TechnicalException {
		log.debug("Entering processor's fetchAllAttributes..");
		AttributeDto attributeDto=null;
		Attribute attribute=null;
		if(null!=attributeId && !attributeId.isEmpty()){
			try{
				attribute=attributeDao.fetchAttribute(attributeId);
				if(null!=attribute) attributeDto=DataTransformer.convertToUI(attribute);
			}catch(Exception e){
				throw new TechnicalException(e);
			}
		}
		return attributeDto;
	}
	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.AttributeAdminProcessor#fetchAttributesAssignedToSubrule(java.lang.String)
	 */
	@Override
	public List<AttributeDto> fetchAttributesAssignedToSubrule(String subruleid) throws TechnicalException {
		log.debug("Entered fetchAttributesAssignedToSubrule processor");

		List<AttributeDto> attributeDtos=new ArrayList<AttributeDto>();
		List<Attribute> attributes=attributeDao.fetchAttributesAllowedForSubRule(subruleid);
		attributeDtos=DataTransformer.convertAttributesToUI(attributes);
		return attributeDtos;
	}
	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.AttributeAdminProcessor#saveAssignAttributesToSubrule(java.lang.String, java.util.List)
	 */
	@Override
	public void saveAssignAttributesToSubrule(String subruleid, List<String> attributeIds,List<String> attributeIdsUnassigned) throws TechnicalException {
		attributeDao.saveAssignAttributesToSubrule(subruleid,attributeIds,attributeIdsUnassigned);
	}
	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.AttributeAdminProcessor#attributesUsedInSubruleLogic(java.util.List, java.lang.String)
	 */
	@Override
	public List<String> attributesUsedInSubruleLogic(List<String> attributeIds, String subruleId) throws TechnicalException {
		log.info("Entered attributesUsedInSubruleLogic method in processor");
		List<String> result=new ArrayList<String>();
		if(attributeIds.size()>0) result= attributeDao.attributeNamesUsedInSubruleLogic(attributeIds,subruleId);
		log.info("Exiting attributesUsedInSubruleLogic method in processor");
		return result;
	}
	/* (non-Javadoc)
	 * @see com.cablevision.pac.scheduler.rulesEngine.common.processor.admin.AttributeAdminProcessor#getSubruleAttrRef(long)
	 */
	@Override
	public List<String> getSubruleAttrRef(String attributeId) {
		List<String> subruleNames=new ArrayList<String>();
		subruleNames=attributeDao.fetchSubruleNames(attributeId);
		return subruleNames;
	}
}
