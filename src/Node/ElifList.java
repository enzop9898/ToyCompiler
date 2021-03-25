package Node;

public class ElifList extends Node {

    private Elif e;
    private ElifList elList;


    public ElifList(String nomeNode, Elif e, ElifList elList) {
        super(nomeNode);
        this.e = e;
        this.elList = elList;
    }

    public ElifList(String nomeNode) {
        super(nomeNode);
    }
}
