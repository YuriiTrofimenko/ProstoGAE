package org.tyaa.prostogae.entity;

import javax.persistence.Id;
/**
 * Заказ
 */
public class Order {
	
	//Код заказа, генерируется автоматически в DataStore
	@Id
	private Long id;
	//Имя пользователя Google-аккаунта
	private String username;
	//Адрес электронной почты пользователя Google-аккаунта
	private String email;
	//Задание
	private String task;
	//Состояние
	private OrderStatus state;
	//Дата добавления
	private String placedDate;
	//Желаемая дата выполнения
	private String desiredDeadlineDate;
	//Устанавливаемая администратором дата выполнения
	private String deadlineDate;
	//Дата оплаты
	private String paidDate;
	//Дата начала выполнения
	private String launchStartDate;
	//Дата завершения
	private String fulfilledDate;
	
	public Order() { }

	public Order(String username, String email, String task, OrderStatus state, String placedDate,
			String desiredDeadlineDate, String deadlineDate, String paidDate, String launchStartDate,
			String fulfilledDate) {
		super();
		this.username = username;
		this.email = email;
		this.task = task;
		this.state = state;
		this.placedDate = placedDate;
		this.desiredDeadlineDate = desiredDeadlineDate;
		this.deadlineDate = deadlineDate;
		this.paidDate = paidDate;
		this.launchStartDate = launchStartDate;
		this.fulfilledDate = fulfilledDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public OrderStatus getState() {
		return state;
	}

	public void setState(OrderStatus state) {
		this.state = state;
	}

	public String getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(String placedDate) {
		this.placedDate = placedDate;
	}

	public String getDesiredDeadlineDate() {
		return desiredDeadlineDate;
	}

	public void setDesiredDeadlineDate(String desiredDeadlineDate) {
		this.desiredDeadlineDate = desiredDeadlineDate;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public String getLaunchStartDate() {
		return launchStartDate;
	}

	public void setLaunchStartDate(String launchStartDate) {
		this.launchStartDate = launchStartDate;
	}

	public String getFulfilledDate() {
		return fulfilledDate;
	}

	public void setFulfilledDate(String fulfilledDate) {
		this.fulfilledDate = fulfilledDate;
	}

	public Long getId() {
		return id;
	}
}
