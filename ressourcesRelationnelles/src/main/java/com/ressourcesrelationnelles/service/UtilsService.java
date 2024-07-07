package com.ressourcesrelationnelles.service;

import org.springframework.stereotype.Service;

@Service
public class UtilsService {

    // remplace certaines lettres pour éviter les failles XSS
    public String escapeHtml(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '<' || c == '>' ) {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }


}
