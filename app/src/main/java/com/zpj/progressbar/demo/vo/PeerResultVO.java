package com.zpj.progressbar.demo.vo;

import java.io.Serializable;
import java.util.List;

public class PeerResultVO implements Serializable {

    private String code;

    private String status;

    private String message;

    private String error;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

    private Object errorData;

    private PageList data;

    public PageList getData() {
        return data;
    }

    public void setData(PageList data) {
        this.data = data;
    }

    public static class PageList {
        private List<TransferFileVO> list;

        public List<TransferFileVO> getList() {
            return list;
        }

        public void setList(List<TransferFileVO> list) {
            this.list = list;
        }
    }


    public static class TransferFileVO {
        private String fileId;
        private String fileName;
        private String fileHash;
        private Long fileSize;
        private String fileExtension;
        private String filePath;
        private int nodeType;
        private String fromMachineCodes;
        private String dataNodeUrls;

        public String getFromMachineCodes() {
            return fromMachineCodes;
        }

        public void setFromMachineCodes(String fromMachineCodes) {
            this.fromMachineCodes = fromMachineCodes;
        }

        public String getDataNodeUrls() {
            return dataNodeUrls;
        }

        public void setDataNodeUrls(String dataNodeUrls) {
            this.dataNodeUrls = dataNodeUrls;
        }

        public Integer getFileVersion() {
            return fileVersion;
        }

        public void setFileVersion(Integer fileVersion) {
            this.fileVersion = fileVersion;
        }

        private Integer fileVersion;

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public void setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileHash() {
            return fileHash;
        }

        public void setFileHash(String fileHash) {
            this.fileHash = fileHash;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public int getNodeType() {
            return nodeType;
        }

        public void setNodeType(int nodeType) {
            this.nodeType = nodeType;
        }
    }
}
