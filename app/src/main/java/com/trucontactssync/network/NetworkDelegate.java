/*
 * Copyright (c) Ravikiran Srinivasan
 */

package com.trucontactssync.network;

import android.os.Bundle;

public interface NetworkDelegate {

    /**
     * NetworkDelegate call back function which provides the bundle after the
     * network call and parsing
     *
     * @param bundle
     */
    void callBack(Bundle bundle);

}