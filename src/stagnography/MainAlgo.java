/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stagnography;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static stagnography.mainDocController.textToEmbed;

/**
 *
 * @author Ajit Singh
 */
public class MainAlgo {

    /**
     * **********************************Encoding
     * Portion*********************************************
     */
    static void embedImage(BufferedImage img, String mess) throws IOException { //getting image from mainDocController.

        int messageLength = mess.length(),
                imageWidth = img.getWidth(),
                imageHeight = img.getHeight(),
                imageSize = imageWidth * imageHeight;
        if (messageLength * 8 + 32 > imageSize) {
            mainDocController mdc = new mainDocController();
            mdc.callPopup("Message is too long, please split your message and do it on more than two images");
            //System.out.println("Message is too long, please split your message and do it on more than two images");

        }
        embedInteger(img, messageLength, 0, 0);
        byte b[] = mess.getBytes();
        for (int i = 0; i < b.length; i++) {
            embedByte(img, b[i], i * 8 + 32, 0);
        }
        mainDocController.getEmbededImage(img);//returning embeded image
    }

    static private void embedInteger(BufferedImage img, int n, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start / maxY, startY = start - startX * maxY, count = 0;
        for (int i = startX; i < maxX && count < 32; i++) {
            for (int j = startY; j < maxY && count < 32; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(n, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }

    static private void embedByte(BufferedImage img, byte b, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start / maxY, startY = start - startX * maxY, count = 0;
        for (int i = startX; i < maxX && count < 8; i++) {
            for (int j = startY; j < maxY && count < 8; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(b, count);
                rgb = setBitValue(rgb, storageBit, bit);
                img.setRGB(i, j, rgb);
                count++;
            }
        }
    }

    static private int getBitValue(int n, int location) {
        int v = n & (int) Math.round(Math.pow(2, location));
        return v == 0 ? 0 : 1;
    }

    static private int setBitValue(int n, int location, int bit) {
        int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
        if (bv == bit) {
            return n;
        }
        if (bv == 0 && bit == 1) {
            n |= toggle;
        } else if (bv == 1 && bit == 0) {
            n ^= toggle;
        }
        return n;
    }

    /**
     * *************************DecodingPortion********************************
     */
    static void decodeImage(BufferedImage image) {
        System.out.println(image.getHeight());
        int len = extractInteger(image, 0, 0);
        if (len < 1073414001) {
            System.out.println("len=" + len);
            byte b[] = new byte[len];
            for (int i = 0; i < len; i++) {
                b[i] = extractByte(image, i * 8 + 32, 0);
            }
            mainDocController.decodedText(new String(b));
        }
    }

    static private int extractInteger(BufferedImage img, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start / maxY, startY = start - startX * maxY, count = 0;
        int length = 0;
        for (int i = startX; i < maxX && count < 32; i++) {
            for (int j = startY; j < maxY && count < 32; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
                length = setBitValue(length, count, bit);
                count++;
            }
        }
        return length;
    }

    static private byte extractByte(BufferedImage img, int start, int storageBit) {
        int maxX = img.getWidth(), maxY = img.getHeight(),
                startX = start / maxY, startY = start - startX * maxY, count = 0;
        byte b = 0;
        for (int i = startX; i < maxX && count < 8; i++) {
            for (int j = startY; j < maxY && count < 8; j++) {
                int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
                b = (byte) setBitValue(b, count, bit);
                count++;
            }
        }
        return b;
    }

}
