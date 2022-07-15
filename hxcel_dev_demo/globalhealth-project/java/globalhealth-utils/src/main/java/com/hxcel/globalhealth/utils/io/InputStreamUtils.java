package com.hxcel.globalhealth.utils.io;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: Bjorn Harvold
 * Date: Feb 16, 2007
 * Time: 11:19:41 PM
 */
public class InputStreamUtils {
    public byte[] inputStreamToBytes(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);

        in.close();
        out.close();

        return out.toByteArray();
    }
}
