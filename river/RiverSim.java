package johnson_noah.river;

/*
 * Author: Noah Johnson
 * Description: JavaFx project, RiverSim class. This is the heart of the logic for the river simulator.
 */

import java.util.ArrayList;
import java.util.Objects;

public class RiverSim {
    private int currentMonth = 1;
    private int currentYear = 0;
    private int funds;
    private int filled = 0;
    private ArrayList<Tile> tiles = new ArrayList<>();


    /*
     * Description: adds (or subtracts if a negative integer is passed in) to the current funds balance
     * Parameters: amount - the amount to be added to the current funds
     */
    public void addToFunds(int amount) {
        funds += amount;
    }

    /*
     * Description: adds a new tile to the tiles arrayList
     * Parameters: tile - the new tile to be added to the arrayList
     */
    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    /*
     * Description: clears the tiles currently in the arrayList
     */
    public void clearTiles() {
        tiles.clear();
    }
    /*
     * Description: getter for the private variable currentMonth
     * Returns - the current month
     */
    public int getCurrentMonth() {
        return currentMonth;
    }

    /*
     * Description: getter for the private member variable currentYear
     * Returns - the current year
     */
    public int getCurrentYear() {
        return currentYear;
    }

    /*
     * Description: getter for the private variable filled
     */
    public int getFilled() {
        return filled;
    }

    /*
     * Description: mutation for private variable filled
     */
    public void addToFilled(int value) {
        filled += value;
    }

    /*
     * Description: getter for the private variable funds
     * Returns - funds
     */
    public int getFunds() {
        return funds;
    }

    /*
     * Description: getter for the private arrayList of tiles
     * Parameters: index - the index of the tile to return
     * Returns: the tile at the specified index
     */
    public Tile getTile(int index) {
        return tiles.get(index);
    }

    /*
     * Description: the logic for the next month button. Adds appropriate funds and invokes the flood when appropriate
     */
    public void nextMonth() {

        // increment current month and loop if a year is finishing
        currentMonth++;
        if (currentMonth % 13 == 0) {
            currentMonth = 0;
            currentYear++;
        }

        // reset current revenue and cost for each tile
        for (Tile tile : tiles) {
            tile.getLandArea().age++;
            tile.setCurrentRevenue(0);
            tile.setCurrentCost(0);
        }

        // handle flood in month 3
        if (currentMonth == 3) {
            int rows;
            int cols;

            // set rows and columns for 5x3 grid
            if(tiles.size() == 12) {
                rows = 3;
                cols = 4;
            }

            // set rows and columns for 7x5 grid
            else if (tiles.size() == 30) {
                rows = 5;
                cols = 6;
            }

            // set rows and columns for 9x7 grid
            else {
                rows = 7;
                cols = 8;
            }

            // set flooded tiles next to the river
            for (int i = 0; i < rows; i++) {

                // decrement filled for flooded tiles that were filled
                if(!Objects.equals(tiles.get((i * cols) + (cols / 2) - 1).getLandArea().abbreviation, "-U-")) {
                    addToFilled(-1);
                }
                tiles.get((i * cols) + (cols / 2) - 1).setLandType("-F-");
                tiles.get((i * cols) + (cols / 2) - 1).getLandArea().lastChangedMonth = currentMonth;
                tiles.get((i * cols) + (cols / 2) - 1).getLandArea().lastChangedYear = currentYear;
                tiles.get((i * cols) + (cols / 2) - 1).getLandArea().age = 0;

                if(!Objects.equals(tiles.get((i * cols) + (cols / 2)).getLandArea().abbreviation, "-U-")) {
                    addToFilled(-1);
                }
                tiles.get((i * cols) + (cols / 2)).setLandType("-F-");
                tiles.get((i * cols) + (cols / 2)).getLandArea().lastChangedMonth = currentMonth;
                tiles.get((i * cols) + (cols / 2)).getLandArea().lastChangedYear = currentYear;
                tiles.get((i * cols) + (cols / 2)).getLandArea().age = 0;
            }
        }

        // handle flood tiles becoming unused tiles in month 4
        if (currentMonth == 4) {
            for (Tile tile : tiles) {
                if (Objects.equals(tile.getLandArea().abbreviation, "-F-")) {
                    tile.setLandType("-U-");
                }
            }
        }

        // handle agriculture costs in month 5
        if (currentMonth == 5) {
            for (Tile tile : tiles) {
                if (tile.getLandArea() instanceof Agriculture) {
                    addToFunds(-50);
                    tile.setCurrentCost(50);
                }
            }
        }

        // handle agriculture revenue in month 10
        if (currentMonth == 10) {
            for (Tile tile : tiles) {
                if (tile.getLandArea() instanceof Agriculture && tile.getLandArea().age > 3) {
                    addToFunds(65);
                    tile.setCurrentRevenue(65);
                }
            }
        }

        // handle recreation revenue in month 12
        if (currentMonth == 12) {
            for (Tile tile : tiles) {
                if (tile.getLandArea() instanceof Recreation) {
                    addToFunds(5);
                    tile.setCurrentRevenue(5);
                }
            }
        }
    }

    /*
     * Description: removes the outline currently "highlighted" (outlined) tile
     */
    public void removeHighlight() {
        // remove the outline from every tile
        for(Tile t: tiles) {
            t.setHighlighted(false);
        }
    }

    /*
     * Description: replaces a tile at a specified index
     * Parameters: landType - the landArea for the new tile to replace
     *             index - the index of the tile to replace
     */
    public void replaceTile(String landType, int index) {
        // locate tile to replace and set it's new land type
        Tile tileToReplace = tiles.get(index);
        tileToReplace.setLandType(landType);

        // set cost based on land type
        if (Objects.equals(landType, "-A-")) {
            tileToReplace.setCurrentCost(300);
        } else if (Objects.equals(landType, "-R-")) {
            tileToReplace.setCurrentCost(10);
        } else {
            tileToReplace.setCurrentCost(0);
        }

        // set last changed month and year to now
        tileToReplace.getLandArea().lastChangedMonth = currentMonth;
        tileToReplace.getLandArea().lastChangedYear = currentYear;
    }

    /*
     * Description: resets filled tiles to 0 (for use upon resize)
     */
    public void resetFilled() {
        filled = 0;
    }
}
