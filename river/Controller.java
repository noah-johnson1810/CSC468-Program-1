package johnson_noah.river;

/*
 * Author: Noah Johnson
 * Description: JavaFx Project, Controller which handles events and button clicks
 */

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class Controller {

    private RiverSim _riverSim;
    private Layout _layout;

    /*
     * Description: Constructor for Controller, injects the layout and riverSim
     * Parameters: layout - the layout of type Layout for the controller
     *             riverSim - the river simulator of time RiverSim for the controller
     */
    Controller(Layout layout, RiverSim riverSim) {
        // set layout and riverSim
        _layout = layout;
        _riverSim = riverSim;
    }

    /*
     * Description: handles the "add" checkbox from the layout. Toggles radio buttons enabled/disabled.
     */
    public EventHandler<MouseEvent> handleCheckBox() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // set the source
                CheckBox source = (CheckBox) mouseEvent.getSource();
                boolean b = source.isSelected();

                // toggle radio buttons disabled
                _layout.toggleRadioButtonsDisabled(!b);
            }
        };
    }

    /*
     * Description: handles the "Next Month" button from the layout. Calls nextMonth in riverSim, updates info bar.
     */
    public EventHandler<MouseEvent> handleNextMonthButton() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // call next month in the riverSim, then refresh info bar
                _riverSim.nextMonth();
                _layout.updateInfoBar();
            }
        };
    }

    /*
     * Description: handles the three resize buttons from the layout. Calls resize in the layout.
     */
    public EventHandler<MouseEvent> handleResizeButton() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // handle resize of 5x3
                if(((Node) mouseEvent.getSource()).getId().compareTo("resize1") == 0) {
                    _layout.resize(5, 3);
                }

                // handle resize of 7x5
                else if(((Node) mouseEvent.getSource()).getId().compareTo("resize2") == 0) {
                    _layout.resize(7, 5);
                }

                // handle resize of 9x7
                else if(((Node) mouseEvent.getSource()).getId().compareTo("resize3") == 0) {
                    _layout.resize(9, 7);
                }
                _layout.updateInfoBar();
            }
        };
    }

    /*
     * Description: handles any clicked tile from the river sim view. Calls appropriate methods in riverSim and updates
     * the layout info bar. Also sets the active tile in the layout.
     */
    public EventHandler<MouseEvent> handleTileClick() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                // get source
                TileView source = (TileView) mouseEvent.getSource();

                // if checkbox is selected, get the land type from radiobuttons
                if(_layout.checkBox.isSelected()) {
                    String selectedLandType =  ((RadioButton)(_layout.radioButtons.getSelectedToggle())).getText();
                    String landString;

                    // handle new agriculture logic
                    if(Objects.equals(selectedLandType, "Agriculture")) {
                        if(Objects.equals(source.getLandText(), "-U-"))
                            _riverSim.addToFilled(1);
                        landString = "-A-";
                        _riverSim.addToFunds(-300);
                        _layout.updateInfoBar();
                    }

                    // handle new recreation logic
                    else if (Objects.equals(selectedLandType, "Recreation")) {
                        if(Objects.equals(source.getLandText(), "-U-"))
                            _riverSim.addToFilled(1);
                        landString = "-R-";
                        _riverSim.addToFunds(-10);
                        _layout.updateInfoBar();
                    }

                    // otherwise, new unused
                    else {
                        landString = "-U-";
                    }

                    // replace tile
                    _riverSim.replaceTile(landString, source.getIndex());
                }

                // set the active tile
                _layout.setActiveTile(_riverSim.getTile(source.getIndex()));
            }
        };
    }
}
