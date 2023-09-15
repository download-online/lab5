package ru.jefremov.prog.handlers;

public class QuotationHandler extends Handler<String>{

    public QuotationHandler() {
        super(null);
    }

    public QuotationHandler(Handler<String> next) {
        super(next);
    }

    @Override
    protected String apply(String material) {
        if (material==null) return null;
        String s = material.trim();
        if ((s.startsWith("\"") && s.endsWith("\""))||(s.startsWith("'") && s.endsWith("'"))) {
            String quotationMark = String.valueOf(s.charAt(0));
            while (s.startsWith(quotationMark) && s.endsWith(quotationMark)) {
                s = s.substring(1,s.length()-1);
                if (!s.isBlank()) {
                    s = s.trim();
                }
                if (s.isEmpty()) return null;
            }
        }
        return s;
    }
}
