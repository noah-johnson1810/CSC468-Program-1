package johnson_noah.river;

/*
 * JavaFX River Simulator
 *
 * Author: Noah Johnson
 * Class: CSC 468
 * Date: 2/16/2023
 * Professor: Dr. Lisa Rebenitsch
 * Description: this is a basic river simulator, with land areas surrounding the river which can be purchased with funds
 * (which start at 0). As time continues, the land types will cost and gain funds according to their type. On month 3 of
 * each year, the river "floods" and the land areas bordering the river will turn to flooded, resetting their type to
 * the default type.
 *
 * Checklist:
 * __X__ Followed the class OOP diagram
 * __X__ Observer pattern (ignores tiers)
 *
 *
 * 1.	Tier: Views and animal (<- ??? I don't know what you mean by "animal", but I implemented nothing of the sort.)
 * __X__ a. All objects (ignoring the sim area)
 * __X__ b. Have a starting number of tiles in sim area
 * __X__ c. Able to add/remove a land area properly
 * __X__ d. Info bar listed correctly with all the required elements
 * __X__ e. Tile Text correct in land area
 * __X__ f. Tile Text correct for each for all rectangles
 * __X__ g. Radio buttons update properly
 * __X__ h. Selecting a rectangle without “add” updates the land area info
 *
 * 2a Tier: Advanced functionality
 * __X__ a. Next month button has some noticeable effect
 * __X__ b. Land areas updated properly on “next”
 * __X__ c. Sim info bar updated properly
 * __X__ d. Selecting a tile after an update shows the new information
 *
 *
 * 2b: Layout
 * __X__ a. Location of all items in correct spot
 * __X__ b. Layout still correct on window resize
 * __X__ c. Resize grid at minimum resets the grid and info
 * __X__ d. Everything still working that is listed above with resize
 *
 * Final Tier: Extensions 30
 * Extension 1: 2a - 5pts, mark what tile is being shown : an outline indicates the currently selected tile
 * Extension 2: 2d - 10pts, use colors instead of just text to indicate a land area: the colors are clearly visible
 * Extension 3: 2g - 5pts, disable the land selection when "add" is not checked: uncheck "add" and radio buttons are disabled
 * Extension 4: 3a - 10pts, add hotkeys (scene key listener) to 3 buttons:
 *      - Hotkey 1: "a" selects "agriculture"
 *      - Hotkey 2: "r" selects "recreation"
 *      - Hotkey 3: "u" selects "unused"
 * Notice that the hotkeys also become disabled when "add" is not selected.
 * Extensions total: 30pts
 *
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override  
    public void start(Stage stage) {
        // set the scene to custom layout and show stage
        Layout layout = new Layout();
        stage.setScene(layout.createLayout());
        stage.show();
    }
}