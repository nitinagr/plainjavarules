/**
 *
 */
package org.pjr.rulesengine.ui.processor.admin;

import java.util.List;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.TechnicalException;

import org.pjr.rulesengine.ui.uidto.AttributeDto;

/**
 * @author Anubhab(Infosys)
 *
 */
public interface AttributeAdminProcessor {
	/**
	 * Method to insert rows into the PAC_RE_OBECT_ATTR table.
	 *
	 * @param attributeList
	 * @throws DataLayerException
	 */
	public int insertAttribute(List<AttributeDto> attributeDtoList) throws TechnicalException;

	/**
	 * Method to update the rows of PAC_RE_OBECT_ATTR table.
	 *
	 * @param attributeList
	 * @return
	 * @throws DataLayerException
	 */
	public boolean updateAttribute(List<AttributeDto> attributeDtoList) throws TechnicalException;

	/**
	 * Method to delete rows of PAC_RE_OBECT_ATTR table.
	 *
	 * @param attributeId
	 * @throws DataLayerException
	 */
	public int deleteAttribute(String attributeId) throws TechnicalException;

	/**
	 * @return
	 * @throws DataLayerException
	 */
	public List<AttributeDto> fetchAllAttributes() throws TechnicalException;

	/**
	 * @param attributeId
	 * @return
	 * @throws DataLayerException
	 */
	public AttributeDto fetchAttribute(String attributeId) throws TechnicalException;

	/**
	 * @param subruleid
	 * @return
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 7, 2012
	 */
	public List<AttributeDto> fetchAttributesAssignedToSubrule(String subruleid)throws TechnicalException;

	/**
	 * @param subruleid
	 * @param attributeIds
	 * @param attributeIdsUnassigned
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 7, 2012
	 */
	public void saveAssignAttributesToSubrule(String subruleid, List<String> attributeIds, List<String> attributeIdsUnassigned)throws TechnicalException;

	/**
	 * @param attributeIds
	 * @param subruleId
	 * @return
	 * @throws TechnicalException
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 10, 2012
	 */
	public List<String> attributesUsedInSubruleLogic(List<String> attributeIds,String subruleId)throws TechnicalException;

	/**
	 * @param attributeId
	 * @return
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 11, 2012
	 */
	public List<String> getSubruleAttrRef(String attributeId);
}
