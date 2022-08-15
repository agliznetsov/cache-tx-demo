package com.example.cachedemo.transaction;

import javax.transaction.TransactionManager;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;


public class InfinispanTransactionManager extends AbstractPlatformTransactionManager {

	private TransactionManager transactionManager;

	public InfinispanTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Override
	protected Object doGetTransaction() throws TransactionException {
		try {
			return new TransactionObject(transactionManager.getTransaction());
		} catch (Exception e) {
			throw new TransactionSystemException("error", e);
		}
	}

	@Override
	protected void doBegin(Object o, TransactionDefinition transactionDefinition) throws TransactionException {
		try {
			transactionManager.begin();
		} catch (Exception e) {
			throw new TransactionSystemException("error", e);
		}
	}

	@Override
	protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
		try {
			transactionManager.commit();
		} catch (Exception e) {
			throw new TransactionSystemException("error", e);
		}
	}

	@Override
	protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
		try {
			transactionManager.rollback();
		} catch (Exception e) {
			throw new TransactionSystemException("error", e);
		}
	}

	public class TransactionObject {

		private Object currentTransactionContext;

		public TransactionObject(Object currentTransactionContext) {
			this.currentTransactionContext = currentTransactionContext;
		}

	}
}
