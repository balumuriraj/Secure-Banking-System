package edu.asu.securebanking.dataaccessobject;

import java.util.List;

import edu.asu.securebanking.model.InternalUserTransaction;

public interface InternalUserTransactionDao {
		String save(InternalUserTransaction internalUserTransaction);
		void update(InternalUserTransaction internalUserTransaction);
		void delete(int id);
		InternalUserTransaction findUserByid(int id);
	InternalUserTransaction findTransactionById(int id);
		InternalUserTransaction findUserByidAndDeptId(int id, int deptid);
		List getAllInternalTransactions();
		List getDeptInternalTransactions(int deptid);
		void deleteByEmpId(int empid);
}
