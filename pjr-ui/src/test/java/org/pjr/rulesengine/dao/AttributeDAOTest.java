/**
 * @author Anubhab
 * Created: Jan 25, 2013
 */
package org.pjr.rulesengine.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pjr.rulesengine.DataLayerException;
import org.pjr.rulesengine.dbmodel.Attribute;
import org.pjr.rulesengine.ui.daos.AttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The AttributeDAOTest.
 *
 * @author Anubhab(Infosys)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = { "classpath:ruleui-applicationContext.xml" })
public class AttributeDAOTest {
	@Autowired
	private AttributeDao attributeDao;
	@Test
	public void updateTest(){
		List<Attribute> attributeList=new ArrayList<Attribute>();
		Attribute attr=new Attribute();
		attr.setName("Age(10)");
		attr.setValue("10");
		attr.setId("2008");
		attributeList.add(attr);
		try {
			attributeDao.updateAttribute(attributeList);
		} catch (DataLayerException e) {
			
			e.printStackTrace();
		}
	}
}
