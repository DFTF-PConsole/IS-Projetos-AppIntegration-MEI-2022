package uc.mei.is;

public enum IdEstaTamanhos {
    XML_SIZE(0),
    XML_GZIP_SIZE(1),
    PROTOBUF_SIZE(2);

    public final int indice;

    private IdEstaTamanhos(int indice) {
        this.indice = indice;
    }
}
