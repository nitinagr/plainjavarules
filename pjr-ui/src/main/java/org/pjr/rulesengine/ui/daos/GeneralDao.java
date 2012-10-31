package org.pjr.rulesengine.ui.daos;

import org.pjr.rulesengine.TechnicalException;
import org.pjr.rulesengine.dbmodel.RuleEngineUser;

public interface GeneralDao {

	public RuleEngineUser getPacUser(String userId) throws TechnicalException;
}
