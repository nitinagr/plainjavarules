/**
 * @author Anubhab(pjr.org)
 * Created: Jan 7, 2013
 */
package org.pjr.rulesengine.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.util.AccessProperties;
import org.pjr.rulesengine.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Anubhab(pjr.org)
 *
 */
@Component(value="modelDao")
public class ModelClassDaoImpl implements ModelClassDao {
	
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(ModelClassDaoImpl.class);
	
	/** The jdbc template. */
	@Autowired
	@Qualifier("pjrJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/** The access props. */
	@Autowired
	private AccessProperties accessProps;

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.ModelClassDao#insertIntoModelClass(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public int[] insertIntoModelClass(final List<Model> models) throws DataLayerException {
		log.debug("Entering insertIntoModelClass..");
		int[] i=null;
		try{
			if (null != models && models.size() != 0){
				i=jdbcTemplate.batchUpdate(accessProps.getFromProps(CommonConstants.QUERY_INSERTINTO_MODEL), new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int count) throws SQLException {
						Model model = models.get(count);

						String value = model.getModel_class_name();

						//Setting the values to the prepared statement
						ps.setString(1, value);
					}

					@Override
					public int getBatchSize() {
						return models.size();
					}
				});
			} else {
				log.info("List is empty");
				throw new DataLayerException("List is empty");
			}
		} catch(Exception e) {
			log.error("Error in insertIntoModelClass",e);
			throw new DataLayerException(e);
		}
		return i;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.ModelClassDao#fetchAllModels()
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Model> fetchAllModels() throws DataLayerException {
		log.debug("Entering fetchAllModels..");
		List<Model> modelList=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCHALL_MODEL);
		try{
			modelList=jdbcTemplate.query(sql, new ResultSetExtractor<List<Model>>(){

				@Override
				public List<Model> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Model> returningList=new ArrayList<Model>();
					while(rs.next()){
						Model md=new Model();
						md.setModel_id(rs.getString("MODEL_ID"));
						md.setModel_class_name(rs.getString("MODEL_CLASS_NAME"));
						
						returningList.add(md);
					}
					return returningList;
				}
				
			});
		}catch(Exception e) {
			log.error("Error in fetchAllModels",e);
			throw new DataLayerException(e);
		}
		return modelList;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.ModelClassDao#updateModels(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public boolean updateModels(final List<Model> models) throws DataLayerException {
		log.debug("Entering updateModels..");
		boolean result=false;
		int[] i=null;

		String sql=accessProps.getFromProps(CommonConstants.QUERY_UPDATE_MODEL);
		try{
			i=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				/* Getting the batch size */
				@Override
				public int getBatchSize() {
					return models.size();
				}

				/* Setting the values into Prepared statement */
				@Override
				public void setValues(PreparedStatement ps, int count)throws SQLException {
					ps.setString(1,models.get(count).getModel_class_name());
					ps.setString(2,models.get(count).getModel_id());
				}
			});
			if(null!=i && i.length!=0){
				result=true;
			}
		}catch(Exception e) {
			log.error("Error in updateModels",e);
			throw new DataLayerException(e);
		}

		log.debug("Created the params");
		return result;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.ModelClassDao#deleteFromModel(java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=DataLayerException.class)
	public int[] deleteFromModel(final List<String> modelIds) throws DataLayerException {
		log.debug("Entering deleteFromModel..");
		int[] i=null;
		String sql=accessProps.getFromProps(CommonConstants.QUERY_DELETE_MODEL);
		try{
			i=jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				/* Getting the batch size */
				@Override
				public int getBatchSize() {
					return modelIds.size();
				}

				/* Setting the values into Prepared statement */
				@Override
				public void setValues(PreparedStatement ps, int count)throws SQLException {
					ps.setString(1, modelIds.get(count));
				}
			});
		}catch(Exception e) {
			log.error("Error in fetchModel",e);
			throw new DataLayerException(e);
		}
		return i;
	}

	/* (non-Javadoc)
	 * @see org.pjr.rulesengine.daos.ModelClassDao#fetchModel(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public Model fetchModel(String modelId) throws DataLayerException {
		Model model=null;
		log.debug("Entering fetchModel..");
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCH_MODEL);
		try{
			model=jdbcTemplate.query(sql, new Object[]{modelId}, new ResultSetExtractor<Model>(){

				@Override
				public Model extractData(ResultSet rs) throws SQLException, DataAccessException {
					Model model=null;
					while(rs.next()){
						model=new Model();
						model.setModel_id(rs.getString("MODEL_ID"));
						model.setModel_class_name(rs.getString("MODEL_CLASS_NAME"));
					}
					return model;
				}
				
			});
		}catch(Exception e) {
			log.error("Error in fetchModel",e);
			throw new DataLayerException(e);
		}
		return model;
	}

	@Override
	public Model isModelNameAlreadyExists(String name) throws DataLayerException {
		log.debug("Entering isModelNameAlreadyExists..");
		
		String sql=accessProps.getFromProps(CommonConstants.QUERY_FETCH_MODEL_BYNAME);
		Model stub=null;
		try{
			stub=jdbcTemplate.query(sql, new Object[]{name},new ResultSetExtractor<Model>() {

				@Override
				public Model extractData(ResultSet rs) throws SQLException, DataAccessException {
					Model model=null;
					while(rs.next()){
						model=new Model();
						model.setModel_id(rs.getString("MODEL_ID"));
						model.setModel_class_name(rs.getString("MODEL_CLASS_NAME"));
					}
					return model;
				}
			});
		}catch(Exception e) {
			log.error("Error in fetchModel",e);
			throw new DataLayerException(e);
		}
		return stub;
	}

}
