package com.trucontactssync.utils;

/**
 * Created by CS39 on 4/20/2017.
 */

public final class MathUtils {

    private MathUtils(){
        //avoid to create instance outside
    }

    /**
     * Return total pagination count
     * @param totalRecords
     *      total records
     * @param limitSize
     *      limit size to find total pagination size
     * @return
     *  total pagination size
     */
    public static int getTotalPagination(int totalRecords, int limitSize) {
        int totalPagination = 0;
        if(totalRecords == 0) {
            totalPagination = 0;
        }
        else if(totalRecords <= limitSize) {
            totalPagination = 1;
        }
        else if(totalRecords > limitSize) {
            totalPagination =  (totalRecords / limitSize);
            if(totalRecords % limitSize > 0) {
                totalPagination += 1;
            }
        }
        return totalPagination;
    }
}
