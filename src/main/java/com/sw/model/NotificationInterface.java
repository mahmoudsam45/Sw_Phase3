package com.sw.model;

import java.util.List;

public interface NotificationInterface {
public void Add(NotifyedUser NU);
public void Remove(NotifyedUser NU);
public List<NotifyedUser> ListNotification(String Email);
}
