package edu.asu.securebanking.businessobject;

import java.util.List;

import edu.asu.securebanking.model.InternalUserTransaction;

public interface InternalUserTransactionBO {
		String save(InternalUserTransaction internalUserTransaction);
		void update(InternalUserTransaction internalUserTransaction);
		void delete(int id);
		InternalUserTransaction findTransactionById(int id);
		InternalUserTransaction findTransactionByIdAndDeptId(int id, int deptid);
		List getAllInternalUserTransactions();
		List getDeptInternalUserTransactions(int deptid);
		void deleteByEmpId(int empid);
}
