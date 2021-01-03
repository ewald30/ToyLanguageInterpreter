package GUI.WrapperClasses;

public class ReferenceWrapper {
    String address;
    String value;

    public ReferenceWrapper(String address, String value) {
        this.address = address;
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
