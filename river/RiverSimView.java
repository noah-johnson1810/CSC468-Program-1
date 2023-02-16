package johnson_noah.river;

/*
 * Author: Noah Johnson
 * Description: JavaFx project, RiverSimView class
 */

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

public class RiverSimView extends GridPane {

    private RiverSim _riverSim;
    private Controller _controller;
    private int rows;
    private int cols;
    private ArrayList<TileView> tileViews = new ArrayList<>();

    /*
     * Description: Constructor for riverSimView, resizes to default rows and columns and sets the riverSim and controller
     * Parameters: controller - the controller for this riverSimView
     *             riverSim - the riverSim for this riverSimView
     */
    RiverSimView(Controller controller, RiverSim riverSim) {
        // set default column and row values for clarity
        final int defaultCols = 5;
        final int defaultRows = 3;

        // set the injected riverSim and controller
        _riverSim = riverSim;
        _controller = controller;

        // call resize for the defaults and set the background to blue for the river
        this.resize(defaultCols, defaultRows);
        this.setStyle("-fx-background-color: blue;");
    }

    /*
     * Description: resizes the riverSimView to the specified rows and columns
     * Parameters: col - the new number of columns
     *             row - the new number of rows
     */
    public void resize(int col, int row) {
        // clear constraints and children
        this.getRowConstraints().clear();
        this.getColumnConstraints().clear();
        this.getChildren().clear();
        _riverSim.resetFilled();

        // set rows and columns to passed in values
        cols = col;
        rows = row;

        // set the new column constraints
        for (int i = 0; i < cols; i++) {
            ColumnConstraints newColumn = new ColumnConstraints();
            if (i > 0 && cols / i == 2) {
                newColumn.setPercentWidth(50);
            } else {
                newColumn.setPercentWidth(100);
            }
            this.getColumnConstraints().add(newColumn);
        }

        // set the new row constraints
        for (int i = 0; i < rows; i++) {
            RowConstraints newRow = new RowConstraints();
            newRow.setPercentHeight(100);
            this.getRowConstraints().add(newRow);
        }

        // add the tiles
        this.addTiles();
    }

    /*
     * Description: adds the tiles into the riverSimView
     */
    public void addTiles() {
        int index = 0;
        tileViews = new ArrayList<>();
        _riverSim.clearTiles();

        // insert the tiles into the proper location
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // skip river tiles
                if(j > 0 && cols / j == 2)
                    continue;
                TileView tileView = new TileView(index);
                Tile tile = new Tile(tileView);
                _riverSim.addTile(tile);

                // bind to controller method
                tileView.setOnMouseClicked(_controller.handleTileClick());
                tileViews.add(tileView);
                GridPane.setRowIndex(tileView, i);
                GridPane.setColumnIndex(tileView, j);
                this.getChildren().add(tileView);
                index++;
            }
        }
    }
}
