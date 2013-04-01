package org.pjr.rulesengine.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.pjr.rulesengine.dbmodel.Attribute;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.RuleSubruleMapping;
import org.pjr.rulesengine.dbmodel.SubruleAttributeMapping;
import org.pjr.rulesengine.dbmodel.SubruleOperatorMapping;
import org.pjr.rulesengine.util.AccessProperties;
import org.pjr.rulesengine.util.CommonConstants;

/**
 * The Class SubruleRuleMappingDaoImpl.
 *
 * @author Sudhakar
 */
@Component
@SuppressWarnings("unused")
public class SubruleRuleMappingDaoImpl implements SubruleRuleMappingDao{

	private static final Log log = LogFactory.getLog(SubruleRuleMappingDaoImpl.class);

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
	 * @see org.pjr.rulesengine.daos.SubruleRuleMappingDao#fetchAllRuleSubruleMapping()
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<RuleSubruleMapping> fetchAllRuleSubruleMapping() {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLRULESUBRULEMAPPING_SELECT);
		//String sql="select rsrm.RULE_SUBRULE_ID,rsrm.RULE_ID,rsrm.SUBRULE_ID, sr.SUBRULE_NAME,sr.SUBRULE_DESCRIPTION from FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING rsrm join FSMMGR.PAC_RE_SUBRULE sr on rsrm.SUBRULE_ID = sr.SUBRULE_ID";

