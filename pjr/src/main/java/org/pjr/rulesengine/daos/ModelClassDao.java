package org.pjr.rulesengine.daos;

import java.util.List;

import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Model;

/**
 * The ModelClassDao.
 *
 * @author Anubhab(Infosys)
 *
 */
public interface ModelClassDao {
	/**
	 * This method inserts a list of Model class into the DB
	 * 
	 * @param models
	 * @return
	 * @throws DataLayerException
	 * 
	 * @author Anubhab
	 * Created: Jan 7, 2013
	 */
	public int[] insertIntoModelClass(List<Model> models) throws DataLayerException;
	public List<Model> fetchAllModels() throws DataLayerException;
	public boolean updateModels(List<Model> models) throws DataLayerException;
	public int[] deleteFromModel(List<String> modelIds) throws DataLayerException;
	public Model fetchModel(String modelId) throws DataLayerException;
	public Model isModelNameAlreadyExists(String name)throws DataLayerException;
}
