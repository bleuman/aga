package net.atos.si.ma.mt.astreinte.service.impl;

import net.atos.si.ma.mt.astreinte.service.NotificationService;

public class NotificationUtils{

	
	
	public static NotificationService notificationService;

	public static NotificationService getNotificationService() {
		return notificationService;
	}

	public static void setNotificationService(
			NotificationService notificationService) {
		NotificationUtils.notificationService = notificationService;
	}

	
}
