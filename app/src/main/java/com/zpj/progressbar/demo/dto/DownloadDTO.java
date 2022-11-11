package com.zpj.progressbar.demo.dto;

import java.io.Serializable;
import java.util.Objects;


public class DownloadDTO implements Serializable {
    private String machineCode;
    private String filePath;

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
