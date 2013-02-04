package org.pjr.rulesengine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.pjr.rulesengine.CommonConstants;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Model;
import org.pjr.rulesengine.propertyloader.PropertyLoader;


/**
 * The Class ModelDao.
 *
 * @author Sudhakar
 */
public class ModelDao {
	
	/** The data source. */
	private DataSource dataSource;
	
	/**
	 * Instantiates a new model dao.
	 *
	 * @param dataSource the data source
	 * @author  Sudhakar 
	 */
	public ModelDao(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * Checks if is model name already exists.
	 *
	 * @param fullyQualifiedClassName the fully qualified class name
	 * @return the model
	 * @throws DataLayerException the data layer exception
	 * @author  Sudhakar 
	 */
	public Model isModelNameAlreadyExists(String fullyQualifiedClassName) throws DataLayerException{		
		String sql=PropertyLoader.getProperty(CommonConstants.QUERY_FETCH_MODEL_BYNAME);
		
		QueryRunner qr = new QueryRunner(dataSource);
		
		Model model = null;
		try {
			model= qr.query(sql, new ResultSetHandler<Model>() {

				@Override
				public Model handle(ResultSet rs) throws SQLException {
					Model model=null;
					while(rs.next()){
						model=new Model();
						model.setModel_id(rs.getString("MODEL_ID"));
						model.setModel_class_name(rs.getString("MODEL_CLASS_NAME"));
					}
					return model;				
				}
				
			},new Object []{fullyQualifiedClassName});
		} catch (Exception e) {
			throw new DataLayerException(e);
		}
		
		
		return model;		
	}

}
