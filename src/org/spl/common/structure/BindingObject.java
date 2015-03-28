package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.Token;
import org.spl.common.type.Type;

public abstract class BindingObject {

    protected Token m_typeToken;
    protected String m_typeString;
    protected Type m_type;
    protected String m_identifier;
    protected ASTNode m_node;

    public BindingObject() {
        m_typeToken = null;
        m_typeString = null;
        m_type = null;
        m_identifier = null;
        m_node = null;
    }

    public BindingObject(Token typeToken, String typeString, String identifier) {
        m_typeToken = typeToken;
        m_typeString = typeString;
        m_type = null;
        m_identifier = identifier;
        m_node = null;
    }

    public BindingObject(Type type, String identifier) {
        m_type = type;
        m_identifier = identifier;
    }

    public Token getTypeToken() {
        return m_typeToken;
    }

    public String getTypeString() {
        return m_typeString;
    }

    public Type getType() {
        return m_type;
    }

    public String getIdentifier() {
        return m_identifier;
    }

    public ASTNode getNode() {
        return m_node;
    }

    public void setNode(ASTNode object) {
        m_node = object;
    }

    @Override
    public String toString() {
        return "{type=\"" + m_type +
                "\", identifier=\"" + m_identifier + "\"}";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof BindingObject)) {
            return false;
        }

        BindingObject bindingObject = (BindingObject) other;
        if (m_identifier == null || bindingObject.getIdentifier() == null) {
//            System.out.println(this.getClass().toString() + " " + bindingObject.getIdentifier());
            return false;
        } else {
            return m_identifier.equals(bindingObject.getIdentifier());
        }
    }
}
