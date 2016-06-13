package com.pixvoxsoftware.packetsinspector;

import java.util.regex.Pattern;

/**
 * Created by PixelIndigo.
 */
public class Utils {
    static Pattern IPV4_PATTERN, IPV6_STD_PATTERN, IPV6_HEX_COMPRESSED_PATTERN;
    static {
        IPV4_PATTERN = Pattern.compile(
                "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

        IPV6_STD_PATTERN = Pattern.compile(
                "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
        IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile(
                "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
    }

    static boolean isValidAddress(String address) {
        return IPV4_PATTERN.matcher(address).matches() ||
                IPV6_STD_PATTERN.matcher(address).matches() ||
                IPV6_HEX_COMPRESSED_PATTERN.matcher(address).matches();
    }
}
