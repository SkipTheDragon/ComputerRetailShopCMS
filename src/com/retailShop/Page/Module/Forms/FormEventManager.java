package com.retailShop.Page.Module.Forms;

import com.retailShop.Page.Module.Forms.EventListeners.FormEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormEventManager {
    Map<String, List<FormEventListener>> listeners = new HashMap<>();

    public FormEventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    public void addEvent(String event) {
        if (listeners.get(event) != null) return;
        this.listeners.put(event, new ArrayList<>());
    }

    public void subscribe(String eventType, FormEventListener listener) {
        List<FormEventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, FormEventListener listener) {
        List<FormEventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String eventType) {
        List<FormEventListener> users = listeners.get(eventType);
        for (FormEventListener listener : users) {
            listener.update(eventType);
        }
    }
}