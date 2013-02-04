package org.pjr.rulesengine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.CommonConstants;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Attribute;
import org.pjr.rulesengine.dbmodel.Operator;
import org.pjr.rulesengine.dbmodel.Subrule;
import org.pjr.rulesengine.dbmodel.SubruleLogic;
import org.pjr.rulesengine.propertyloader.PropertyLoader;

/**
 * The Class SubRuleDao.
 *
 * @author Sudhakar
 */
public class SubRuleDao {
	
	/** The Constant log. */
	private static final Log  log = LogFactory.getLog(SubRuleDao.class);

	/** The data source. */
	private DataSource dataSource;
	
	/**
	 * Instantiates a new sub rule dao.
	 *
	 * @param dataSource the data source
	 * @author  Sudhakar
	 */
	public SubRuleDao(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * Fetch subrule.
	 *
	 * @param id the id
	 * @return the subrule
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar
	 */
	public Subrule fetchSubrule(String id) throws DataLayerException{
		
		QueryRunner qr = new QueryRunner(dataSource);
		String sql=PropertyLoader.getProperty(CommonConstants.QUERY_FETCHSUBRULE_SELECT);
		
		Subrule subrule;
		try {
			subrule = qr.query(sql, new ResultSetHandler<Subrule>() {

				@Override
				public Subrule handle(ResultSet rs) throws SQLException {
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
			},new Object[]{id});
			
			

			if(null != subrule){
				String subrulelogicsql=PropertyLoader.getProperty(CommonConstants.QUERY_FETCHSUBRULE_SELECTLOGIC);
				
				List<SubruleLogic> logic =  qr.query(subrulelogicsql, new ResultSetHandler<List<SubruleLogic>> (){

					@Override
					public List<SubruleLogic> handle(ResultSet rs) throws SQLException {
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
					
				},new Object[]{id});
				subrule.setLogic(logic);
			}			
			
			
		} catch (SQLException e) {
			log.error("", e);
			throw new DataLayerException(e);
		}
		
		return subrule;
		
	}
	
}
