package johnson_noah.river;

/*
 * Author: Noah Johnson
 * Description: creates the layout for the River Sim application. It uses a riverSimView, as well as VBoxes for the info
 * and sidebar. It's mostly just the big method "CreateLayout", but has a few helper functions such as calling resize
 * and setting the active tile in the land information sidebar.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;

import java.util.Objects;

public class Layout {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private RiverSimView rsv;
    private RiverSim riverSim;
    private VBox infoBar;
    private VBox upperSideBar;
    public ToggleGroup radioButtons;
    private RadioButton agricultureButton;
    private RadioButton recreationButton;
    private RadioButton unusedButton;
    public CheckBox checkBox;

    /*
     * Description: creates the layout for the river sim using Gridpane, VBox, HBox etc, it's resizable and responsive
     * to different window sizes. Its primary feature is housing the river sim view, but also includes a sidebar and info bar.
     */
    public Scene createLayout() {

        // define basic layout blocks
        VBox root = new VBox();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        HBox topHalf = new HBox();

        // initialize riverSim and upper side bar
        riverSim = new RiverSim();
        upperSideBar = new VBox();

        // initialize controller
        Controller controller = new Controller(this, riverSim);
        rsv = new RiverSimView(controller, riverSim);
        rsv.prefWidthProperty().bind(topHalf.widthProperty().multiply(0.75));

        // create sidebar
        VBox sideBar = new VBox();
        sideBar.setPadding(new Insets(20));
        sideBar.setSpacing(20);
        sideBar.prefWidthProperty().bind(topHalf.widthProperty().multiply(0.25));

        // create upper region of sidebar
        Text upperSideBarText = new Text("Unused\nLast changed: 0-1\nAge: 0-1\nTotal Cost: $0k\nTotal Revenue: $0k");
        upperSideBar.getChildren().add(upperSideBarText);
        upperSideBar.prefHeightProperty().bind(sideBar.heightProperty().multiply(0.5));
        upperSideBar.setAlignment(Pos.CENTER);

        // create lower region of sidebar
        VBox lowerSideBar = new VBox();
        lowerSideBar.setAlignment(Pos.CENTER);

        // create next month button and link to controller method
        Button nextMonthButton = new Button();
        nextMonthButton.setText("Next Month");
        nextMonthButton.setOnMouseClicked(controller.handleNextMonthButton());
        lowerSideBar.getChildren().add(nextMonthButton);

        // create radio buttons and their toggle group
        VBox radioButtonsBox = new VBox();
        radioButtons = new ToggleGroup();
        agricultureButton = new RadioButton("Agriculture");
        recreationButton = new RadioButton("Recreation");
        unusedButton = new RadioButton("Unused");

        // link radio buttons to the toggle group
        agricultureButton.setToggleGroup(radioButtons);
        recreationButton.setToggleGroup(radioButtons);
        unusedButton.setToggleGroup(radioButtons);
        toggleRadioButtonsDisabled(true);

        // create add checkbox
        checkBox = new CheckBox("Add");
        checkBox.setOnMouseClicked(controller.handleCheckBox());
        VBox checkBoxWrapper = new VBox();
        checkBoxWrapper.getChildren().add(checkBox);
        checkBoxWrapper.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(checkBoxWrapper, Priority.ALWAYS);

        // create spacing rules for the radio buttons area
        radioButtonsBox.getChildren().addAll(agricultureButton, recreationButton, unusedButton);
        radioButtonsBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(radioButtonsBox, Priority.ALWAYS);

        // create spacing rules for the different regions of the sidebar
        lowerSideBar.getChildren().addAll(radioButtonsBox, checkBoxWrapper);
        upperSideBar.prefHeightProperty().bind(root.heightProperty().multiply(0.5));
        lowerSideBar.prefHeightProperty().bind(root.heightProperty().multiply(0.5));

        // add children to the sidebar and regions
        sideBar.getChildren().addAll(upperSideBar, lowerSideBar);
        topHalf.getChildren().addAll(rsv, sideBar);
        topHalf.prefHeightProperty().bind(root.heightProperty().multiply(0.85));

        // create resize buttons area
        HBox resizeButtonsArea = new HBox();
        Label resize = new Label("Resize: ");

        // create resize button 1
        Button resize1 = new Button("5x3");
        resize1.setId("resize1");
        resize1.setOnMouseClicked(controller.handleResizeButton());

        // create resize button 2
        Button resize2 = new Button("7x5");
        resize2.setId("resize2");
        resize2.setOnMouseClicked(controller.handleResizeButton());

        // create resize button 3
        Button resize3 = new Button("9x7");
        resize3.setId("resize3");
        resize3.setOnMouseClicked(controller.handleResizeButton());

        // set spacing rules for the resize buttons area
        HBox resizeButtonsBox = new HBox();
        HBox.setHgrow(resizeButtonsBox, Priority.ALWAYS);
        resizeButtonsBox.getChildren().addAll(resize1, resize2, resize3);
        resizeButtonsBox.setAlignment(Pos.BASELINE_RIGHT);

        // create resize label box
        HBox resizeLabelBox = new HBox();
        HBox.setHgrow(resizeLabelBox, Priority.ALWAYS);
        resizeLabelBox.getChildren().add(resize);

        // add children to resize buttons area and attach to the lower side bar
        resizeButtonsArea.getChildren().addAll(resizeLabelBox, resizeButtonsBox);
        lowerSideBar.getChildren().add(resizeButtonsArea);

        // create bottom information bar
        infoBar = new VBox();
        infoBar.setPadding(new Insets(10));
        infoBar.setSpacing(10);
        infoBar.setAlignment(Pos.CENTER);
        infoBar.prefHeightProperty().bind(root.heightProperty().multiply(0.15));
        updateInfoBar();

        // add the top half and infobar to the root
        root.getChildren().addAll(topHalf, infoBar);

        // define hotkeys
        KeyCodeCombination A = new KeyCodeCombination(KeyCode.A);
        KeyCodeCombination U = new KeyCodeCombination(KeyCode.U);
        KeyCodeCombination R = new KeyCodeCombination(KeyCode.R);

        // add event filter to the scene to listen for hotkey presses
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // link "a" hotkey to agriculture button
            if(A.match(event)) {
                if(!agricultureButton.isDisabled())
                    agricultureButton.fire();
                event.consume();
            }
            // link "u" hotkey to unused button
            if(U.match(event)) {
                if(!unusedButton.isDisabled())
                    unusedButton.fire();
                event.consume();
            }
            // link "r" hotkey to recreation button
            if(R.match(event)) {
                if(!recreationButton.isDisabled())
                    recreationButton.fire();
                event.consume();
            }
;        });
        return scene;
    }

    /*
     * Description: gets the number of filled (non-unused) tiles
     * Returns: filled - the number of filled (non-unused) tiles
     */
    public int getFilled() {
        return riverSim.getFilled();
    }

    /*
     * Description: calls resize of the riverSimView
     * Params: cols - the new number of columns
     *         rows - the new number of rows
     */
    public void resize(int cols, int rows) {
        rsv.resize(cols, rows);
    }

    /*
     * Description: sets the active tile in the land info bar
     */
    public void setActiveTile(Tile tile) {

        // remove the outline from the currently highlighted tile and clear tile info area
        riverSim.removeHighlight();
        upperSideBar.getChildren().clear();

        String ageString = (tile.getLandArea().age / 13 + "-" + tile.getLandArea().age % 13);

        // highlight new tile
        tile.setHighlighted(true);
        Text sideBarText = new Text("");

        // handle sidebar text for flooded tile
        if(Objects.equals(tile.getLandArea().abbreviation, "-F-")) {
            sideBarText.setText("Flooded");
        }

        // handle sidebar text for recreation tile
        else if (Objects.equals(tile.getLandArea().abbreviation, "-R-")) {
            sideBarText.setText("Recreation\nLast changed: " + tile.getLandArea().lastChangedYear + "-" + tile.getLandArea().lastChangedMonth + "\nAge: " + ageString
                    + "\nTotal Cost: $" + tile.getTotalCost() + "k\nTotal Revenue: $" + tile.getTotalRevenue() + "k");
        }

        // handle sidebar text for agriculture tile
        else if (Objects.equals(tile.getLandArea().abbreviation, "-A-")) {
            sideBarText.setText("Agriculture\nLast changed: " + tile.getLandArea().lastChangedYear + "-" + tile.getLandArea().lastChangedMonth + "\nAge: " + ageString
                    + "\nTotal Cost: $" + tile.getTotalCost() + "k\nTotal Revenue: $" + tile.getTotalRevenue() + "k");
        }

        // handle sidebar text for unused tile
        else if (Objects.equals(tile.getLandArea().abbreviation, "-U-")) {
            sideBarText.setText("Unused\nLast changed: " + tile.getLandArea().lastChangedYear + "-" + tile.getLandArea().lastChangedMonth +
                    "\nAge: " + ageString + "\nTotal Cost: $" + tile.getTotalCost() + "k\nTotal Revenue: $" + tile.getTotalRevenue() + "k");
        }

        // mount to the sidebar
        upperSideBar.getChildren().add(sideBarText);
    }

    /*
     * Description: sets whether the radio buttons should be enabled or disabled.
     * Parameters: b - the boolean describing whether they should be enabled or disabled
     */
    public void toggleRadioButtonsDisabled(boolean b) {
        // enable / disable radio buttons based on boolean passed in
        agricultureButton.setDisable(b);
        recreationButton.setDisable(b);
        unusedButton.setDisable(b);
    }

    /*
     * Description: refreshes the labels on the info bar to reflect the latest information from the river sim
     */
    public void updateInfoBar() {
        // create three labels with refreshed information from the river sim
        Label dateLabel = new Label("Year: " + riverSim.getCurrentYear() + " Month: " + riverSim.getCurrentMonth());
        Label filledLabel = new Label("Filled: " + riverSim.getFilled());
        Label fundsLabel = new Label("Funds: $" + riverSim.getFunds() + "k");

        // clear and mount the new labels to the infobar
        infoBar.getChildren().clear();
        infoBar.getChildren().addAll(dateLabel, filledLabel, fundsLabel);
    }
}