		List<RuleSubruleMapping> ruleSubruleMappingList = jdbcTemplate.query(sql,  new ResultSetExtractor<List<RuleSubruleMapping>>() {

			@Override
			public List<RuleSubruleMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<RuleSubruleMapping> ruleSubruleMappingTempList  = new ArrayList<RuleSubruleMapping>();

				while(rs.next()){
					RuleSubruleMapping rsm =  new RuleSubruleMapping();

					rsm.setId(rs.getString(1));
					rsm.setRuleId(rs.getString(2));
					rsm.setSubRuleId(rs.getString(3));
					rsm.setSubRuleName(rs.getString(4));
					rsm.setSubRuleDescription(rs.getString(5));

					ruleSubruleMappingTempList.add(rsm);
				}

				return ruleSubruleMappingTempList;
			}
		});

		return ruleSubruleMappingList;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleRuleMappingDao#fetchAllRuleSubruleMapping(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<RuleSubruleMapping> fetchAllRuleSubruleMapping(String ruleId) {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLRULESUBRULEMAPPING_SELECTBYRULE);
		//String sql="select rsrm.RULE_SUBRULE_ID,rsrm.RULE_ID,rsrm.SUBRULE_ID , sr.SUBRULE_NAME,sr.SUBRULE_DESCRIPTION from FSMMGR.PAC_RE_RULES_SUBRULE_MAPPING rsrm join FSMMGR.PAC_RE_SUBRULE sr on rsrm.SUBRULE_ID = sr.SUBRULE_ID where rsrm.RULE_ID=?";

		List<RuleSubruleMapping> ruleSubruleMappingList = jdbcTemplate.query(sql, new Object[]{ruleId}, new ResultSetExtractor<List<RuleSubruleMapping>>() {

			@Override
			public List<RuleSubruleMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<RuleSubruleMapping> ruleSubruleMappingTempList  = new ArrayList<RuleSubruleMapping>();

				while(rs.next()){
					RuleSubruleMapping rsm =  new RuleSubruleMapping();

					rsm.setId(rs.getString(1));
					rsm.setRuleId(rs.getString(2));
					rsm.setSubRuleId(rs.getString(3));
					rsm.setSubRuleName(rs.getString(4));
					rsm.setSubRuleDescription(rs.getString(5));

					ruleSubruleMappingTempList.add(rsm);
				}

				return ruleSubruleMappingTempList;
			}
		});

		return ruleSubruleMappingList;

	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleRuleMappingDao#fetchAllAttributeMapping(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<SubruleAttributeMapping> fetchAllAttributeMapping(String subRuleId) {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLATTRIBUTEMAPPING_SELECT);
		/*String sql ="select sattrMap.SUBRULE_ATTR_ID,sattrMap.SUBRULE_ID,sattrMap.ATTR_ID, " +
					" attr.ATTR_ID,attr.ATTR_NAME,attr.ATTR_VALUE "
				+" from FSMMGR.PAC_RE_SUBRULE_ATTR_MAPPING sattrMap " +
				" left outer join FSMMGR.PAC_RE_OBECT_ATTR attr on sattrMap.ATTR_ID = attr.ATTR_ID " +
				" where SUBRULE_ID=? ";*/

		List<SubruleAttributeMapping>  subruleAttributeMappings = jdbcTemplate.query(sql, new Object[]{subRuleId}, new ResultSetExtractor<List<SubruleAttributeMapping>>() {

			@Override
			public List<SubruleAttributeMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<SubruleAttributeMapping>  attrMapTemp = new ArrayList<SubruleAttributeMapping>();

				while(rs.next()){
					SubruleAttributeMapping subruleAttributeMapping = new SubruleAttributeMapping();

					subruleAttributeMapping.setId(rs.getString(1));
					subruleAttributeMapping.setSubRuleId(rs.getString(2));
					subruleAttributeMapping.setAttributeId(rs.getString(3));

					Attribute attr = new Attribute();
					attr.setId(rs.getString(4));
					attr.setName(rs.getString(5));
					attr.setValue(rs.getString(6));
					subruleAttributeMapping.setAttribute(attr);

					attrMapTemp.add(subruleAttributeMapping);
				}

				return attrMapTemp;
			}
		});

		return subruleAttributeMappings;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleRuleMappingDao#fetchAllOperatorMapping(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<SubruleOperatorMapping> fetchAllOperatorMapping(String subRuleId) {
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALLOPERATORMAPPING_SELECT);
		/*String sql ="select oprMap.SUBRULE_OPERATOR_ID,oprMap.SUBRULE_ID,oprMap.OPERATOR_ID ," +
				" opr.OPERATOR_ID,opr.OPERATOR_NAME,opr.OPERATOR_VALUE " +
				" from FSMMGR.PAC_RE_SUBRULE_OPR_MAPPING oprMap " +
				" left outer join FSMMGR.PAC_RE_OPERATOR opr on oprMap.OPERATOR_ID = opr.OPERATOR_ID " +
				" where SUBRULE_ID =? ";*/

		List<SubruleOperatorMapping> subruleOperatorMappings =  jdbcTemplate.query(sql, new Object[]{subRuleId},  new ResultSetExtractor<List<SubruleOperatorMapping> >() {

			@Override
			public List<SubruleOperatorMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<SubruleOperatorMapping>  sOprMapTemp = new ArrayList<SubruleOperatorMapping>();

				while(rs.next()){
					SubruleOperatorMapping subruleOperatorMapping = new SubruleOperatorMapping();

					subruleOperatorMapping.setId(rs.getString(1));
					subruleOperatorMapping.setSubRuleId(rs.getString(2));
					subruleOperatorMapping.setOperatorId(rs.getString(3));


					Operator operator = new Operator();
					operator.setId(rs.getString(4));
					operator.setName(rs.getString(5));
					operator.setValue(rs.getString(6));
					subruleOperatorMapping.setOperator(operator);

					sOprMapTemp.add(subruleOperatorMapping);
				}
				return sOprMapTemp;
			}
		});

		return subruleOperatorMappings;
	}
	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.SubruleRuleMappingDao#fetchRuleNamesForSubrule(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<String> fetchRuleNamesForSubrule(String id) {
		List<String> ruleNames=new ArrayList<String>();
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHRULENAMESFORSUBRULE_SELECT);
		/*String sql="select r.RULE_NAME from PAC_RE_RULES r join PAC_RE_RULES_SUBRULE_MAPPING rsm "
				+"on r.RULE_ID=rsm.RULE_ID where rsm.SUBRULE_ID=?";*/
		ruleNames=jdbcTemplate.query(sql,new Object[]{id}, new ResultSetExtractor<List<String>>() {

			@Override
			public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> ruleNames =  new ArrayList<String>();

				while (rs.next()) {
					ruleNames.add(rs.getString(1));
				}
				return ruleNames;
			}

		});
		log.debug("Subrule id:"+id+" is referred by rules:"+ruleNames);
		return ruleNames;
	}


}
