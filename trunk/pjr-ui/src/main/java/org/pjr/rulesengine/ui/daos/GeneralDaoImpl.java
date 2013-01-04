package org.pjr.rulesengine.ui.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.dbmodel.RuleEngineUser;
import org.pjr.rulesengine.util.AccessProperties;
import org.pjr.rulesengine.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GeneralDaoImpl implements GeneralDao {

	@Autowired
	@Qualifier("pjrJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private AccessProperties accessProps;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public RuleEngineUser getPacUser(String userId) throws TechnicalException{
		RuleEngineUser user = null;

		//String sql="select USER_ID,ACTIVE,ENABLE_DATE,DISABLE_DATE,ISADMIN from FSMMGR.PAC_USER_ADMIN where USER_ID= ? and ACTIVE=1";
		String sql=accessProps.getFromProps(CommonConstants.QUERY_GETPACUSER_SELECT);

		user = jdbcTemplate.query(sql, new Object[]{userId},new ResultSetExtractor<RuleEngineUser>(){

			@Override
			public RuleEngineUser extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				RuleEngineUser user = null;
				while (rs.next()){
					user = new RuleEngineUser();
					user.setUserId(rs.getString("USER_ID"));
					user.setActive(rs.getBoolean("ACTIVE"));
					user.setEnableDate(rs.getDate("ENABLE_DATE"));
					user.setDisableDate(rs.getDate("DISABLE_DATE"));
					user.setAdmin(rs.getBoolean("ISADMIN"));
					user.setPassword(rs.getString("PASSWORD"));
				}

				return user;
			}

		});
		return user;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
