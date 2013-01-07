package org.pjr.rulesengine.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Attribute;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.dbmodel.SubruleLogic;
import org.pjr.rulesengine.util.AccessProperties;
import org.pjr.rulesengine.util.CommonConstants;

/**
 * The Class SubruleDaoImpl.
 *
 * @author Sudhakar
 */
@Component(value="subruleDao")
public class SubruleDaoImpl implements SubruleDao{

	/** The jdbc template. */
	@Autowired
	@Qualifier("pjrJdbcTemplate")
	private JdbcTemplate jdbcTemplate;


	/** The named jdbc template pac. */
	@Autowired
    @Qualifier("pjrNamedJdbcTemplatePac")
	private NamedParameterJdbcTemplate namedJdbcTemplatePac;

	/** The access props. */
	@Autowired
	private AccessProperties accessProps;

	/** The Constant log. */
	static final Logger log = Logger.getLogger(SubruleDaoImpl.class);

	/**
	 * Gets the jdbc template.
	 *
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * Sets the jdbc template.
	 *
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/**
	 * Gets the named parameter jdbc template.
	 *
	 * @return the namedJdbcTemplatePac
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedJdbcTemplatePac;
	}

	/**
	 * Sets the named parameter jdbc template.
	 *
	 * @param namedJdbcTemplatePac the namedJdbcTemplatePac to set
	 */
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedJdbcTemplatePac) {
		this.namedJdbcTemplatePac = namedJdbcTemplatePac;
	}

	/**
	 * Gets the access props.
	 *
	 * @return the accessProps
	 */
	public AccessProperties getAccessProps() {
		return accessProps;
	}

	/**
	 * Sets the access props.
	 *
	 * @param accessProps the accessProps to set
	 */
	public void setAccessProps(AccessProperties accessProps) {
		this.accessProps = accessProps;
	}

	/*
	 * (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#insertSubrule(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public int[] insertSubrule(final List<Subrule> subruleList) throws DataLayerException {
		//log.debug("Entering insertSubrule..");
		int[] i=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_INSERTSUBRULE_INSERT);
		//String sql="INSERT INTO FSMMGR.PAC_RE_SUBRULE(SUBRULE_ID,SUBRULE_NAME,SUBRULE_DESCRIPTION,DEFAULT_VALUE,ACTIVE) VALUES(PAC_SUBRULES_PK_SQ.NEXTVAL,?,?,?,?)";
		try {
			if (null != subruleList && subruleList.size() != 0){
				i=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int count) throws SQLException {
						Subrule subrule = subruleList.get(count);
						int active;
						if(subrule.isActive()) active=1;
						else active=0;

						int def;
						if(subrule.isDefaultValue()) def=1;
						else def=0;
						//Setting the values to the prepared statement
						ps.setString(1, subrule.getName());
						ps.setString(2, subrule.getDescription());
						ps.setInt(3, def);
						ps.setInt(4, active);
						ps.setString(5, subrule.getModelId());

					}

					@Override
					public int getBatchSize() {
						return subruleList.size();
					}
				});
				if(null==i || i.length<=0) throw new DataLayerException("Some Error while inserting in subrule table");
			} else {
				//log.debug("List is empty");
				throw new DataLayerException("List is empty");
			}

		} catch(Exception e) {
			log.error("Error in insertSubrule",e);
			throw new DataLayerException(e);
		}
		//log.debug("Exit: insertSubrule");
		return i;
	}

	/*
	 * (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#updateSubrule(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public boolean updateSubrule(final List<Subrule> subruleList) throws DataLayerException {
		//log.debug("Entered updateSubrule method");
		boolean updated=false;

		int[] i=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_UPDATESUBRULE_UPDATE);
		//String sql="UPDATE FSMMGR.PAC_RE_SUBRULE SET SUBRULE_NAME=?,SUBRULE_DESCRIPTION=?,DEFAULT_VALUE=?,ACTIVE=? WHERE SUBRULE_ID=?";
		try{
			i=jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {

					int active;
					if(subruleList.get(i).isActive()) active=1;
					else active=0;

					int def;
					if(subruleList.get(i).isDefaultValue()) def=1;
					else def=0;

					ps.setString(1,subruleList.get(i).getName());
					ps.setString(2,subruleList.get(i).getDescription());
					ps.setInt(3,def);
					ps.setInt(4, active);
					ps.setString(5,subruleList.get(i).getId());
				}

				@Override
				public int getBatchSize() {
					return subruleList.size();
				}

			});

			if(null!=i && i.length > 0){
				updated=true;
			}
		}catch(Exception e){
			log.error("Error in updateSubrule", e);
			throw new DataLayerException(e);
		}
		//log.debug("Exiting updateSubrule method");
		return updated;
	}

	/*
	 * (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#deleteSubrule(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public boolean deleteSubrule(final List<String> subruleId) throws DataLayerException {
		//log.debug("Entering deleteSubrule..");
		String sql=accessProps.getFromProps(CommonConstants.QUERY_DELETESUBRULE_DELETE);
		//String sql="DELETE FROM FSMMGR.PAC_RE_SUBRULE WHERE SUBRULE_ID=?";
		int[] i=null;
		boolean result=false;
		try {
			i=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				/* Getting the batch size */
				@Override
				public int getBatchSize() {
					return subruleId.size();
				}

				/* Setting the values into Prepared statement */
				@Override
				public void setValues(PreparedStatement ps, int count)throws SQLException {
					ps.setString(1, subruleId.get(count));
				}
			});
			if(null!= i && i.length>0) result=true;
			//log.debug("Rows Deleted:"+result);
		} catch(Exception e) {
			log.error("Error in deleteSubrule",e);
			throw new DataLayerException(e);
		}
		//log.debug("Exit: deleteSubrule");
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#fetchAllSubrules()
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Subrule> fetchAllSubrules() throws DataLayerException {
		//log.debug("Entered fetchAllSubrules method");
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLSUBRULES_SELECT);
		//String sql="SELECT SUBRULE_ID,SUBRULE_NAME,SUBRULE_DESCRIPTION,DEFAULT_VALUE,ACTIVE FROM FSMMGR.PAC_RE_SUBRULE order by SUBRULE_NAME";
		List<Subrule> subrules;
		try{
			subrules=jdbcTemplate.query(sql, new ResultSetExtractor<List<Subrule>>(){

				@Override
				public List<Subrule> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Subrule> subrules=new ArrayList<Subrule>();
					while(rs.next()){
						Subrule subrule=new Subrule();

						subrule.setId(rs.getString("SUBRULE_ID"));
						subrule.setName(rs.getString("SUBRULE_NAME"));
						subrule.setDescription(rs.getString("SUBRULE_DESCRIPTION"));
						subrule.setDefaultValue(rs.getBoolean("DEFAULT_VALUE"));
						subrule.setActive(rs.getBoolean("ACTIVE"));
						subrule.setModelId(rs.getString("MODEL_ID"));

						subrules.add(subrule);
					}
					return subrules;
				}

			});
			//log.debug("fetched subrules:"+subrules.size());
		}catch(Exception e) {
        	log.error("Error in fetchAllSubrules",e);
        	throw new DataLayerException(e);
        }
		//log.debug("Exiting fetchAllSubrules method");
		return subrules;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#fetchSubrule(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Subrule fetchSubrule(String id) throws DataLayerException {

		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHSUBRULE_SELECT);
		//String sql="SELECT SUBRULE_ID,SUBRULE_NAME,SUBRULE_DESCRIPTION,DEFAULT_VALUE,ACTIVE FROM FSMMGR.PAC_RE_SUBRULE WHERE SUBRULE_ID=?";
		Subrule subrule=null;
		try{
			//This will return null if no rows are fetched
			subrule=jdbcTemplate.query(sql, new Object[]{id},new ResultSetExtractor<Subrule>(){

				@Override
				public Subrule extractData(ResultSet rs) throws SQLException, DataAccessException {
					Subrule subrule=null;
					while(rs.next()){
						subrule=new Subrule();
						subrule.setId(rs.getString("SUBRULE_ID"));
						subrule.setName(rs.getString("SUBRULE_NAME"));
						subrule.setDescription(rs.getString("SUBRULE_DESCRIPTION"));
						subrule.setDefaultValue(rs.getBoolean("DEFAULT_VALUE"));
						subrule.setActive(rs.getBoolean("ACTIVE"));
						subrule.setModelId(rs.getString("MODEL_ID"));
					}
					return subrule;
				}

			});


			if(null != subrule){
				String subrulelogicsql=accessProps.getFromProps(CommonConstants.QUERY_FETCHSUBRULE_SELECTLOGIC);
				/*String subrulelogicsql ="SELECT srl.SUBRULE_LOGIC_ID,srl.SUBRULE_ID,srl.SUBRULES_OPERATOR_ID,srl.SUBRULE_ATTR_ID,srl.ORDER_NO, " +
										" objattr.ATTR_ID,objattr.ATTR_NAME,objattr.ATTR_VALUE, "
										+" opr.OPERATOR_ID,opr.OPERATOR_NAME,opr.OPERATOR_VALUE "
										+" from FSMMGR.PAC_RE_SUBRULE_LOGIC srl "
										+" left outer join FSMMGR.PAC_RE_SUBRULE sr on srl.SUBRULE_ID = sr.SUBRULE_ID "
										+" left outer join FSMMGR.PAC_RE_SUBRULE_ATTR_MAPPING sram on srl.SUBRULE_ATTR_ID = sram.SUBRULE_ATTR_ID "
										+" left outer join FSMMGR.PAC_RE_SUBRULE_OPR_MAPPING soprm on srl.SUBRULES_OPERATOR_ID = soprm.SUBRULE_OPERATOR_ID "
										+" left outer join FSMMGR.PAC_RE_OBECT_ATTR objattr on sram.ATTR_ID = objattr.ATTR_ID "
										+" left outer join FSMMGR.PAC_RE_OPERATOR opr on soprm.OPERATOR_ID = opr.OPERATOR_ID "
										+" where sr.SUBRULE_ID=?  order by  srl.ORDER_NO ";*/


				List<SubruleLogic> logic =  jdbcTemplate.query(subrulelogicsql,new Object[]{subrule.getId()}, new ResultSetExtractor<List<SubruleLogic>>() {

					@Override
					public List<SubruleLogic> extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<SubruleLogic> subRuleLogic = new ArrayList<SubruleLogic>();

						while(rs.next()){
							SubruleLogic srl = new SubruleLogic();

							srl.setId(rs.getString(1));
							srl.setSubRuleId(rs.getString(2));
							srl.setOperatorMapId(rs.getString(3));
							srl.setAttributeMapId(rs.getString(4));
							srl.setOrderno(rs.getString(5));


							Attribute attr = null;
							if(null != rs.getString(6)){
								attr = new Attribute();
								attr.setId(rs.getString(6));
								attr.setName(rs.getString(7));
								attr.setValue(rs.getString(8));

							}


							Operator opr = null;
							if(null != rs.getString(9)){
								opr = new Operator();
								opr.setId(rs.getString(9));
								opr.setName(rs.getString(10));
								opr.setValue(rs.getString(11));
							}

							srl.setAttribute(attr);
							srl.setOperator(opr);

							subRuleLogic.add(srl);
						}

						return subRuleLogic;
					}
				});

				subrule.setLogic(logic);
			}


		}catch(Exception e) {
        	log.error("Error in fetchSubrule",e);
        	throw new DataLayerException(e);
        }
		//log.debug("fetched subrule "+subrule);
		//log.debug("Exiting fetchSubrule method");
		return subrule;
	}


	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#fetchAllSubrules(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Subrule> fetchAllSubrules(String ruleId) throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLSUBRULES_SELECTSUBRULES);
		/*String sql="SELECT sr.SUBRULE_ID,sr.SUBRULE_NAME,sr.SUBRULE_DESCRIPTION,sr.DEFAULT_VALUE,sr.ACTIVE" +
				" FROM FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING  rsrm join FSMMGR.PAC_RE_SUBRULE sr  on rsrm.SUBRULE_ID =sr.SUBRULE_ID " +
				" where rsrm.RULE_ID=? order by sr.SUBRULE_NAME" ;*/
		List<Subrule> subrules;
		try{
			subrules=jdbcTemplate.query(sql, new Object[]{ruleId},new ResultSetExtractor<List<Subrule>>(){

				@Override
				public List<Subrule> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Subrule> subrules=new ArrayList<Subrule>();
					while(rs.next()){
						Subrule subrule=new Subrule();

						subrule.setId(rs.getString(1));
						subrule.setName(rs.getString(2));
						subrule.setDescription(rs.getString(3));
						subrule.setDefaultValue(rs.getBoolean(4));
						subrule.setActive(rs.getBoolean(5));

						subrules.add(subrule);
					}
					return subrules;
				}

			});

		}catch(Exception e) {
        	log.error("Error in fetchAllSubrules",e);
        	throw new DataLayerException(e);
        }
		//log.debug("Exiting fetchAllSubrules method");
		return subrules;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#saveAssignSubRulesToRule(java.util.List, java.util.List, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void saveAssignSubRulesToRule(final List<String> subruleidListToAssign, List<String> subruleidListToUnAssign ,final String ruleId) throws DataLayerException {


		try {
			if (null != subruleidListToUnAssign && subruleidListToUnAssign.size() > 0) {
				//UnAssigning SubRules From a Rule
				String deleteMappingSql=accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNSUBRULESTORULE_DELETE);
				//String deleteMappingSql = "Delete from FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING where RULE_ID =:ruleId and SUBRULE_ID in (:subRuleIds)";
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ruleId", ruleId);
				paramMap.put("subRuleIds", subruleidListToUnAssign);

				namedJdbcTemplatePac.update(deleteMappingSql, paramMap);
			}
			String sql=accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNSUBRULESTORULE_INSERT);
			/*String sql="insert into FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING (RULE_SUBRULE_ID,RULE_ID,SUBRULE_ID) " +
					"values (FSMMGR.RULES_SUBRULE_MAPPING_PK_SQ.NEXTVAL, ?, ?)";*/


			if (null != subruleidListToAssign && subruleidListToAssign.size() > 0) {
				jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {

						String subruleId = subruleidListToAssign.get(index);

						ps.setString(1, ruleId);
						ps.setString(2, subruleId);
					}

					@Override
					public int getBatchSize() {
						return subruleidListToAssign.size();
					}
				});
			}
		} catch (Exception e) {
			throw new DataLayerException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#subRulesUsedInRulesLogic(java.util.List, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> subRulesUsedInRulesLogic(List<String> subRuleIds,String ruleId) throws DataLayerException {
		/*String sql ="Select srl.SUBRULE_NAME,rsrm.SUBRULE_ID ,rl.RULE_ID from PAC_RE_RULES_SUBRULE_MAPPING rsrm "
					+" left outer join FSMMGR.PAC_RE_RULE_LOGIC rl on  rl.RULE_SUBRULE_MAP_ID=rsrm.RULE_SUBRULE_ID "
					+" left outer join FSMMGR.PAC_RE_SUBRULE srl on rsrm.SUBRULE_ID = srl.SUBRULE_ID "
					+" where rsrm.SUBRULE_ID in (:subRuleIds) and rl.RULE_ID=:ruleId  ";*/

		String sql=accessProps.getFromProps(CommonConstants.QUERY_SUBRULESUSEDINRULESLOGIC_SELECT);

		HashMap< String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("subRuleIds", subRuleIds);
		paramMap.put("ruleId", ruleId);


		List<String> subRuleToRules  = new   ArrayList<String>();
		if (subRuleIds.size() > 0) {
			subRuleToRules = namedJdbcTemplatePac.query(sql, paramMap, new ResultSetExtractor<List<String>>() {

				@Override
				public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<String> subRuleToRulesTemp  = subRuleToRulesTemp = new  ArrayList<String>();

					while (rs.next()) {

						String subruleName = rs.getString(1);
						//String ruleId = rs.getString(2);

						subRuleToRulesTemp.add(subruleName);
					}

					return subRuleToRulesTemp;
				}
			});
		}


		return subRuleToRules;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#fetchSubruleByName(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Subrule fetchSubruleByName(String subRuleName) throws DataLayerException {

		//log.debug("Entered fetchSubrule method");
		//String sql="SELECT SUBRULE_ID,SUBRULE_NAME,SUBRULE_DESCRIPTION,DEFAULT_VALUE,ACTIVE FROM FSMMGR.PAC_RE_SUBRULE WHERE SUBRULE_NAME=?";
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHSUBRULEBYNAME_SELECT);
		Subrule subrule=null;
		try{
			subrule=jdbcTemplate.query(sql,new Object[]{subRuleName}, new ResultSetExtractor<Subrule>(){

				@Override
				public Subrule extractData(ResultSet rs) throws SQLException, DataAccessException {
					Subrule subrule=null;
					while(rs.next()){
						subrule=new Subrule();
						subrule.setId(rs.getString("SUBRULE_ID"));
						subrule.setName(rs.getString("SUBRULE_NAME"));
						subrule.setDescription(rs.getString("SUBRULE_DESCRIPTION"));
						subrule.setDefaultValue(rs.getBoolean("DEFAULT_VALUE"));
						subrule.setActive(rs.getBoolean("ACTIVE"));
					}
					return subrule;
				}
			});
			//log.debug("fetched single subrule:"+subrule.toString());
		}catch(Exception e) {
        	log.error("Error in fetchSubrule",e);
        	throw new DataLayerException(e);
        }
		//log.debug("Exiting fetchSubrule method");
		return subrule;

	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleDao#fetchRuleOperatorMappingForRule(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<RuleSubruleMapping> fetchRuleOperatorMappingForRule(String ruleId) throws DataLayerException {

		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHRULEOPERATORMAPPINGFORRULE_SELECTSUBRULE);
		/*String sql ="select rsrmap.RULE_SUBRULE_ID ,rsrmap.RULE_ID,rsrmap.SUBRULE_ID, " +
				" sr.SUBRULE_NAME,sr.SUBRULE_DESCRIPTION " +
				" from FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING rsrmap join FSMMGR.PAC_RE_SUBRULE sr  on rsrmap.SUBRULE_ID = sr.SUBRULE_ID where rsrmap.RULE_ID=?";
		*/
		List<RuleSubruleMapping> subRulesMappings = jdbcTemplate.query(sql, new Object[]{ruleId}, new ResultSetExtractor<List<RuleSubruleMapping>>() {

			@Override
			public List<RuleSubruleMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<RuleSubruleMapping> subRuleMappingList = new ArrayList<RuleSubruleMapping>();
				while(rs.next()){
					RuleSubruleMapping rsm = new RuleSubruleMapping();

					rsm.setId(rs.getString(1));
					rsm.setRuleId(rs.getString(2));
					rsm.setSubRuleId(rs.getString(3));
					rsm.setSubRuleName(rs.getString(4));
					rsm.setSubRuleDescription(rs.getString(5));

					subRuleMappingList.add(rsm);
				}
				return subRuleMappingList;
			}

		});

		return subRulesMappings;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void deleteSubRuleLogic(String subRuleId) throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_DELETESUBRULELOGIC_DELETE);
		//String sql="delete from FSMMGR.PAC_RE_SUBRULE_LOGIC where SUBRULE_ID = ?";
		jdbcTemplate.update(sql, new Object[]{subRuleId});
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void insertSubRuleLogic(String subRuleId, final List<SubruleLogic> logic) throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_INSERTSUBRULELOGIC_INSERT);
		/*String sql = "insert into FSMMGR.PAC_RE_SUBRULE_LOGIC (SUBRULE_LOGIC_ID,SUBRULE_ID,SUBRULES_OPERATOR_ID,SUBRULE_ATTR_ID,ORDER_NO) " +
					" values (FSMMGR.SUBRULE_LOGIC_PK_SQ.NEXTVAL,?,?,?,?) ";*/
		jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				SubruleLogic subruleLogic =  logic.get(index);

				ps.setString(1, subruleLogic.getSubRuleId());
				ps.setString(2, subruleLogic.getOperatorMapId());
				ps.setString(3, subruleLogic.getAttributeMapId());
				ps.setString(4, subruleLogic.getOrderno());
			}

			@Override
			public int getBatchSize() {
				return logic.size();
			}
		});

	}

	@Override
	public List<Subrule> fetchAllSubrulesbyModelId(String modelId) throws DataLayerException {

		//log.debug("Entered fetchAllSubrules method");
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLSUBRULES_MODEL_ID);
		//String sql="SELECT SUBRULE_ID,SUBRULE_NAME,SUBRULE_DESCRIPTION,DEFAULT_VALUE,ACTIVE FROM FSMMGR.PAC_RE_SUBRULE order by SUBRULE_NAME";
		List<Subrule> subrules;
		try{
			subrules=jdbcTemplate.query(sql, new ResultSetExtractor<List<Subrule>>(){

				@Override
				public List<Subrule> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Subrule> subrules=new ArrayList<Subrule>();
					while(rs.next()){
						Subrule subrule=new Subrule();

						subrule.setId(rs.getString("SUBRULE_ID"));
						subrule.setName(rs.getString("SUBRULE_NAME"));
						subrule.setDescription(rs.getString("SUBRULE_DESCRIPTION"));
						subrule.setDefaultValue(rs.getBoolean("DEFAULT_VALUE"));
						subrule.setActive(rs.getBoolean("ACTIVE"));

						subrules.add(subrule);
					}
					return subrules;
				}

			});
			//log.debug("fetched subrules:"+subrules.size());
		}catch(Exception e) {
        	log.error("Error in fetchAllSubrules",e);
        	throw new DataLayerException(e);
        }
		//log.debug("Exiting fetchAllSubrules method");
		return subrules;
			
	}


}
