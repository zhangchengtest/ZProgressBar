package com.zpj.progressbar.demo.utils;

import com.zpj.progressbar.demo.BuildConfig;

/**
 * Created by Atom on 2017/10/26.
 */

public class HttpUrl {

    public static final String BASE_URL = "/v1/peer/web/";

    //
    public static final String MACHINE_ADD = BASE_URL + "machines/";

    public static final String MACHINE_CONFIG = BASE_URL + "machines/getConfigByMachineCode/";

    public static final String RECENT_FILES = BASE_URL + "files/";

    public static final String DOWNLOAD_REQUEST = BASE_URL + "peers/downloadFile/";

    public static final String DOWNLOAD_STATUS = BASE_URL + "peers/downloadStatus/";

    public static final String DOWNLOADED_REQUEST = BASE_URL + "peers/downloadedRequest/";

}
