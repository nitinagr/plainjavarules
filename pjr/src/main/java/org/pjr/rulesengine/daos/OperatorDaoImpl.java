/**
 *
 */
package org.pjr.rulesengine.daos;

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
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.RuleOperatorMapping;
import org.pjr.rulesengine.util.AccessProperties;
import org.pjr.rulesengine.util.CommonConstants;

/**
 * The Class OperatorDaoImpl.
 *
 * @author Sudhakar(pjr.org)
 */
@Component(value="operatorDao")
public class OperatorDaoImpl implements OperatorDao {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(OperatorDaoImpl.class);

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

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#insertIntoOperator(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public int[] insertIntoOperator(final List<Operator> operatorList) throws DataLayerException {
		log.debug("Entering insertOperator..");
		int[] i=null;
		try {
			if (null != operatorList && operatorList.size() != 0){
				i=jdbcTemplate.batchUpdate(accessProps.getFromProps(CommonConstants.QUERY_INSERTINTOOPERATOR_INSERT), new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int count) throws SQLException {
						Operator operator = operatorList.get(count);

						String name = operator.getName();
						String value = operator.getValue();

						//Setting the values to the prepared statement
						ps.setString(1, name);
						ps.setString(2, value);

					}

					@Override
					public int getBatchSize() {
						return operatorList.size();
					}
				});
			} else {
				log.info("List is empty");
				throw new DataLayerException("List is empty");
			}
			log.debug("Exit: insertOperator");
		} catch(Exception e) {
			log.error("Error in insertOperator",e);
			throw new DataLayerException(e);
		}
		return i;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#updateOperator(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public boolean updateOperator(final List<Operator> operatorList) throws DataLayerException {
		log.info("Inside updateOperator..");
		boolean updated=false;

		int[] i=null;

		String sql=accessProps.getFromProps(CommonConstants.QUERY_UPDATEOPERATOR_UPDATE);

		log.debug("Created the params");
		try{
			i=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int count) throws SQLException {
					//Setting the values to the prepared statement
					ps.setString(1,operatorList.get(count).getName());
					ps.setString(2,operatorList.get(count).getValue());
					ps.setString(3,String.valueOf(operatorList.get(count).getId()));

				}

				@Override
				public int getBatchSize() {
					return operatorList.size();
				}
			});
		}catch(Exception e){
			log.error("Error in updateOperator", e);
			throw new DataLayerException(e);
		}
		if(null!=i && i.length!=0){
			updated=true;
		}
		log.info("Exit: updateOperator");
		return updated;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#deleteOperator(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public int[] deleteOperator(final List<String> operatorId) throws DataLayerException {
		log.info("Entering deleteOperator..");
		log.info("Executing delete query");
		int[] i=null;
		try {
			i=jdbcTemplate.batchUpdate(accessProps.getFromProps(CommonConstants.QUERY_DELETEOPERATOR_DELETE), new BatchPreparedStatementSetter() {

				/* Getting the batch size */
				@Override
				public int getBatchSize() {
					return operatorId.size();
				}

				/* Setting the values into Prepared statement */
				@Override
				public void setValues(PreparedStatement ps, int count)throws SQLException {
					ps.setString(1, String.valueOf(operatorId.get(count)));
				}
			});

		} catch(Exception e) {
			log.error("Error in deleteOperator",e);
			throw new DataLayerException(e);
		}
		log.info("Exit: deleteOperator");
		return i;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#fetchAllOperators()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Operator> fetchAllOperators() throws DataLayerException {
		log.debug("Entering fetchAllOperators..");

		List<Operator> operatorList=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLOPERATORS_SELECT);
		try{
			operatorList=jdbcTemplate.query(sql,new RowMapper(){
				public Object mapRow(ResultSet rs, int count) throws SQLException{
					Operator temp=new Operator();
					temp.setId(rs.getString("OPERATOR_ID"));
					temp.setName(rs.getString("OPERATOR_NAME"));
					temp.setValue(rs.getString("OPERATOR_VALUE"));
					return temp;
				}
			});
			if(null==operatorList) throw new DataLayerException("Some error while fetching operator details");
			log.info("Successfully fetched operator details.");
		}catch(Exception e) {
			log.error("Error in fetchAllOperators",e);
			throw new DataLayerException(e);
		}
		log.info("Exit: fetchAllOperators");
		return operatorList;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#fetchOperator(long)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Operator fetchOperator(String operatorId) throws DataLayerException {
		log.info("Entering fetchOperator..");
		Operator opr=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHOPERATOR_SELECT);
		try{
			//This will return null if no rows are fetched
			opr=jdbcTemplate.query(sql, new Object[]{operatorId}, new ResultSetExtractor<Operator>(){

				@Override
				public Operator extractData(ResultSet rs) throws SQLException, DataAccessException {
					Operator temp=null;
					while(rs.next()){
						temp=new Operator();
						temp.setId(rs.getString("OPERATOR_ID"));
						temp.setName(rs.getString("OPERATOR_NAME"));
						temp.setValue(rs.getString("OPERATOR_VALUE"));
					}
					return temp;
				}

			});
			//Commenting the below code because it will throw an error if no rows are fetched
			/*opr=jdbcTemplate.query(sql, new Object[]{String.valueOf(operatorId)}, new ResultSetExtractor<Operator>(){
				@Override
				public Operator extractData(ResultSet rs) throws SQLException, DataAccessException {
					Operator temp=null;
					while(rs.next()){
						temp=new Operator();
						temp.setId(rs.getString("OPERATOR_ID"));
						temp.setName(rs.getString("OPERATOR_NAME"));
						temp.setValue(rs.getString("OPERATOR_VALUE"));
					}
					return temp;
				}
			});
			if(null==opr) throw new DataLayerException("Operator not found");*/
		}catch(Exception e) {
			log.error("Error in fetchOperator",e);
			throw new DataLayerException(e);
		}
		log.debug("Operator fetched"+opr);
		log.info("Exit: fetchOperator");
		return opr;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#fetchRuleOperatorMappingForRule(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<RuleOperatorMapping> fetchRuleOperatorMappingForRule(String ruleId) throws DataLayerException{

		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHRULEOPERATORMAPPINGFORRULE_SELECT);
		/*String sql="select rom.RULES_OPERATOR_ID,rom.RULE_ID,rom.OPERATOR_ID,ropr.OPERATOR_NAME,ropr.OPERATOR_VALUE from " +
				"PAC_RE_RULES_OPR_MAPPING rom join PAC_RE_OPERATOR ropr " +
				"on rom.OPERATOR_ID = ropr.OPERATOR_ID  where rom.RULE_ID =?";*/

		List<RuleOperatorMapping> operatorsMappings = jdbcTemplate.query(sql, new Object[]{ruleId}, new ResultSetExtractor<List<RuleOperatorMapping>>() {

			@Override
			public List<RuleOperatorMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<RuleOperatorMapping> operatorMappingList = new ArrayList<RuleOperatorMapping>();
				while(rs.next()){
					RuleOperatorMapping rom = new RuleOperatorMapping();

					rom.setId(rs.getString(1));
					rom.setRuleId(rs.getString(2));
					rom.setOperatorId(rs.getString(3));

					Operator o = new Operator();
					o.setId(rs.getString(3));
					o.setName(rs.getString(4));
					o.setValue(rs.getString(5));

					rom.setOperator(o);

					operatorMappingList.add(rom);

				}
				return operatorMappingList;
			}

		});

		return operatorsMappings;

	}


	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#fetchOperatorsAllowedForRule(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Operator> fetchOperatorsAllowedForRule(String ruleId) throws DataLayerException {

		//String sql="select rom.OPERATOR_ID,ropr.OPERATOR_NAME,ropr.OPERATOR_VALUE from PAC_RE_RULES_OPR_MAPPING rom join PAC_RE_OPERATOR ropr on rom.OPERATOR_ID = ropr.OPERATOR_ID  where rom.RULE_ID =?";
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHOPERATORSALLOWEDFORRULE_SELECT);
		List<Operator> operators = jdbcTemplate.query(sql, new Object[]{ruleId}, new ResultSetExtractor<List<Operator>>() {

			@Override
			public List<Operator> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Operator> operatorList = new ArrayList<Operator>();
				while(rs.next()){
					Operator o = new Operator();
					o.setId(rs.getString("OPERATOR_ID"));
					o.setName(rs.getString("OPERATOR_NAME"));
					o.setValue(rs.getString("OPERATOR_VALUE"));

					operatorList.add(o);
				}
				return operatorList;
			}

		});

		return operators;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#saveAssignOperatorsToRule(java.lang.String, java.util.List, java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public void saveAssignOperatorsToRule(final String ruleId, final  List<String> operatorIdToAssign,final List<String> operatorIdToUnAssign) throws DataLayerException {

		try {
			if (null != operatorIdToUnAssign && operatorIdToUnAssign.size() >0 ) {
				//String deleteMappingSql = "delete from FSMMGR.PAC_RE_RULES_OPR_MAPPING rom where rom.RULE_ID=:ruleId and rom.OPERATOR_ID in (:operatorIdToUnassign) ";
				String deleteMappingSql =accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNOPERATORSTORULE_DELETE);
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ruleId", ruleId);
				paramMap.put("operatorIdToUnassign", operatorIdToUnAssign);
				namedJdbcTemplatePac.update(deleteMappingSql, paramMap);
			}


			if (null != operatorIdToAssign && operatorIdToAssign.size() >0) {
				//String sql = "insert into FSMMGR.PAC_RE_RULES_OPR_MAPPING (RULES_OPERATOR_ID,RULE_ID,OPERATOR_ID) values (FSMMGR.RULES_OPR_MAPPING_PK_SQ.NEXTVAL,?,?)";
				String sql=accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNOPERATORSTORULE_INSERT);
				jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {
						String oprid = operatorIdToAssign.get(index);

						ps.setString(1, ruleId);
						ps.setString(2, oprid);
					}

					@Override
					public int getBatchSize() {
						return operatorIdToAssign.size();
					}
				});
			}
		} catch (Exception e) {
			throw new DataLayerException(e);
		}


	}



	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#isOperatorNameExists(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public boolean isOperatorNameExists(String name) throws DataLayerException {
		log.debug("Entered isOperatorNameExists method");
		boolean result=false;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_ISOPERATORNAMEEXISTS_SELECT);
		//String sql="select COUNT(OPERATOR_NAME) from FSMMGR.PAC_RE_OPERATOR where OPERATOR_NAME=?";
		try{
			int i=jdbcTemplate.queryForInt(sql, new Object[]{name});
			if(i>0) result=true;
		} catch(Exception e){
			throw new DataLayerException("Error in isOperatorNameExists method",e);
		}
		log.info("Checking Operator name in DB:"+name+",Found="+result);
		log.debug("Exiting isOperatorNameExists method");
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#isOperatorValueExists(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public boolean isOperatorValueExists(String value) throws DataLayerException {
		log.debug("Entered isOperatorValueExists method");
		boolean result=false;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_ISOPERATORVALUEEXISTS_SELECT);
		//String sql="select COUNT(OPERATOR_VALUE) from FSMMGR.PAC_RE_OPERATOR where OPERATOR_VALUE=?";
		try{

			int i=jdbcTemplate.queryForInt(sql, new Object[]{value});
			if(i>0) result=true;
		} catch(Exception e){
			throw new DataLayerException("Error in isOperatorNameExists method",e);
		}
		log.info("Checking Operator value in DB:"+value+",Found="+result);
		log.debug("Exiting isOperatorValueExists method");
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#fetchOperatorsAllowedForSubrule(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Operator> fetchOperatorsAllowedForSubrule(String subruleId) throws DataLayerException {
		log.info("Fetching operator for the subrule:"+subruleId);
		List<Operator> operators=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHOPERATORSALLOWEDFORSUBRULE_SELECT);
		//String sql="select som.OPERATOR_ID,opr.OPERATOR_NAME,opr.OPERATOR_VALUE from PAC_RE_SUBRULE_OPR_MAPPING som join PAC_RE_OPERATOR opr on som.OPERATOR_ID = opr.OPERATOR_ID  where som.SUBRULE_ID =?";
		operators = jdbcTemplate.query(sql, new Object[]{subruleId}, new ResultSetExtractor<List<Operator>>() {

			@Override
			public List<Operator> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Operator> operatorList = new ArrayList<Operator>();
				while(rs.next()){
					Operator o = new Operator();
					o.setId(rs.getString("OPERATOR_ID"));
					o.setName(rs.getString("OPERATOR_NAME"));
					o.setValue(rs.getString("OPERATOR_VALUE"));

					operatorList.add(o);
				}
				return operatorList;
			}

		});
		return operators;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#saveAssignOperatorsToSubrule(java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public boolean saveAssignOperatorsToSubrule(final String subruleId,final  List<String> operatorIds,final List<String> operatorIdsUnassigned) throws DataLayerException {
		log.info("Saving Operator Subrule mapping");
		boolean result=true;
		//String deleteMappingSql = "delete from PAC_RE_SUBRULE_OPR_MAPPING where SUBRULE_ID =? ";
		if(StringUtils.isBlank(subruleId)) throw new DataLayerException("Subrule Id is null");

		try{
			if(null!=operatorIdsUnassigned && operatorIdsUnassigned.size()>0){
				log.info("Unassigning specific operators for subrule:"+subruleId);
				String deleteMappingSql=accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNOPERATORSTOSUBRULE_DELETE);
				//String deleteMappingSql = "delete from PAC_RE_SUBRULE_OPR_MAPPING where SUBRULE_ID =:subruleId AND OPERATOR_ID IN (:operatorIdsUnassigned)";
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("subruleId", subruleId);
				paramMap.put("operatorIdsUnassigned", operatorIdsUnassigned);

				namedJdbcTemplatePac.update(deleteMappingSql, paramMap);

			}
			//insert
			String insertMappingSql=accessProps.getFromProps(CommonConstants.QUERY_SAVEASSIGNOPERATORSTOSUBRULE_INSERT);
			//String insertMappingSql="insert into PAC_RE_SUBRULE_OPR_MAPPING values(FSMMGR.SUBRULE_OPR_MAPPING_PK_SQ.NEXTVAL,?,?)";

			if(null!=operatorIds && operatorIds.size()>0){
				log.debug("Inserting row in subrule_operator mapping");
				int []i=jdbcTemplate.batchUpdate(insertMappingSql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {
						String oprid = operatorIds.get(index);

						ps.setString(1, subruleId);
						ps.setString(2, oprid);
					}

					@Override
					public int getBatchSize() {
						return operatorIds.size();
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
	 * @see org.pjr.rulesengine.daos.OperatorDao#operatorNamesInSubruleLogic(java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> operatorNamesInSubruleLogic(String subruleid, List<String> operatorIdsUnassigned) {
		log.info("Entered operatorNamesInSubruleLogic method");
		List<String> operatorNames=new ArrayList<String>();
		String sql=accessProps.getFromProps(CommonConstants.QUERY_OPERATORNAMESINSUBRULELOGIC_SELECT);
		/*String sql="select opr.OPERATOR_NAME "
				+"from PAC_RE_SUBRULE_OPR_MAPPING som join PAC_RE_SUBRULE_LOGIC sl "
				+"on sl.SUBRULES_OPERATOR_ID=som.SUBRULE_OPERATOR_ID "
				+"and sl.SUBRULE_ID=som.SUBRULE_ID "
				+"join PAC_RE_OPERATOR opr on som.OPERATOR_ID=opr.OPERATOR_ID "
				+"where som.SUBRULE_ID=:subruleId and som.OPERATOR_ID IN (:operatorIds)";*/

		HashMap< String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("operatorIds", operatorIdsUnassigned);
		paramMap.put("subruleId", subruleid);

		operatorNames=namedJdbcTemplatePac.query(sql, paramMap,new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> oprNames =  new ArrayList<String>();

				while (rs.next()) {
					oprNames.add(rs.getString(1));
				}
				return oprNames;
			}
		});
		log.debug("Got the used operators in subrule logic: "+operatorNames);
		log.info("Exiting operatorNamesInSubruleLogic method");
		return operatorNames;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#getRulesForOperator(long)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> getRulesForOperator(String operatorId) {
		log.debug("Trying to fetch rules using operatorId:" +operatorId);
		List<String> ruleNames=new ArrayList<String>();
		String sql=accessProps.getFromProps(CommonConstants.QUERY_GETRULESFOROPERATOR_SELECT);
		/*String sql="select r.RULE_NAME "
			+"from PAC_RE_RULES r join PAC_RE_RULES_OPR_MAPPING ro "
			+"on r.RULE_ID=ro.RULE_ID "
			+"where ro.OPERATOR_ID=?";*/
		ruleNames=jdbcTemplate.query(sql, new Object[]{operatorId},new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> ruleNames =  new ArrayList<String>();

				while (rs.next()) {
					ruleNames.add(rs.getString(1));
				}
				return ruleNames;
			}
		});
		log.info("Operator id: "+operatorId+" is used by these rules :"+ruleNames);
		return ruleNames;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#getSubrulesForOperator(long)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> getSubrulesForOperator(String operatorId) {
		log.debug("Trying to fetch Subrules using operatorId:" +operatorId);
		List<String> subruleNames=new ArrayList<String>();
		String sql=accessProps.getFromProps(CommonConstants.QUERY_GETSUBRULESFOROPERATOR_SELECT);
		/*String sql="select s.SUBRULE_NAME "
			+"from PAC_RE_SUBRULE s join PAC_RE_SUBRULE_OPR_MAPPING so "
			+"on s.SUBRULE_ID=so.SUBRULE_ID "
			+"where so.OPERATOR_ID=?";*/

		subruleNames=jdbcTemplate.query(sql, new Object[]{operatorId},new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> sruleNames =  new ArrayList<String>();

				while (rs.next()) {
					sruleNames.add(rs.getString(1));
				}
				return sruleNames;
			}
		});
		log.info("Operator id: "+operatorId+" is used by these subrules :"+subruleNames);
		return subruleNames;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.OperatorDao#fetchOperatorsAssignedToRule(java.util.List, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> fetchOperatorsAssignedToRule(List<String> operatorIdsToUnassign, String ruleId) throws DataLayerException {


		List<String> operatorNames=new ArrayList<String>();

		if (null != operatorIdsToUnassign && operatorIdsToUnassign.size() >0) {
			String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHOPERATORSASSIGNEDTORULE_SELECT);
			/*String sql = "select opr.OPERATOR_NAME from FSMMGR.PAC_RE_RULES_OPR_MAPPING ropm "
					+ " left outer join FSMMGR.PAC_RE_RULE_LOGIC rl on ropm.RULES_OPERATOR_ID = rl.RULE_OPERATOR_MAP_ID "
					+ " left outer join FSMMGR.PAC_RE_OPERATOR opr on ropm.OPERATOR_ID = opr.OPERATOR_ID "
					+ " where ropm.OPERATOR_ID in (:operatorIds) and rl.RULE_ID=:ruleId ";*/
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ruleId", ruleId);
			paramMap.put("operatorIds", operatorIdsToUnassign);
			operatorNames = namedJdbcTemplatePac.query(sql, paramMap, new ResultSetExtractor<List<String>>() {

				@Override
				public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<String> operatorNamesTemp = new ArrayList<String>();

					while (rs.next()) {
						operatorNamesTemp.add(rs.getString(1));
					}
					return operatorNamesTemp;
				}

			});
		}
		return operatorNames;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	@TriggersRemove(cacheName={"RulesEngineImpl.getCompiledExpressions"} ,when = When.AFTER_METHOD_INVOCATION, removeAll = true)
	public boolean assignAllOperatorsToSubrule(final String subruleId) throws DataLayerException {
		// TODO Auto-generated method stub
		log.info("Saving Operator Subrule mapping");
		boolean result=true;
		if(StringUtils.isBlank(subruleId)) throw new DataLayerException("Subrule Id is null");
		final List<Operator> operators =fetchAllOperators();
		//insert
		String insertMappingSql=accessProps.getFromProps(CommonConstants.QUERY_ASSIGNALLOPERATORSTOSUBRULE_INSERT);
		if(null!=operators && operators.size()>0){
			log.debug("Inserting row in subrule_operator mapping");
			int[] i=null;
			try {
				i = jdbcTemplate.batchUpdate(insertMappingSql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int index) throws SQLException {
						String oprid = operators.get(index).getId();

						ps.setString(1, subruleId);
						ps.setString(2, oprid);
					}

					@Override
					public int getBatchSize() {
						return operators.size();
					}
				});
			} catch (DataAccessException e) {
				log.error(e);
				throw new DataLayerException("Error while Assigining operator to Subrule");
			}
			if(null==i) result=false;
			else log.debug("Operator subrule mapping saved successfully");
		}
		return result;
	}
}

