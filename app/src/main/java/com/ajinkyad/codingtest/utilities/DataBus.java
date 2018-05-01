package com.ajinkyad.codingtest.utilities;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;

public class DataBus extends Bus {

    public DataBus(ThreadEnforcer threadEnforcer) {
        super(threadEnforcer);
    }

    private ArrayList registeredObjects = new ArrayList<>();

    @Override
    public void register(Object object) {
        removeBusListeners();
        if (!registeredObjects.contains(object)) {
            registeredObjects.add(object);
            super.register(object);
        }
    }

    @Override
    public void unregister(Object object) {
        if (registeredObjects.contains(object)) {
            registeredObjects.remove(object);
            super.unregister(object);
        }
    }

    private void removeBusListeners() {
        if (registeredObjects != null) {

            for (Object currentObject :
                    registeredObjects) {
                super.unregister(currentObject);
            }

            registeredObjects.clear();
        }
    }
}
