package org.pjr.rulesengine.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.Rule;
import org.pjr.rulesengine.dbmodel.RuleLogic;
import org.pjr.rulesengine.dbmodel.RuleOperatorMapping;
import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.util.AccessProperties;
import org.pjr.rulesengine.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

/**
 * The Class RulesDaoImpl.
 *
 * @author Sudhakar
 */
@Component
public class RuleDaoImpl implements RuleDao{

	private static final Log log = LogFactory.getLog(RuleDaoImpl.class);

	@Autowired
    @Qualifier("pjrJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	@Autowired
    @Qualifier("pjrNamedJdbcTemplatePac")
	private NamedParameterJdbcTemplate namedJdbcTemplatePac;

	@Autowired
    @Qualifier("accessProps")
    private AccessProperties accessProps;

	@Autowired
	private SubruleDao subruleDao;

	@Autowired
	private OperatorDao operatorDao;



	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/**
	 * @return the accessProps
	 */
	public AccessProperties getAccessProps() {
		return accessProps;
	}

	/**
	 * @param accessProps the accessProps to set
	 */
	public void setAccessProps(AccessProperties accessProps) {
		this.accessProps = accessProps;
	}

	/**
	 * Method Name			: insertIntoRules
	 * Method Description	: This method is responsible to insert Rules details
	 * 						  into the PAC DB
	 * @param List<Rule>
	 * @return void
	 * @throws DataLayerException
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	@Override public void insertIntoRules(final Rule ruleObj)
			throws DataLayerException  {

		try {
				String sql=accessProps.getFromProps(CommonConstants.QUERY_INSERTINTORULES_INSERT);
				//String sql ="INSERT into FSMMGR.PAC_RE_RULES (RULE_ID, RULE_NAME, RULE_DESCRIPTION, ACTIVE, EXE_ORDER,RETURN_VALUE) VALUES(FSMMGR.PAC_RE_RULES_PK_SQ.NEXTVAL,?,?,?,?,?)";

				jdbcTemplate.update(sql, new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps) throws SQLException {

						int active;
						if(ruleObj.isActive())
							active=1;
						else
							active=0;


						/* Setting the values to prepared statement */
						ps.setString(1, ruleObj.getRuleName());
						ps.setString(2, ruleObj.getRuleDescription());
						ps.setInt(3, active);
						ps.setInt(4, ruleObj.getExecutionOrder());
						ps.setString(5, ruleObj.getReturnValue());
						ps.setString(6, ruleObj.getModelId());
					}
				});

		} catch (Exception e) {
			throw new DataLayerException(e);
		}

		log.info("Exiting insertIntoRules..");
	}



	/**
	 * Method Name : updateRule
	 *  Method Description : This method is used to update PAC_RE_RULES
	 *
	 * @param List
	 *            <Rule>
	 * @return boolean
	 * @throws DataLayerException
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	@Override public boolean updateRule(final List<Rule> ruleList)
			throws DataLayerException {
		log.info("Entering updateRule ");

		boolean dataUpdated = false;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_UPDATERULE_UPDATE);
		//String sql ="UPDATE FSMMGR.PAC_RE_RULES SET RULE_NAME=?,RULE_DESCRIPTION=?,ACTIVE=?,RETURN_VALUE=?,EXE_ORDER=? WHERE RULE_ID=?";

		int[] batchUpdatedRows = jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {

					@Override
					public int getBatchSize() {
						return ruleList.size();
					}

					@Override
					public void setValues(PreparedStatement ps, int count)
							throws SQLException {

						ps.setString(1, ruleList.get(count)
								.getRuleName());
						ps.setString(2, ruleList.get(count)
								.getRuleDescription());
						int active;
						if(ruleList.get(count).isActive())
							active=1;
						else
							active=0;


						ps.setLong(3, active);
						ps.setString(4, ruleList.get(count).getReturnValue());
						ps.setInt(5, ruleList.get(count).getExecutionOrder());
						ps.setString(6, String.valueOf(ruleList.get(count)
								.getId()));

					}
				});

		if (batchUpdatedRows.length > 0) {
			dataUpdated = true;
		}

		log.info("Exiting updateRule");
		return dataUpdated;
	}


	/**
	 * Method Name			: deleteFromRules
	 * Method Description	: This method is used to delete record from PAC_RE_RULES
	 * @param Long rulesId
	 * @return boolean
	 * @throws DataLayerException
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	@Override public boolean deleteFromRules(final String rulesId)
			throws DataLayerException {

		boolean dataDeleted;
		try {
			log.info("Entering deleteFromRules ");

			dataDeleted = false;
			String sql=accessProps.getFromProps(CommonConstants.QUERY_DELETEFROMRULES_DELETE);
			//String sql ="DELETE FROM FSMMGR.PAC_RE_RULES WHERE RULE_ID=?" ;
			int rowsDeleted = jdbcTemplate.update(sql, new PreparedStatementSetter()  {
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1,rulesId);

				}
			});
			if(rowsDeleted > 0)
				dataDeleted = true;
			log.info("Exiting deleteFromRules "+rowsDeleted+" "+dataDeleted);
		} catch (Exception e) {
			throw new DataLayerException(e);
		}
		return dataDeleted;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Rule> fetchAllRules() throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLRULES_SELECT);
		//String sql="select RULE_ID,RULE_NAME,RULE_DESCRIPTION,ACTIVE,RETURN_VALUE,EXE_ORDER from FSMMGR.PAC_RE_RULES order by EXE_ORDER";

		List<Rule> ruleList;
		try {
			ruleList = jdbcTemplate.query(sql, new ResultSetExtractor<List<Rule>>() {

				@Override
				public List<Rule> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Rule> ruleList = new ArrayList<Rule>();

					while(rs.next()){
						Rule rule = new Rule();

						rule.setId(rs.getString("RULE_ID"));
						rule.setRuleName(rs.getString("RULE_NAME"));
						rule.setRuleDescription(rs.getString("RULE_DESCRIPTION"));
						rule.setActive(rs.getBoolean("ACTIVE"));
						rule.setReturnValue(rs.getString("RETURN_VALUE"));
						rule.setExecutionOrder(rs.getInt("EXE_ORDER"));
						rule.setModelId(rs.getString("MODEL_ID"));
						ruleList.add(rule);
					}

					return ruleList;
				}
			});
		} catch (Exception e) {
			throw new DataLayerException(e);
		}

		return ruleList;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Rule> fetchAllRulesBYExecutionOrder() throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLRULES_SELECT);
		//String sql="select RULE_ID,RULE_NAME,RULE_DESCRIPTION,ACTIVE,RETURN_VALUE,EXE_ORDER from FSMMGR.PAC_RE_RULES order by EXE_ORDER";

		List<Rule> ruleList;
		try {
			ruleList = jdbcTemplate.query(sql, new ResultSetExtractor<List<Rule>>() {

				@Override
				public List<Rule> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Rule> ruleList = new ArrayList<Rule>();

					while(rs.next()){
						Rule rule = new Rule();

						rule.setId(rs.getString("RULE_ID"));
						rule.setRuleName(rs.getString("RULE_NAME"));
						rule.setRuleDescription(rs.getString("RULE_DESCRIPTION"));
						rule.setActive(rs.getBoolean("ACTIVE"));
						rule.setReturnValue(rs.getString("RETURN_VALUE"));
						rule.setExecutionOrder(rs.getInt("EXE_ORDER"));
						ruleList.add(rule);
					}

					return ruleList;
				}
			});
		} catch (Exception e) {
			throw new DataLayerException(e);
		}

		return ruleList;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Rule fetchRule(final String ruleId) throws DataLayerException {

		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHRULE_SELECTRULE);
		//String sql="select RULE_ID,RULE_NAME,RULE_DESCRIPTION,ACTIVE,RETURN_VALUE,EXE_ORDER from FSMMGR.PAC_RE_RULES where RULE_ID =?";

		Rule rule = null;

			rule = jdbcTemplate.query(sql,new Object[]{ruleId}, new ResultSetExtractor<Rule>() {

				@Override
				public Rule extractData(ResultSet rs) throws SQLException, DataAccessException {
					Rule r = null;

					while(rs.next()){
						r  = new Rule();
						r.setId(rs.getString("RULE_ID"));
						r.setRuleName(rs.getString("RULE_NAME"));
						r.setRuleDescription(rs.getString("RULE_DESCRIPTION"));
						r.setActive(rs.getBoolean("ACTIVE"));
						r.setReturnValue(rs.getString("RETURN_VALUE"));
						r.setExecutionOrder(rs.getInt("EXE_ORDER"));
						r.setModelId(rs.getString("MODEL_ID"));
					}
					return r;
				}

			});


		if (null != rule) {
			String ruleLogicSql=accessProps.getFromProps(CommonConstants.QUERY_FETCHRULE_SELECTRULELOGIC);
			/*String ruleLogicSql = "SELECT rl.RULE_LOGIC_ID,rl.RULE_SUBRULE_MAP_ID,rl.RULE_OPERATOR_MAP_ID,rl.ORDER_NO, "
					+ "sr.SUBRULE_NAME,sr.SUBRULE_DESCRIPTION,sr.DEFAULT_VALUE , " + "opr.OPERATOR_NAME,opr.OPERATOR_VALUE , "
					+ " sr.SUBRULE_ID "
					+ "FROM   "
					+ "FSMMGR.PAC_RE_RULE_LOGIC rl left outer join FSMMGR.PAC_RE_RULES r on rl.RULE_ID = r.RULE_ID "
					+ "left outer join FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING rulesSrm on rl.RULE_SUBRULE_MAP_ID = rulesSrm.RULE_SUBRULE_ID "
					+ "left outer join FSMMGR.PAC_RE_RULES_OPR_MAPPING rulesOprm on rl.RULE_OPERATOR_MAP_ID = rulesOprm.RULES_OPERATOR_ID  "
					+ "left outer join FSMMGR.PAC_RE_SUBRULE sr on rulesSrm.SUBRULE_ID = sr.SUBRULE_ID  "
					+ "left outer join FSMMGR.PAC_RE_OPERATOR opr on  rulesOprm.OPERATOR_ID = opr.OPERATOR_ID "
					+ "where r.RULE_ID=? order by rl.ORDER_NO ";*/
			List<RuleLogic> logic = jdbcTemplate.query(ruleLogicSql, new Object[] { ruleId }, new ResultSetExtractor<List<RuleLogic>>() {

				@Override
				public List<RuleLogic> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<RuleLogic> ruleLogicList = new ArrayList<RuleLogic>();

					while (rs.next()) {
						RuleLogic ruleLogic = new RuleLogic();

						//Rule Logic
						String ruleLogicId = rs.getString(1);
						String subruleMapId = rs.getString(2);
						String operatorId = rs.getString(3);
						int orderNo = rs.getInt(4);

						//Subrule
						String subRuleName = rs.getString(5);
						String subRuleDesription = rs.getString(6);
						boolean subRuleDefaultvalue = rs.getBoolean(7);

						Subrule sr = null;
						if (null != subruleMapId) {
							sr = new Subrule();
							sr.setId(subruleMapId);
							sr.setDescription(subRuleDesription);
							sr.setName(subRuleName);
							sr.setDefaultValue(subRuleDefaultvalue);
						}
						String subRuleId = rs.getString(10);
						Subrule subRuleTemp;
						try {

							if (null != subRuleId) {
								subRuleTemp = subruleDao.fetchSubrule(subRuleId);
								sr.setLogic(subRuleTemp.getLogic());
								sr.setActive(subRuleTemp.isActive());
								sr.setDefaultValue(subRuleTemp.isDefaultValue());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}


						//Operator
						String operatorName = rs.getString(8);
						String operatorValue = rs.getString(9);

						Operator opr = null;
						if (null != operatorId) {
							opr = new Operator();
							opr.setId(operatorId);
							opr.setName(operatorName);
							opr.setValue(operatorValue);
						}
						ruleLogic.setId(ruleLogicId);
						ruleLogic.setRuleId(ruleId);
						ruleLogic.setRuleSubRuleMapping(subruleMapId);
						ruleLogic.setRuleOperatorIdMapping(operatorId);
						ruleLogic.setOrderno(orderNo);
						ruleLogic.setSubRule(sr);
						ruleLogic.setOperator(opr);

						ruleLogicList.add(ruleLogic);
					}
					return ruleLogicList;
				}

			});
			//log.debug("Rule logic size :" + logic.size());
			final Set<String> subRuleIds = new TreeSet<String>();
			for (RuleLogic rl : logic) {
				if (null != rl.getRuleSubRuleMappingId())
					subRuleIds.add(rl.getRuleSubRuleMappingId());
			}
			rule.setLogic(new TreeSet<RuleLogic>(logic));
		}
		return rule;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void insertRuleLogic(String ruleId,final List<RuleLogic> ruleLogics) throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_INSERTRULELOGIC_INSERT);
		/*String sql="insert into FSMMGR.PAC_RE_RULE_LOGIC (RULE_LOGIC_ID,RULE_ID,RULE_SUBRULE_MAP_ID,RULE_OPERATOR_MAP_ID,ORDER_NO) " +
				"values(FSMMGR.RULE_LOGIC_PK_SQ.NEXTVAL,?,?,?,?)";*/

		try {
			if(null != ruleLogics && !ruleLogics.isEmpty()){
				jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {

						RuleLogic rl =ruleLogics.get(index);

						ps.setString(1, rl.getRuleId());
						ps.setString(2, rl.getRuleSubRuleMappingId());
						ps.setString(3, rl.getRuleOperatorIdMapping());
						ps.setLong(4, rl.getOrderno());

					}

					@Override
					public int getBatchSize() {
						return ruleLogics.size();
					}
				});
			}
		} catch (DataAccessException e) {
			throw new DataLayerException(e);
		}


	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void deleteRuleLogic(String ruleId) throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_DELETERULELOGIC_DELETE);
		//String sql ="Delete from FSMMGR.PAC_RE_RULE_LOGIC where RULE_ID= ?";
		try {
			jdbcTemplate.update(sql, new Object[] { ruleId });
		} catch (Exception e) {
			throw new DataLayerException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Rule fetchRuleByName(String ruleName) throws DataLayerException {

		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHRULEBYNAME_SELECT);
		//String sql="select RULE_ID,RULE_NAME,RULE_DESCRIPTION,ACTIVE,RETURN_VALUE,EXE_ORDER from FSMMGR.PAC_RE_RULES where RULE_NAME =?";

		Rule rule = null;

			rule = jdbcTemplate.query(sql,new Object[]{ruleName}, new ResultSetExtractor<Rule>() {

				@Override
				public Rule extractData(ResultSet rs) throws SQLException, DataAccessException {
					Rule r = null;

					while(rs.next()){
						r = new Rule();
						r.setId(rs.getString("RULE_ID"));
						r.setRuleName(rs.getString("RULE_NAME"));
						r.setRuleDescription(rs.getString("RULE_DESCRIPTION"));
						r.setActive(rs.getBoolean("ACTIVE"));
						r.setReturnValue(rs.getString("RETURN_VALUE"));
						r.setExecutionOrder(rs.getInt("EXE_ORDER"));
						r.setModelId(rs.getString("MODEL_ID"));
					}
					return r;
				}

			});


		if (null != rule) {
			final String ruleId = rule.getId();
			String ruleLogicSql =accessProps.getFromProps(CommonConstants.QUERY_FETCHRULEBYNAME_SELECTRULELOGIC);
			/*String ruleLogicSql = "SELECT rl.RULE_LOGIC_ID,rl.RULE_SUBRULE_MAP_ID,rl.RULE_OPERATOR_MAP_ID,rl.ORDER_NO, "
					+ "sr.SUBRULE_NAME,sr.SUBRULE_DESCRIPTION,sr.DEFAULT_VALUE , " + "opr.OPERATOR_NAME,opr.OPERATOR_VALUE  " + "FROM   "
					+ "FSMMGR.PAC_RE_RULE_LOGIC rl left outer join FSMMGR.PAC_RE_RULES r on rl.RULE_ID = r.RULE_ID "
					+ "left outer join FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING rulesSrm on rl.RULE_SUBRULE_MAP_ID = rulesSrm.RULE_SUBRULE_ID "
					+ "left outer join FSMMGR.PAC_RE_RULES_OPR_MAPPING rulesOprm on rl.RULE_OPERATOR_MAP_ID = rulesOprm.RULES_OPERATOR_ID  "
					+ "left outer join FSMMGR.PAC_RE_SUBRULE sr on rulesSrm.SUBRULE_ID = sr.SUBRULE_ID  "
					+ "left outer join FSMMGR.PAC_RE_OPERATOR opr on  rulesOprm.OPERATOR_ID = opr.OPERATOR_ID "
					+ "where r.RULE_ID=? order by rl.ORDER_NO ";*/
			List<RuleLogic> logic = jdbcTemplate.query(ruleLogicSql, new Object[] { ruleId }, new ResultSetExtractor<List<RuleLogic>>() {

				@Override
				public List<RuleLogic> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<RuleLogic> ruleLogicList = new ArrayList<RuleLogic>();

					while (rs.next()) {
						RuleLogic ruleLogic = new RuleLogic();

						//Rule Logic
						String ruleLogicId = rs.getString(1);
						String subruleId = rs.getString(2);
						String operatorId = rs.getString(3);
						int orderNo = rs.getInt(4);

						//Subrule
						String subRuleName = rs.getString(5);
						String subRuleDesription = rs.getString(6);
						boolean subRuleDefaultvalue = rs.getBoolean(7);
						Subrule sr = new Subrule();
						sr.setId(subruleId);
						sr.setDescription(subRuleDesription);
						sr.setName(subRuleName);
						sr.setDefaultValue(subRuleDefaultvalue);

						//Operator
						String operatorName = rs.getString(8);
						String operatorValue = rs.getString(9);
						Operator opr = new Operator();
						opr.setId(operatorId);
						opr.setName(operatorName);
						opr.setValue(operatorValue);

						ruleLogic.setId(ruleLogicId);
						ruleLogic.setRuleId(ruleId);
						ruleLogic.setRuleSubRuleMapping(subruleId);
						ruleLogic.setRuleOperatorIdMapping(operatorId);
						ruleLogic.setOrderno(orderNo);
						ruleLogic.setSubRule(sr);
						ruleLogic.setOperator(opr);

						ruleLogicList.add(ruleLogic);
					}
					return ruleLogicList;
				}

			});
			log.debug("Rule logic size :" + logic.size());
			final Set<String> subRuleIds = new TreeSet<String>();
			for (RuleLogic rl : logic) {
				if (null != rl.getRuleSubRuleMappingId())
					subRuleIds.add(rl.getRuleSubRuleMappingId());
			}
			rule.setLogic(new TreeSet<RuleLogic>(logic));
		}
		return rule;

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public boolean isExecutionOrderExists(int executionOrder) throws DataLayerException {

		String sql =accessProps.getFromProps(CommonConstants.QUERY_ISEXECUTIONORDEREXISTS_SELECT);
		//String sql ="Select RULE_ID from FSMMGR.PAC_RE_RULES where EXE_ORDER = ?";

		boolean result = false;

		String ruleId = jdbcTemplate.query(sql, new Object[]{new Integer(executionOrder)}, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				String ruleId = null;
				while(rs.next()){
					ruleId = rs.getString("RULE_ID");
				}
				return ruleId;
			}
		});

		if(null != ruleId) result = true;
		log.debug("Completed isExecutionOrderExists method ");
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public void duplicateRuleAndRuleLogic(Rule rule,String ruleIdToCopy) throws DataLayerException {
		try {
			insertIntoRules(rule);
			Rule ruleInserted = fetchRuleByName(rule.getRuleName());
			//Rule ruleToCopyfrom = fetchRule(ruleIdToCopy);

			List<String> subruleidListToAssign = new ArrayList<String>();
			List<Subrule>  subRulesToCopy = subruleDao.fetchAllSubrules(ruleIdToCopy);
			for(Subrule sr : subRulesToCopy){
				if(null != sr && StringUtils.isNotBlank(sr.getId()))subruleidListToAssign.add(sr.getId());
			}

			List<String> operatorIdToAssign = new ArrayList<String>();
			List<RuleOperatorMapping> operatorMappings = operatorDao.fetchRuleOperatorMappingForRule(ruleIdToCopy);
			for(RuleOperatorMapping rom : operatorMappings){
				if(null != rom && StringUtils.isNotBlank(rom.getOperatorId()))operatorIdToAssign.add(rom.getOperatorId());
			}



			operatorDao.saveAssignOperatorsToRule(ruleInserted.getId(), operatorIdToAssign, null);
			subruleDao.saveAssignSubRulesToRule(subruleidListToAssign, null, ruleInserted.getId());

			String ruleLogicCopy = "Select RSM.SUBRULE_ID,OPM.OPERATOR_ID,RL.ORDER_NO "+
				" FROM PACMGR.PAC_RE_RULE_LOGIC RL  "+
				" LEFT OUTER join PACMGR.PAC_RE_RULES_SUBRULE_MAPPING RSM ON RL.RULE_SUBRULE_MAP_ID = RSM.RULE_SUBRULE_ID "+
				" LEFT OUTER JOIN PACMGR.PAC_RE_RULES_OPR_MAPPING OPM ON RL.RULE_OPERATOR_MAP_ID = OPM.RULES_OPERATOR_ID "+
				" WHERE RL.RULE_ID=?";

			List<RuleLogic> logicToCopyFrom = jdbcTemplate.query(ruleLogicCopy,new Object[]{ruleIdToCopy}, new ResultSetExtractor<List<RuleLogic>>() {

				@Override
				public List<RuleLogic> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<RuleLogic> logic = new ArrayList<RuleLogic>();
					while (rs.next()){
						RuleLogic rl = new RuleLogic();
						rl.setRuleSubRuleMapping(rs.getString(1));
						rl.setRuleOperatorIdMapping(rs.getString(2));
						rl.setOrderno(rs.getInt(3));
						logic.add(rl);
					}

					return logic;
				}

			});



			List<RuleOperatorMapping>  ruleOprMapping = operatorDao.fetchRuleOperatorMappingForRule(ruleInserted.getId());
			List<RuleSubruleMapping> ruleSubRuleMapping = subruleDao.fetchRuleOperatorMappingForRule(ruleInserted.getId());

			List<RuleLogic> logicToCopy = new ArrayList<RuleLogic>();
			for(RuleLogic rl :logicToCopyFrom){
				if(StringUtils.isNotBlank(rl.getRuleSubRuleMappingId())){
					for(RuleSubruleMapping rostemp :ruleSubRuleMapping){
						if(rostemp.getSubRuleId().equals(rl.getRuleSubRuleMappingId())){
							RuleLogic rTemp = new RuleLogic();
							rTemp.setRuleId(ruleInserted.getId());
							rTemp.setRuleSubRuleMapping(rostemp.getId());
							rTemp.setOrderno(rl.getOrderno());
							logicToCopy.add(rTemp);
							break;
						}
					}
				}else{
					for(RuleOperatorMapping romTemp :ruleOprMapping){
						if(romTemp.getOperatorId().equals(rl.getRuleOperatorIdMapping())){
							RuleLogic rTemp = new RuleLogic();
							rTemp.setRuleId(ruleInserted.getId());
							rTemp.setRuleOperatorIdMapping(romTemp.getId());
							rTemp.setOrderno(rl.getOrderno());
							logicToCopy.add(rTemp);
							break;
						}
					}
				}

			}

			insertRuleLogic(ruleInserted.getId(), logicToCopy) ;


		} catch (Exception e) {
			throw new DataLayerException("unable to duplicate ruleid :"+ruleIdToCopy, e);
		}
	}

	@Override
	public List<Rule> fetchAllRulesByModel(String modelId) throws DataLayerException {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLRULES_BYMODEL_SELECT);
		//String sql="select RULE_ID,RULE_NAME,RULE_DESCRIPTION,ACTIVE,RETURN_VALUE,EXE_ORDER from FSMMGR.PAC_RE_RULES order by EXE_ORDER";

		List<Rule> ruleList;
		try {
			ruleList = jdbcTemplate.query(sql,new Object[]{modelId}, new ResultSetExtractor<List<Rule>>() {

				@Override
				public List<Rule> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Rule> ruleList = new ArrayList<Rule>();

					while(rs.next()){
						Rule rule = new Rule();

						rule.setId(rs.getString("RULE_ID"));
						rule.setRuleName(rs.getString("RULE_NAME"));
						rule.setRuleDescription(rs.getString("RULE_DESCRIPTION"));
						rule.setActive(rs.getBoolean("ACTIVE"));
						rule.setReturnValue(rs.getString("RETURN_VALUE"));
						rule.setExecutionOrder(rs.getInt("EXE_ORDER"));
						rule.setModelId(rs.getString("MODEL_ID"));
						ruleList.add(rule);
					}

					return ruleList;
				}
			});
		} catch (Exception e) {
			throw new DataLayerException(e);
		}

		return ruleList;
	}

	@Override
	public boolean isExecutionOrderExists(int executionOrder, String modelId) throws DataLayerException {
		String sql =accessProps.getFromProps(CommonConstants.QUERY_ISEXECUTIONORDEREXISTS_MODEL_SELECT);
		//String sql ="Select RULE_ID from FSMMGR.PAC_RE_RULES where EXE_ORDER = ?";

		boolean result = false;

		String ruleId = jdbcTemplate.query(sql, new Object[]{executionOrder,modelId}, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				String ruleId = null;
				while(rs.next()){
					ruleId = rs.getString("RULE_ID");
				}
				return ruleId;
			}
		});

		if(null != ruleId) result = true;
		log.debug("Completed isExecutionOrderExists method ");
		return result;
	}

}


