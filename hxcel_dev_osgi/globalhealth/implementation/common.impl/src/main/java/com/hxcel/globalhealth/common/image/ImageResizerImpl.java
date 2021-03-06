package com.hxcel.globalhealth.common.image;

import org.jdesktop.swingx.graphics.GraphicsUtilities;

import java.io.*;
import java.awt.image.BufferedImage;

import com.hxcel.globalhealth.common.spec.image.ImageResizer;

import javax.imageio.ImageIO;

/**
 * User: bjorn
 * Date: Oct 4, 2008
 * Time: 5:18:29 PM
 */
public class ImageResizerImpl implements ImageResizer {

    /**
     * Resizes an image quickly
     *
     * @param image
     * @param desiredWidth
     * @return resized input stream
     * @throws IOException
     */
    public InputStream resize(InputStream image, int desiredWidth, String extension) throws IOException {
        InputStream result;

        BufferedImage bi = ImageIO.read(image);
        int currentWidth = bi.getWidth();

        if (currentWidth > desiredWidth) {
            // time to resize
            BufferedImage resized = GraphicsUtilities.createThumbnailFast(bi, desiredWidth);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(resized, extension.toUpperCase(), bos);

            result = new BufferedInputStream(new ByteArrayInputStream(bos.toByteArray()));
        } else {
            // no resize needed
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bi, extension.toUpperCase(), bos);

            result = new BufferedInputStream(new ByteArrayInputStream(bos.toByteArray()));
        }

        return result;
    }
}
