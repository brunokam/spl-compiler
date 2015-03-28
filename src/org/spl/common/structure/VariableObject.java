package org.spl.common.structure;

import org.spl.common.Token;
import org.spl.common.type.Type;

public class VariableObject extends BindingObject {

    public VariableObject(Token typeToken, String typeString, String identifier) {
        super(typeToken, typeString, identifier);
    }

    public VariableObject(Type type, String identifier) {
        super(type, identifier);
    }
}
