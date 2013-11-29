package org.ejmc.android.simplechat.View;

import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: frutos
 * Date: 25/11/13
 * Time: 11:08
 */
public interface IView {
    public abstract void navigate(Class<?> destination);
    public abstract void navigate(Class<?> destination, boolean logoutFlag);
    public abstract void navigate(Class<?> destination, Bundle extras);
    public abstract void showError(String errorMessage);
}

