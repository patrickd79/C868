package C868.Entities;

public class Type {
    private String typeID;
    private String typeName;
    private String length;
    private String instrument;

    public Type(String typeID, String typeName, String length, String instrument) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.length = length;
        this.instrument = instrument;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
