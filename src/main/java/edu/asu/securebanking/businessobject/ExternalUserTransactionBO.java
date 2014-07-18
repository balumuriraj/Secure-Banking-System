package edu.asu.securebanking.businessobject;

import java.util.List;

import edu.asu.securebanking.model.ExternalUserTransaction;
import edu.asu.securebanking.model.InternalUserTransaction;

public interface ExternalUserTransactionBO {
		void save(ExternalUserTransaction externalUserTransaction);
		void update(ExternalUserTransaction externalUserTransaction);
		void delete(int id);
		ExternalUserTransaction findTransactionById(int id);
		List getAllExternalUsersTransactions();
		void deleteByUserId(int userId);
}
