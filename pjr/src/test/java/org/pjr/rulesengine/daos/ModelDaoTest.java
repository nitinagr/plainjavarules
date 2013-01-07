/**
 * @author Anubhab
 * Created: Jan 7, 2013
 */
package org.pjr.rulesengine.daos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * The ModelDaoTest.
 *
 * @author Anubhab(Infosys)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = { "classpath:pjrContext.xml" })
@TransactionConfiguration(defaultRollback=false)
public class ModelDaoTest {
	private Log log = LogFactory.getLog(ModelDaoTest.class);
	
	@Autowired
	private ModelClassDao modelDao;
	@Test
	public void insertTest(){
		Model md1=new Model();
		md1.setModel_class_name("org.me.jvm");
		Model md2=new Model();
		md2.setModel_class_name("org.me.ask");
		List<Model> listt=new ArrayList<Model>();
		listt.add(md1);
		listt.add(md2);
		try {
			modelDao.insertIntoModelClass(listt);
		} catch (DataLayerException e) {
			log.error("",e);
		}
	}
	@Test
	public void fetchAllTest(){
		try {
			List<Model> listt=modelDao.fetchAllModels();
			for(Model temp:listt){
				log.info(temp);
			}
		} catch (DataLayerException e) {
			log.error("",e);
		}
	}
	@Test
	public void fetchTest(){
		try {
			Model md=modelDao.fetchModel("2000");
			log.info(md);
		} catch (DataLayerException e) {
			log.error("",e);
		}
	}
	@Test
	public void deleteTest(){
		List<String> modelIds=new ArrayList<String>();
		modelIds.add("2000");
		try {
			modelDao.deleteFromModel(modelIds);
		} catch (DataLayerException e) {
			log.error("",e);
		}
	}
	@Test
	public void updateTest(){
		Model md1=new Model();
		md1.setModel_id("2002");
		md1.setModel_class_name("org.me.jvmTest");
		Model md2=new Model();
		md2.setModel_id("2003");
		md2.setModel_class_name("org.me.askTest");
		List<Model> listt=new ArrayList<Model>();
		listt.add(md1);
		listt.add(md2);
		try {
			boolean s=modelDao.updateModels(listt);
			log.info(s);
		} catch (DataLayerException e) {
			log.error("",e);
		}
	}
	@Test
	public void fetchByNameTest(){
		try {
			Model md=modelDao.isModelNameAlreadyExists("org.me.jvmTest");
			log.info(md);
		} catch (DataLayerException e) {
			log.error("",e);
		}
	}
}
