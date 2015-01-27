package com.seierfriendapp.services;

/**
 * Created by matha on 29.11.14..
 * <p/>
 * Listener that indicates if data is collected or not
 */
public interface DataCollectedListener {
    /**
     * True or false depending on data loading success
     *
     * @param dataIsCollected - did we get the data
     * @param errors          - were there any errors
     * @param errorMessage    - if there were, what was it
     */
    public void DataCollected(boolean dataIsCollected, boolean errors, String errorMessage);
}
