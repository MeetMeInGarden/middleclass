package com.middleclass.middleclass.stock.common;

public enum TrId {
    FHKST01010100("FHKST01010100"),     // 국내주식시세 - 주식현재가 시세[v1_국내주식-008]
    FHPST01710000("FHPST01710000"),     // 국내주식시세 - 거래량순위[v1_국내주식-047]
    CTCA0903R("CTCA0903R")              // 국내휴장일조회[국내주식-040]
    ;

    private final String value;

    TrId(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}