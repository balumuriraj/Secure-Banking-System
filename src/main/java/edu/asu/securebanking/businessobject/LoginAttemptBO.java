package edu.asu.securebanking.businessobject;

import edu.asu.securebanking.model.ExternalAccount;
import edu.asu.securebanking.model.InternalAccount;
import edu.asu.securebanking.model.LoginAttempt;

public interface LoginAttemptBO {
		void save(LoginAttempt LoginAttempt);
		void update(LoginAttempt LoginAttempt);
		void delete(LoginAttempt LoginAttempt);
		ExternalAccount findAccountByNameExternal(String username);
		InternalAccount findAccountByNameInternal(String username);
		String findRecoveryAccount(String username, String email, String tel, String sec_question, String sec_answer);
}
