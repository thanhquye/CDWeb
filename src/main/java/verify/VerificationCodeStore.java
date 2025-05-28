package verify;

import java.util.HashMap;
import java.util.Map;

public class VerificationCodeStore {
    private static final long CODE_EXPIRY = 10 * 60 * 1000; // 10 phút

    private static Map<String, CodeData> codeMap = new HashMap<>();

    public static void store(String email, String code) {
        codeMap.put(email, new CodeData(code, System.currentTimeMillis()));
    }

    public static boolean verify(String email, String code) {
        CodeData data = codeMap.get(email);
        if (data == null) return false;

        long now = System.currentTimeMillis();
        if (now - data.getTimestamp() > CODE_EXPIRY) {
            codeMap.remove(email); // Xoá nếu hết hạn
            return false;
        }

        return data.getCode().equals(code);
    }

    public static void remove(String email) {
        codeMap.remove(email);
    }
}