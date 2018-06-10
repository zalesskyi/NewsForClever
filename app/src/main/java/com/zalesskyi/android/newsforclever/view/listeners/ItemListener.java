package com.zalesskyi.android.newsforclever.view.listeners;

public interface ItemListener<T> {

    /**
     * Открытие элемента списка.
     *
     * @param item модель элемента списка.
     */
    void open(T item);
}
