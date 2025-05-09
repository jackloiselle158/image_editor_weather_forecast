import java.awt.*;
import java.awt.image.BufferedImage;

class ImageOperations {

    /**
     * Removes the red channel from the image
     *
     * @param img image to edit
     * @return New image with red removed
     */
    static BufferedImage zeroRed(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < newImg.getHeight(); i++) {
            for (int j = 0; j < newImg.getWidth(); j++) {
                // Make new color with red as 0, and replace the color
                Color c = ColorOperations.getColorAtPos(img, j, i);
                int g = ColorOperations.getGreen(c.getRGB());
                int b = ColorOperations.getBlue(c.getRGB());
                Color newC = new Color(0, g, b);
                newImg.setRGB(j, i, newC.getRGB());
            }
        }
        return newImg;
    }

    /**
     * Converts the image to grayscale
     *
     * @param img image
     * @return new image with grayscale effect applied
     */
    static BufferedImage grayscale(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < newImg.getHeight(); i++) {
            for (int j = 0; j < newImg.getWidth(); j++) {
                // Get the average of the three RGB values and set each new RGB value to it
                Color c = ColorOperations.getColorAtPos(img, j, i);
                int avg = (int) ((c.getRed() + c.getGreen() + c.getBlue()) / 3);
                newImg.setRGB(j, i, new Color(avg, avg, avg).getRGB());
            }
        }
        return newImg;
    }

    /**
     * Inverts the pixel data
     * @param img image
     * @return Picture with colors inverted
     */
    static BufferedImage invert(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < newImg.getHeight(); i++) {
            for (int j = 0; j < newImg.getWidth(); j++) {
                // Get opposite of the three RGB values and replace the color
                Color c = ColorOperations.getColorAtPos(img, j, i);
                int r = 255 - ColorOperations.getRed(c.getRGB());
                int g = 255 - ColorOperations.getGreen(c.getRGB());
                int b = 255 - ColorOperations.getBlue(c.getRGB());
                newImg.setRGB(j, i, new Color(r, g ,b).getRGB());
            }
        }
        return newImg;
    }

    /**
     * Mirrors the image either vertically or horizontally
     *
     * @param img image
     * @param dir direction
     * @return Image mirrored either vertiacally or horizontally
     */
    static BufferedImage mirror(BufferedImage img, MirrorMenuItem.MirrorDirection dir) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (dir == MirrorMenuItem.MirrorDirection.VERTICAL) {
            for (int i = 0; i < newImg.getHeight(); i++) {
                for (int j = 0; j < newImg.getWidth(); j++) {
                    // Get the pixels from the top half of the image and copy them
                    // Copy them in reverse for the second half
                    if (j < newImg.getWidth() / 2){
                        Color c = ColorOperations.getColorAtPos(img, j, i);
                        newImg.setRGB(j, i, c.getRGB());
                    }
                    else{
                        Color c = ColorOperations.getColorAtPos(img, newImg.getWidth() - j, i);
                        newImg.setRGB(j, i, c.getRGB());
                    }
                }
            }
        } else {
            for (int i = 0; i < newImg.getHeight(); i++) {
                for (int j = 0; j < newImg.getWidth(); j++) {
                    // Get the pixels from the top half of the image and copy them
                    // Copy them in reverse for the second half
                    if (i < newImg.getHeight() / 2){
                        Color c = ColorOperations.getColorAtPos(img, j, i);
                        newImg.setRGB(j, i, c.getRGB());
                    }
                    else{
                        Color c = ColorOperations.getColorAtPos(img, j, newImg.getHeight() - i);
                        newImg.setRGB(j, i, c.getRGB());
                    }
                }
            }
        }
        return newImg;
    }

    /**
     * Rotates an image 90 degrees clockwise or counterclockwise
     *
     * @param img image to rotate
     * @param dir Direction to rotate, either clockwise or counterclockwise
     * @return new image rotated the desired direction
     */
    static BufferedImage rotate(BufferedImage img, RotateMenuItem.RotateDirection dir) {
        BufferedImage newImg = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
        if (dir == RotateMenuItem.RotateDirection.CLOCKWISE) {
            for (int i = 0; i < newImg.getHeight(); i++) {
                for (int j = 0; j < newImg.getWidth(); j++) {
                    // Get pixels starting from the bottom of the first column going up
                    Color c = ColorOperations.getColorAtPos(img, i, img.getHeight() - 1 - j);
                    newImg.setRGB(j, i, c.getRGB());
                }
            }
        } else {
            for (int i = 0; i < newImg.getHeight(); i++) {
                for (int j = 0; j < newImg.getWidth(); j++) {
                    // Get pixels starting from the far right of the first row going down
                    Color c = ColorOperations.getColorAtPos(img, img.getWidth() - 1 - i, j);
                    newImg.setRGB(j, i, c.getRGB());
                }
            }
        }
        return newImg;
    }

    /**
     * Creates an image with the current image repeated on the given side, a given number of times
     *
     * @param img image to repeat
     * @param n   number of times to repeat
     * @param dir direction to repeat (horizontal or vertical)
     * @return new image with image repeated n times in dir direction
     */
    static BufferedImage repeat(BufferedImage img, int n, RepeatMenuItem.RepeatDirection dir) {
        BufferedImage newImg = null;
        // newImg must be instantiated in both branches with the correct dimensions.
        if (n==0){
            return img;
        }
        if (dir == RepeatMenuItem.RepeatDirection.HORIZONTAL) {
            newImg = new BufferedImage(img.getWidth(), img.getHeight() * (n + 1), BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    for (int k = 0; k < n + 1; k++) {
                        // Copy each pixel one at a time for the number of repeats the user specifies
                        Color c = ColorOperations.getColorAtPos(img, j, i);
                        newImg.setRGB(j, i + (img.getHeight() * k), c.getRGB());
                    }

                }
            }
        } else {
            newImg = new BufferedImage(img.getWidth() * (n + 1), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    for (int k = 0; k < n + 1; k++) {
                        // Copy each pixel one at a time for the number of repeats the user specifies
                        Color c = ColorOperations.getColorAtPos(img, j, i);
                        newImg.setRGB(j + (img.getWidth() * k), i, c.getRGB());
                    }
                }
            }
        }
        return newImg;
    }

    /**
     * Zooms in on the image. The zoom factor increases in multiplicatives of 10% and
     * decreases in multiplicatives of 10%.
     *
     * @param img        the original image to zoom in on. The image cannot be already zoomed in
     *                   or out because then the image will be distorted.
     * @param zoomFactor The factor to zoom in by.
     * @return the zoomed in image.
     */
    static BufferedImage zoom(BufferedImage img, double zoomFactor) {
        int newImageWidth = (int) (img.getWidth() * zoomFactor);
        int newImageHeight = (int) (img.getHeight() * zoomFactor);
        BufferedImage newImg = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, newImageWidth, newImageHeight, null);
        g2d.dispose();
        return newImg;
    }
}
