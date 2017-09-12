package org.tyaa.prostogae.entity;

/**
 * Варианты состояния заказа
 */
public enum OrderStatus {
	/**
	 * размещен
	 */
	placed
	/**
	 * отменен
	 */
	, canceled
	/**
	 * оплачен
	 */
	, paid
	/**
	 * выполняется
	 */
	, launching
	/**
	 * приостановлен
	 */
	, suspended
	/**
	 * выполнен
	 */
	, fulfilled
}
