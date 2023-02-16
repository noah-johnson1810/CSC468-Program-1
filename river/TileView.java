package johnson_noah.river;

/*
 * Author: Noah Johnson
 * Description: JavaFx project, TileView class
 */

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class TileView extends VBox implements PropertyChangeListener {

    private int index;
    private Label landLabel = new Label("-U-");
    private Label costLabel = new Label("-$0k");
    private Label revenueLabel = new Label("+$0k");

    /*
     * Description: constructor for TileView class, sets defaults for alignment, styles, etc as well as the index
     * Parameters: index - the index of this TileView
     */
    TileView(int index) {
        // set alignment, style, index, and children
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(landLabel, costLabel, revenueLabel);
        this.setIndex(index);
        this.setStyle("-fx-background-color: linear-gradient(#ffe7ae, #fddd87)");
    }

    /*
     * Description: getter for the private variable index
     * Returns: index
     */
    public int getIndex() {
        return index;
    }

    /*
     * Description: getter for the text within the private label landLabel
     * Returns: the text within the private label landLabel
     */
    public String getLandText() {
        return landLabel.getText();
    }

    /*
     * Description: required observer pattern method, reacts to property changes, built into java
     * Parameters: event - the event that triggered the property change
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) { // GRADING: OBSERVE

        // handle land type property change
        if (Objects.equals(event.getPropertyName(), "land")) {

            // update label text to reflect land type
            landLabel.setText((String) event.getNewValue());

            // update background color to reflect land type
            if(Objects.equals(landLabel.getText(), "-F-"))
                this.setStyle("-fx-background-color: linear-gradient(#98c5ff, #6288e8)");
            else if(Objects.equals(landLabel.getText(), "-R-"))
                this.setStyle("-fx-background-color: linear-gradient(#ff98cc, #e175a9)");
            else if(Objects.equals(landLabel.getText(), "-A-"))
                this.setStyle("-fx-background-color: linear-gradient(#8fff8f, #49e749)");
            else if(Objects.equals(landLabel.getText(), "-U-"))
                this.setStyle("-fx-background-color: linear-gradient(#ffe7ae, #fddd87)");
        }

        // handle cost property change
        if (Objects.equals(event.getPropertyName(), "cost")) {
            costLabel.setText("-$" + event.getNewValue() + "k");
        }

        // handle revenue property change
        if (Objects.equals(event.getPropertyName(), "revenue")) {
            revenueLabel.setText("+$" + event.getNewValue() + "k");
        }

        // handle highlight property change
        if(Objects.equals(event.getPropertyName(), "highlighted")) {
            boolean b = (boolean) event.getNewValue();

            // apply outline
            if(b) {
                this.setStyle(this.getStyle() + "; -fx-border-color: #413b34; -fx-border-width: 2; -fx-border-style: solid");
            }

            // remove outline
            else {
                this.setStyle(this.getStyle() + "; -fx-border-width: 0");
            }
        }
    }

    /*
     * Description: setter for the private variable index
     * Parameter: value - the value to set the index to
     */
    public void setIndex(int value) {
        index = value;
    }
}
