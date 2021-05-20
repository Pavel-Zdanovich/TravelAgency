package com.zdanovich.web.search;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

import java.math.BigDecimal;

public class BigDecimalNumericFieldBridge implements TwoWayFieldBridge {

    private static final BigDecimal storeFactor = BigDecimal.valueOf(0.0001);

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
        if (value != null) {
            BigDecimal decimalValue = (BigDecimal) value;
            Long indexedValue = decimalValue.multiply(storeFactor).longValue();
            luceneOptions.addNumericFieldToDocument(name, indexedValue, document);
        }
    }

    @Override
    public Object get(String name, Document document) {
        String fromLucene = document.get(name);
        BigDecimal storedBigDecimal = new BigDecimal(fromLucene);
        return storedBigDecimal.divide(storeFactor);
    }

    @Override
    public String objectToString(Object object) {
        return object.toString();
    }
}
