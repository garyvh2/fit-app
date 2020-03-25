package com.gitgud.fitapp.ui.modules.steps;

import java.util.Observable;

public class StepsBroadcastObserver extends Observable {
    private static StepsBroadcastObserver INSTANCE = new StepsBroadcastObserver();

    public static StepsBroadcastObserver getInstance() {
        return INSTANCE;
    }

    private StepsBroadcastObserver() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}