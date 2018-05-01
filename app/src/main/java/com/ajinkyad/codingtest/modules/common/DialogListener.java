package com.ajinkyad.codingtest.modules.common;

/**
 * This interface is used to handle the Actions performed by the User on Alert Dialog Box
 *
 * @author AjinkyaD
 * @version 1.0
 */

public interface DialogListener {

    /**
     * This function is called when the Positive Action in performed on the Alert Dialog Box
     *
     * @param dialogID    -The Current Dialog ID
     * @param updatedData - The Data after the Option is Selected by user
     */
    void onPositiveAction(int dialogID, Object updatedData);

    /**
     * This function is called when the Negative Action in performed on the Alert Dialog Box
     *
     * @param dialogID    -The Current Dialog ID
     * @param updatedData - The Data after the Option is Selected by user
     */
    void onNegativeAction(int dialogID, Object updatedData);
}
