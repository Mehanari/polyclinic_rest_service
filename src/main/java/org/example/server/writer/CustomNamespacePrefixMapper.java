package org.example.server.writer;

import org.example.Constants;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if (Constants.MEDICAL_CARD_NS.equals(namespaceUri)) {
            return "tns";
        }
        if (Constants.APPOINTMENT_NS.equals(namespaceUri)) {
            return "app";
        }
        if (Constants.APPOINTMENT_RESULT_NS.equals(namespaceUri)) {
            return "res";
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{Constants.MEDICAL_CARD_NS, Constants.APPOINTMENT_NS, Constants.APPOINTMENT_RESULT_NS};
    }
}
