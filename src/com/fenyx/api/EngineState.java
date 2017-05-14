package com.fenyx.api;

/**
 *
 * @author KiQDominaN
 */
public abstract class EngineState {

    private EngineState prevState;
    private boolean active;

    public abstract void init();
    public abstract void activate();
    public abstract void process();
    public abstract void deactivate();
    public abstract void stop();

    public final void setActive(boolean active) {
        this.active = active;

        if (active) {
            activate();
        } else
            deactivate();
    }

    public final boolean isActive() {
        return active;
    }

    public EngineState getPrevState() {
        return prevState;
    }
}
