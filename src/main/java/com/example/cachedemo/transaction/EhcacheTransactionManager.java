package com.example.cachedemo.transaction;

import net.sf.ehcache.TransactionController;
import net.sf.ehcache.transaction.local.LocalTransactionContext;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class EhcacheTransactionManager extends AbstractPlatformTransactionManager {

	private TransactionController transactionController;

	public EhcacheTransactionManager(TransactionController transactionController) {
		this.transactionController = transactionController;
	}

	@Override
	protected Object doGetTransaction() throws TransactionException {
		return new EhcacheTransactionObject(transactionController.getCurrentTransactionContext());
	}

	@Override
	protected void doBegin(Object o, TransactionDefinition transactionDefinition) throws TransactionException {
		int timeout = transactionDefinition.getTimeout();
		if (timeout != TransactionDefinition.TIMEOUT_DEFAULT) {
			transactionController.begin(timeout);
		} else {
			transactionController.begin();
		}
	}

	@Override
	protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
		transactionController.commit();
	}

	@Override
	protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
		transactionController.rollback();
	}

	public class EhcacheTransactionObject {

		private LocalTransactionContext currentTransactionContext;

		public EhcacheTransactionObject(LocalTransactionContext currentTransactionContext) {
			this.currentTransactionContext = currentTransactionContext;
		}

	}

}
