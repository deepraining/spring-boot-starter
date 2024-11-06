package dr.sbs.misc.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import java.util.Base64;

public class SbsEncryptUtil {
  private static final String publicKey =
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAMHchuMFFaj2fZfoq6CMaqT7j9Idy8XLOghyDP5J0ryYg72q5l4wDPJ0O0tPY3jMBskMgJ/z2KX/yrCvYQnN5p13NenPAuIw2vXF8FNLntVkN7XnM+JuHeDN491KCR8DghlNBTf7L3LmjMnTPHFIjKS8ugRGFIw0+7hToaG7BwwIDAQAB";
  private static final String privateKey =
      "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIAwdyG4wUVqPZ9l+iroIxqpPuP0h3Lxcs6CHIM/knSvJiDvarmXjAM8nQ7S09jeMwGyQyAn/PYpf/KsK9hCc3mnXc16c8C4jDa9cXwU0ue1WQ3tecz4m4d4M3j3UoJHwOCGU0FN/svcuaMydM8cUiMpLy6BEYUjDT7uFOhobsHDAgMBAAECgYA/esCj/GAJRfE6LD/UW5x2qXUxYXhp3VNjHF+ORz75dXrGOIl7WqbFO5DzNEggTvinUGWZWPQV2rs3qzcTIuUXl9hzt8LBwLguUEbur+XQcwIfTQ+wN3PuX07F5ClK/XMaaOApFQwQ291fBqj2uCFX5J7nzIQuIq33X52moxqOwQJBALuteTf42aRXls2WuFEHzMGw0BRUkKvMHsvURvL9CGg3Vs0XbcONmuZI1WkI3nZ+4avXdLvdNuK2TUexE5P/yJMCQQCu2wHT1ojWEFDMzks+exHr2/gfvGyhFdnTeNJ8HPDh/2MaiWHhM7pfqnl6inS/splU5uw2GUqt22Z50G68edARAkAad1oy1HsMu/VeRLT1aF4tqluNomdMctqc7/CZb6lx2Ov6vCUUgLGpa/f8ee21VCtK6nVh0M5epkq7o3MZWebVAkEAhUK+Os+HC/X1TpFugRjUiLfle3JkK8R2dZfgetynrOWxhvjrEvIUeXJ4EK9S2QJpn2462eQeqS/MAsyaiKQhsQJAMrg3d+Yv/8yntc98ffoak0VGC/reHKD4dHvk7YTGNlWNgI50zeSlJiaU/Ap9448dWUYOaVYx7lsob+6ld1r7Sw==";
  private static final RSA rsa = SecureUtil.rsa(privateKey, publicKey);

  public static String encodeStr(String originStr) {
    byte[] encodedBytes = rsa.encrypt(originStr.getBytes(), KeyType.PublicKey);
    return Base64.getEncoder().encodeToString(encodedBytes);
  }

  public static String decodeStr(String encodedStr) {
    byte[] decodedBytes = rsa.decrypt(Base64.getDecoder().decode(encodedStr), KeyType.PrivateKey);
    return new String(decodedBytes);
  }
}
