package org.pjr.rulesengine.ui.daos;

import java.util.List;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Attribute;

/**
 * @author Anubhab(Infosys)
 *
 */
public interface AttributeDao {

	/**
	 * Method to insert rows into the PAC_RE_OBJECT_ATTR table.
	 *
	 * @param attributeList
	 * @throws DataLayerException
	 */
	public int[] insertIntoAttribute(List<Attribute> attributeList) throws DataLayerException;

	/**
	 * Method to update the rows of PAC_RE_OBJECT_ATTR table.
	 *
	 * @param attributeList
	 * @return
	 * @throws DataLayerException
	 */
	public boolean updateAttribute(List<Attribute> attributeList) throws DataLayerException;

	/**
	 * Method to delete rows of PAC_RE_OBJECT_ATTR table.
	 *
	 * @param attributeIds
	 * @throws DataLayerException
	 */
	public int[] deleteAttribute(List<String> attributeIds) throws DataLayerException;

	/**
	 * Method to fetch all rows of PAC_RE_OBJECT_ATTR table.
	 * @return
	 * @throws DataLayerException
	 */
	public List<Attribute> fetchAllAttributes() throws DataLayerException;

	/**
	 * Method to fetch a row of PAC_RE_OBJECT_ATTR table.
	 * @param attributeId
	 * @return
	 * @throws DataLayerException
	 */
	public Attribute fetchAttribute(String attributeId) throws DataLayerException;


	/**
	 * @param ruleId
	 * @return
	 * @throws DataLayerException
	 *
	 * @Author Anubhab(Infosys)
	 *
	 */
	public List<Attribute> fetchAttributesAllowedForSubRule(String subruleId) throws DataLayerException;

	/**
	 * @param name
	 * @return
	 * @throws DataLayerException
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 6, 2012
	 */
	public boolean isAttributeNameExists(String name)throws DataLayerException;
	/**
	 * @param value
	 * @return
	 * @throws DataLayerException
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 6, 2012
	 */
	public boolean isAttributeValueExists(String value)throws DataLayerException;

	/**
	 * @param subruleid
	 * @param attributeIds
	 * @param attributeIdsUnassigned
	 * @throws DataLayerException
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 7, 2012
	 */
	public boolean saveAssignAttributesToSubrule(String subruleid, List<String> attributeIds, List<String> attributeIdsUnassigned) throws DataLayerException;

	/**
	 * @param attributeIds
	 * @param subruleId
	 * @return
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 10, 2012
	 */
	public List<String> attributeNamesUsedInSubruleLogic(List<String> attributeIds, String subruleId);

	/**
	 * @param attributeId
	 * @return
	 *
	 * @Author Anubhab(Infosys)
	 * Created: Sep 11, 2012
	 */
	public List<String> fetchSubruleNames(String attributeId);
}
