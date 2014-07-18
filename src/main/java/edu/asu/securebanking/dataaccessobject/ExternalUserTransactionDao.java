package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.model.ExternalUserTransaction;

public interface ExternalUserTransactionDao {
		void save(ExternalUserTransaction externalUserTransaction);
		void update(ExternalUserTransaction externalUserTransaction);
		void delete(int id);
		ExternalUserTransaction findTransactionById(int id);
		List getAllExternalUsersTransactions();
		void deleteByUserId(int userId);
}
