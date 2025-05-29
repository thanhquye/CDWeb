package verify;

public class CodeData {
    private String code;
    private long timestamp;

    public CodeData(String code, long timestamp) {
        this.code = code;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public long getTimestamp() {
        return timestamp;
    }
}