package johnson_noah.river;

/*
 * Author: Noah Johnson
 * Description: JavaFx project, Tile class
 */

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class Tile {

    private LandArea landArea;
    private final PropertyChangeSupport support;
    private int totalCost = 0;
    private int totalRevenue = 0;
    private boolean highlighted;

    /*
     * Description: constructor for the Tile class, adds property change listeners for the observer pattern, as well as
     * sets the default values for land type, current cost, current revenue, etc
     * Parameters: tileView - the corresponding tileView that is observing this Tile
     */
    Tile(TileView tileView) {

        // initialize observer pattern
        support = new PropertyChangeSupport(this);
        addPropertyChangeListener(tileView);

        // set default tile values
        setLandType("-U-");
        setCurrentCost(0);
        setCurrentRevenue(0);
        landArea.lastChangedMonth = 0;
        landArea.lastChangedYear = 0;
        landArea.age = 0;
        landArea.abbreviation = "-U-";
    }

    /*
     * Description: adds the property change listener for the observer pattern
     * Parameters: pcl - the property change listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl); // GRADING: SUBJECT
    }

    /*
     * Description: getter for the private variable totalRevenue
     * Returns: totalRevenue
     */
    public int getTotalRevenue() {
        return totalRevenue;
    }

    /*
     * Description: getter for the private variable totalCost
     * Returns: totalCost
     */
    public int getTotalCost() {
        return totalCost;
    }

    /*
     * Description: sets the current cost for this tile
     * Parameters: newCost - the new current cost to set
     */
    public void setCurrentCost(int newCost) {
        // fire property change for observer pattern
        support.firePropertyChange("cost", landArea.currentCost, newCost); // GRADING: TRIGGER

        // set costs
        this.landArea.currentCost = newCost;
        totalCost += newCost;
    }

    /*
     * Description: sets the current revenue for this tile
     * Parameters: newRevenue - the new revenue for this tile
     */
    public void setCurrentRevenue(int newRevenue) {
        // fire property change for observer pattern
        support.firePropertyChange("revenue", landArea.currentRevenue, newRevenue); // GRADING: TRIGGER

        // set revenue
        this.landArea.currentRevenue = newRevenue;
        totalRevenue += newRevenue;
    }

    /*
     * Description: sets this as highlighted (with an outline) if b is true
     * Parameters: b - whether this tile should be highlighted
     */
    public void setHighlighted(boolean b) {
        // fire property change for observer pattern
        support.firePropertyChange("highlighted", highlighted, b); // GRADING: TRIGGER
        highlighted = b;
    }

    /*
     * Description: sets the land type for this tile
     * Parameters: newLandType - the new land type to be changed to
     */
    public void setLandType(String newLandType) {
        // create new landArea based on the passed in land type
        if (Objects.equals(newLandType, "-A-")) {
            landArea = new Agriculture();
        } else if (Objects.equals(newLandType, "-R-")) {
            landArea = new Recreation();
        } else if (Objects.equals(newLandType, "-U-")) {
            landArea = new Unused();
        } else {
            landArea = new Flooded();
        }

        // fire property change for observer pattern
        support.firePropertyChange("land", landArea.abbreviation, newLandType); // GRADING: TRIGGER
        landArea.abbreviation = newLandType;
    }

    /*
     * Description: setter for the private variable landArea
     */
    public void setLandArea(LandArea newLandArea) {
        landArea = newLandArea;
    }

    /*
     * Description: getter for the private variable landArea
     */
    public LandArea getLandArea() {
        return landArea;
    }
}
