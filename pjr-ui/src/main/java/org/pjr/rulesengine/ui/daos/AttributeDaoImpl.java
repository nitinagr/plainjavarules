package org.pjr.rulesengine.ui.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Attribute;
import org.pjr.rulesengine.util.AccessProperties;
import org.pjr.rulesengine.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

@Component(value="attributeDao")
public class AttributeDaoImpl implements AttributeDao {

	private static final Log log = LogFactory.getLog(AttributeDaoImpl.class);

	@Autowired
	@Qualifier("pjrJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
    @Qualifier("pjrNamedJdbcTemplatePac")
	private NamedParameterJdbcTemplate namedJdbcTemplatePac;

	@Autowired
	private AccessProperties accessProps;

	/*
	 * (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#insertAttribute(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public int[] insertIntoAttribute(final List<Attribute> attributeList) throws DataLayerException {

		log.debug("Entering insertAttribute..");
		int[] i=null;
		try {
			if (null != attributeList && attributeList.size() != 0){
				i=jdbcTemplate.batchUpdate(accessProps.getFromProps(CommonConstants.QUERY_INSERTINTOATTRIBUTE_INSERT), new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int count) throws SQLException {
						//Setting the values to the prepared statement
						ps.setString(1, attributeList.get(count).getName());
						ps.setString(2, attributeList.get(count).getValue());
						ps.setString(3, attributeList.get(count).getModelId());

					}

					@Override
					public int getBatchSize() {
						return attributeList.size();
					}
				});
			} else {
				log.info("List is empty");
				throw new DataLayerException("List is empty");
			}
			log.debug("Exit: insertAttribute");
		} catch(Exception e) {
			log.error("Error in insertAttribute",e);
			throw new DataLayerException(e);
		}
		return i;
	}

	/*
	 * (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#updateAttribute(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public boolean updateAttribute(final List<Attribute> attributeList) throws DataLayerException {

		log.info("Inside updateAttribute..");
		boolean updated=false;

		int[] i=null;

		String sql=accessProps.getFromProps(CommonConstants.QUERY_UPDATEATTRIBUTE_UPDATE);

		log.debug("Created the params");
		try{
			i=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int count) throws SQLException {

					//Setting the values to the prepared statement
					ps.setString(1, attributeList.get(count).getName());
					ps.setString(2, attributeList.get(count).getValue());
					ps.setString(3, attributeList.get(count).getId());					

				}

				@Override
				public int getBatchSize() {
					return attributeList.size();
				}
			});
		}catch(Exception e){
			log.error("Error in updateAttribute", e);
			throw new DataLayerException(e);
		}
		if(null!=i && i.length!=0){
			updated=true;
		}
		log.info("Exit: updateAttribute");
		return updated;
	}

	/*
	 * (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#deleteAttribute(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public int[] deleteAttribute(final List<String> attributeId) throws DataLayerException {

		log.info("Entering deleteAttribute..");
		int[] i=null;
		try {
			i=jdbcTemplate.batchUpdate(accessProps.getFromProps(CommonConstants.QUERY_DELETEATTRIBUTE_DELETE), new BatchPreparedStatementSetter() {

				/* Getting the batch size */
				@Override
				public int getBatchSize() {
					return attributeId.size();
				}

				/* Setting the values into Prepared statement */
				@Override
				public void setValues(PreparedStatement ps, int count)throws SQLException {
					ps.setString(1, attributeId.get(count));
				}
			});
		} catch(Exception e) {
			log.error("Error in deleteAttribute",e);
			throw new DataLayerException(e);
		}
		log.info("Exit: deleteAttribute");
		return i;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Attribute> fetchAllAttributes() throws DataLayerException {
		log.debug("Entering fetchAllAttribute..");

		List<Attribute> attributeList=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLATTRIBUTES_SELECT);
		try{
			attributeList=jdbcTemplate.query(sql,new RowMapper(){
				public Object mapRow(ResultSet rs, int count) throws SQLException{
					Attribute temp=new Attribute();
					temp.setId(rs.getString("ATTR_ID"));
					temp.setName(rs.getString("ATTR_NAME"));
					temp.setValue(rs.getString("ATTR_VALUE"));
					temp.setModelId(rs.getString("MODEL_ID"));
					return temp;
				}
			});
			if(null==attributeList) throw new DataLayerException("Some error while fetching attribute details");
			log.info("Successfully fetched attribute details.");
		}catch(Exception e) {
			log.error("Error in fetchAllAttribute",e);
			throw new DataLayerException(e);
		}
		log.info("Exit: fetchAllAttribute");
		return attributeList;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Attribute> fetchAllAttributes(String modelId) throws DataLayerException {
		log.debug("Entering fetchAllAttribute..");

		List<Attribute> attributeList=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLATTRIBUTES_MODEL_ID_SELECT);
		try{
			attributeList=jdbcTemplate.query(sql,new Object[]{modelId} ,new RowMapper(){
				public Object mapRow(ResultSet rs, int count) throws SQLException{
					Attribute temp=new Attribute();
					temp.setId(rs.getString("ATTR_ID"));
					temp.setName(rs.getString("ATTR_NAME"));
					temp.setValue(rs.getString("ATTR_VALUE"));
					temp.setModelId(rs.getString("MODEL_ID"));
					return temp;
				}
			});
			if(null==attributeList) throw new DataLayerException("Some error while fetching attribute details");
			log.info("Successfully fetched attribute details.");
		}catch(Exception e) {
			log.error("Error in fetchAllAttribute",e);
			throw new DataLayerException(e);
		}
		log.info("Exit: fetchAllAttribute");
		return attributeList;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Attribute fetchAttribute(String attributeId) throws DataLayerException {
		log.info("Entering fetchAttribute..");
		Attribute attr=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHATTRIBUTE_SELECT);
		try{
			//This will return null if no rows are fetched
			attr=jdbcTemplate.query(sql, new Object[]{attributeId}, new ResultSetExtractor<Attribute>(){

				@Override
				public Attribute extractData(ResultSet rs) throws SQLException, DataAccessException {
					Attribute temp=null;
					while(rs.next()){
						temp=new Attribute();
						temp.setId(rs.getString("ATTR_ID"));
						temp.setName(rs.getString("ATTR_NAME"));
						temp.setValue(rs.getString("ATTR_VALUE"));
						temp.setModelId(rs.getString("MODEL_CLASS_NAME"));
					}
					return temp;
				}

			});
			//This will return exception if rows are not fetched
			/*attr=(Attribute)jdbcTemplate.queryForObject(sql, new Object[]{attributeId}, new RowMapper(){
				public Object mapRow(ResultSet rs, int count) throws SQLException{
					Attribute temp=new Attribute();
					temp.setId(rs.getString("ATTR_ID"));
					temp.setName(rs.getString("ATTR_NAME"));
					temp.setValue(rs.getString("ATTR_VALUE"));
					return temp;
				}
			});*/
			//if(null==attr) throw new DataLayerException("Some error while fetching single attribute details");
		}catch(Exception e) {
			log.error("Error in fetchAttribute",e);
			throw new DataLayerException(e);
		}
		log.debug("Attribute fetch for id:"+attributeId+ " is :"+attr);
		log.info("Exit: fetchAttribute");
		return attr;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Attribute> fetchAttributesAllowedForSubRule(String subruleId) {
		log.info("Fetching operator for the subrule:"+subruleId);
		List<Attribute> attributes=null;
		//String sql="select sam.ATTR_ID,atr.ATTR_NAME,atr.ATTR_VALUE from PAC_RE_SUBRULE_ATTR_MAPPING sam join PAC_RE_OBECT_ATTR atr on sam.ATTR_ID = atr.ATTR_ID  where sam.SUBRULE_ID =?";
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHATTRIBUTESALLOWEDFORSUBRULE_SELECT);

		attributes = jdbcTemplate.query(sql, new Object[]{subruleId}, new ResultSetExtractor<List<Attribute>>() {

			@Override
			public List<Attribute> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Attribute> attributeList = new ArrayList<Attribute>();
				while(rs.next()){
					Attribute o = new Attribute();
					o.setId(rs.getString("ATTR_ID"));
					o.setName(rs.getString("ATTR_NAME"));
					o.setValue(rs.getString("ATTR_VALUE"));
					//o.setModelId(rs.getString("MODEL_ID"));

					attributeList.add(o);
				}
				return attributeList;
			}

		});
		return attributes;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#isAttributeNameExists(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public boolean isAttributeNameExists(String name) throws DataLayerException {
		log.debug("Entered isAttributeNameExists method");
		boolean result=false;
		//String sql="select COUNT(*) from FSMMGR.PAC_RE_OBECT_ATTR where ATTR_NAME=?";
		String sql=accessProps.getFromProps(CommonConstants.QUERY_ISATTRIBUTENAMEEXISTS_SELECT);
		try{
			int i=jdbcTemplate.queryForInt(sql, new Object[]{name});
			if(i>0) result=true;
		} catch(Exception e){
			throw new DataLayerException("Error in isAttributeNameExists method",e);
		}
		log.info("Checking Attribute name in DB:"+name+",Found="+result);
		log.debug("Exiting isAttributeNameExists method");
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#isAttributeValueExists(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public boolean isAttributeValueExists(String value) throws DataLayerException {
		log.debug("Entered isAttributeValueExists method");
		boolean result=false;
		//String sql="select COUNT(*) from FSMMGR.PAC_RE_OBECT_ATTR where ATTR_VALUE=?";
		String sql=accessProps.getFromProps(CommonConstants.QUERY_ISATTRIBUTEVALUEEXISTS_SELECT);
		try{
			int i=jdbcTemplate.queryForInt(sql, new Object[]{value});
			if(i>0) result=true;
		} catch(Exception e){
			throw new DataLayerException("Error in isAttributeValueExists method",e);
		}
		log.info("Checking Attribute value in DB:"+value+",Found="+result);
		log.debug("Exiting isAttributeValueExists method");
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#saveAssignAttributesToSubrule(java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public boolean saveAssignAttributesToSubrule(final String subruleId,final List<String> attributeIds,final List<String> attributeIdsUnassigned) throws DataLayerException {
		boolean result=true;

		if(StringUtils.isBlank(subruleId)) throw new DataLayerException("Subrule Id is null");

		try {
			//delete the mappings first before inserting
			if(null!=attributeIdsUnassigned && attributeIdsUnassigned.size()>0){
				log.info("Deleteing mapping for specific attributes for subrule :"+subruleId);
				//String deleteMappingSql = "delete from PAC_RE_SUBRULE_ATTR_MAPPING where SUBRULE_ID =:subruleId and ATTR_ID IN (:attrIds) ";
				String deleteMappingSql=accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNATTRIBUTESTOSUBRULE_DELETE);
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("subruleId", subruleId);
				paramMap.put("attrIds", attributeIdsUnassigned);
				namedJdbcTemplatePac.update(deleteMappingSql, paramMap);
				log.debug("Subrule attr mapping deleted successfully for subrule id: "+subruleId);
			}
			//insert
			//String insertMappingSql="insert into PAC_RE_SUBRULE_ATTR_MAPPING values(FSMMGR.SUBRULE_ATTR_MAPPING_PK_SQ.NEXTVAL,?,?)";
			String insertMappingSql=accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNATTRIBUTESTOSUBRULE_INSERT);
			if(null!=attributeIds && attributeIds.size()>0){
				log.debug("");
				int []i=jdbcTemplate.batchUpdate(insertMappingSql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {
						String attributeid = attributeIds.get(index);

						ps.setString(1, subruleId);
						ps.setString(2, attributeid);
					}

					@Override
					public int getBatchSize() {
						return attributeIds.size();
					}
				});
				if(null==i) result=false;
				else log.debug("Operator subrule mapping saved successfully");
			}
		} catch (Exception e) {
			throw new DataLayerException(e);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#attributeNamesUsedInSubruleLogic(java.util.List, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> attributeNamesUsedInSubruleLogic(List<String> attributeIds, String subruleId) {
		log.info("Entered attributeNamesUsedInSubruleLogic method");
		List<String> attributeNames=new ArrayList<String>();
		String sql=accessProps.getFromProps(CommonConstants.QUERY_ATTRIBUTENAMESUSEDINSUBRULELOGIC_SELECT);
		/*String sql="select atr.ATTR_NAME "
					+"from PAC_RE_SUBRULE_ATTR_MAPPING sam join PAC_RE_SUBRULE_LOGIC sl "
					+"on sl.SUBRULE_ATTR_ID=sam.SUBRULE_ATTR_ID "
					+"and sl.SUBRULE_ID=sam.SUBRULE_ID "
					+"join PAC_RE_OBECT_ATTR atr on sam.ATTR_ID=atr.ATTR_ID "
					+"where sam.SUBRULE_ID=:subruleId and sam.ATTR_ID IN (:attributeIds) ";*/

		HashMap< String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("attributeIds", attributeIds);
		paramMap.put("subruleId", subruleId);
		attributeNames=namedJdbcTemplatePac.query(sql, paramMap, new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> atrNames =  new ArrayList<String>();

				while (rs.next()) {
					atrNames.add(rs.getString(1));
				}
				return atrNames;
			}
		});
		log.info("Exiting attributeNamesUsedInSubruleLogic method");
		return attributeNames;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.ui.daos.AttributeDao#fetchSubruleNames(long)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> fetchSubruleNames(String attributeId) {
		log.debug("Trying to fetch Subrules using attributeId:" +attributeId);
		List<String> subruleNames=new ArrayList<String>();
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHSUBRULENAMES_SELECT);
		/*String sql="select s.SUBRULE_NAME "
			+"from PAC_RE_SUBRULE s join PAC_RE_SUBRULE_ATTR_MAPPING sa "
			+"on s.SUBRULE_ID=sa.SUBRULE_ID "
			+"where sa.ATTR_ID=?";*/

		subruleNames=jdbcTemplate.query(sql, new Object[]{attributeId},new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> sruleNames =  new ArrayList<String>();

				while (rs.next()) {
					sruleNames.add(rs.getString(1));
				}
				return sruleNames;
			}
		});
		log.info("Attribute id: "+attributeId+" is used by these subrules :"+subruleNames);
		return subruleNames;
	}
}
