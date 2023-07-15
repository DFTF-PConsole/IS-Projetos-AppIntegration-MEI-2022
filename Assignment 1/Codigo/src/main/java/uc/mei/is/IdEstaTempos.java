package uc.mei.is;

public enum IdEstaTempos {
    GENERATOR(0),
    XML_MARSHAL(1),
    XML_UNMARSHAL(2),
    XML_GZIP_MARSHAL(3),
    XML_GZIP_UNMARSHAL(4),
    PROTOBUF_MARSHAL(5),
    PROTOBUF_MAR_INI(6),
    PROTOBUF_UNMARSHAL(7),
    PROTOBUF_UNMAR_INI(8),;

    public final int indice;

    private IdEstaTempos(int indice) {
        this.indice = indice;
    }
}
