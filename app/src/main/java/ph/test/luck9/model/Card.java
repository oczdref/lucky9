package ph.test.luck9.model;

public class Card {

    private String type;
    private String cardDisplay;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardDisplay() {
        return cardDisplay;
    }

    public void setCardDisplay(String cardDisplay) {
        this.cardDisplay = cardDisplay;
    }

    @Override
    public String toString() {
        return "card [" + cardDisplay +
                " of "+ type +
                "]";
    }
}
