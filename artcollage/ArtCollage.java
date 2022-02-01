/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage Flo2.jpeg
 *
 *  @author:
 *
 *************************************************************************/

import java.awt.Color;

public class ArtCollage {

    // The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
    
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) {
        collageDimension = 4;
        tileDimension = 100;

        int ct = collageDimension * tileDimension;

        original = new Picture(filename);
        collage = new Picture(ct, ct);

        for(int i = 0; i < ct; i++){
            for(int j = 0; j < ct; j++){
                int sCol = i * original.width() / ct;
                int sRow = j * original.height() / ct;
                Color c = original.get(sCol, sRow);
                collage.set(i, j, c);
            }
        }
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension, 
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) {
        collageDimension = cd;
        tileDimension = td;

        int ct = collageDimension * tileDimension;

        original = new Picture(filename);
        collage = new Picture(ct, ct);

        for(int i = 0; i < ct; i++){
            for(int j = 0; j < ct; j++){
                int sCol = i * original.width() / ct;
                int sRow = j * original.height() / ct;
                Color c = original.get(sCol, sRow);
                collage.set(i, j, c);
            }
        }
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {
        return collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {
        return tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() {
        return original;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() {
        return collage;
    }
    
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {
        original.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {
        collage.show();
    }
    
    /*
     * Makes a collage of tiles from original Picture
     * collage will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () {
        for(int index = 0; index < collageDimension; index++){
            for(int index2 = 0; index2 < collageDimension; index2++){
                for(int i = 0; i < tileDimension; i++){
                    for(int j = 0; j < tileDimension; j++){
                        int sCol = i * original.width() / tileDimension;
                        int sRow = j * original.height() / tileDimension;
                        Color c = original.get(sCol, sRow);

                        collage.set(i + (tileDimension * index), j + (tileDimension * index2), c);
                        collage.set(i + (tileDimension * index2), j + (tileDimension * index), c);
                    }
                }
            }
        }
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {
        Picture replace = new Picture(filename);

        for(int i = 0; i < tileDimension; i++){
            for(int j = 0; j < tileDimension; j++){
                int sCol = i * replace.width() / tileDimension;
                int sRow = j * replace.height() / tileDimension;
                Color c = replace.get(sCol, sRow);

                collage.set(i + (tileDimension * collageCol), j + (tileDimension * collageRow), c);
            }
        }
    }

    // /*
    //  * Colorizes the tile at (collageCol, collageRow) with component 
    //  * (see CS111 Week 9 slides, the code for color separation is at the 
    //  *  book's website)
    //  *
    //  * @param component is either red, blue or green
    //  * @param collageCol tile column
    //  * @param collageRow tile row
    //  */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
        for(int i = 0; i < tileDimension; i++){
            for(int j = 0; j < tileDimension; j++){
                Color c = collage.get(i + (tileDimension * collageCol), j + (tileDimension * collageRow));

                if(component.equalsIgnoreCase("red")){
                    int color = c.getRed();
                    Color change = new Color(color, 0, 0);
                    collage.set(i + (tileDimension * collageCol), j + (tileDimension * collageRow), change);
                }

                if(component.equalsIgnoreCase("blue")){
                    int color = c.getBlue();
                    Color change = new Color(0, 0, color);
                    collage.set(i + (tileDimension * collageCol), j + (tileDimension * collageRow), change);
                }

                if(component.equalsIgnoreCase("green")){
                    int color = c.getGreen();
                    Color change = new Color(0, color, 0);
                    collage.set(i + (tileDimension * collageCol), j + (tileDimension * collageRow), change);
                }
            }
        }
    }

    /*
     * Grayscale tile at (collageCol, collageRow)
     * (see CS111 Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void grayscaleTile (int collageCol, int collageRow) {
        for(int i = 0; i < tileDimension; i++){
            for(int j = 0; j < tileDimension; j++){
                Color c = collage.get(i + (tileDimension * collageCol), j + (tileDimension * collageRow));

                Color gray = Luminance.toGray(c);

                collage.set(i + (tileDimension * collageCol), j + (tileDimension * collageRow), gray);
            }
        }
    }


    /*
     *
     *  Test client: use the examples given on the assignment description to test your ArtCollage
     */
    public static void main (String[] args) {
        ArtCollage art = new ArtCollage(args[0], 200, 3);
        art.makeCollage();

        // art.replaceTile(args[1], 1, 1);
        // art.replaceTile(args[2], 1, 2);
        // art.replaceTile(args[3], 2, 2);

        art.colorizeTile("red", 2, 1);
        art.colorizeTile("blue", 1, 0);
        art.colorizeTile("green", 0, 1);
        art.grayscaleTile(0, 0);
        art.showCollagePicture();
    }
}