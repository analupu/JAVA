public enum TipTranzactie {
    INTRARE(1),
    IESIRE(-1);

    private int semn;

    TipTranzactie (int semn) {
        this.semn = semn;
    }

    public int semn() {
        return semn;
    }

}
