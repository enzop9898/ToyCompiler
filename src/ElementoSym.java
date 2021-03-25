public class ElementoSym {

    private String lessemaString;
    private String type;

    public ElementoSym(String lessemaString, String type) {
        this.lessemaString = lessemaString;
        this.type = type;
    }

    public ElementoSym(String lessemaString) {
        this.lessemaString = lessemaString;
    }

    public String getLessemaString() {
        return lessemaString;
    }

    public void setLessemaString(String lessemaString) {
        this.lessemaString = lessemaString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type==null? lessemaString : "("+lessemaString+", \""+type+"\")";
    }
}
