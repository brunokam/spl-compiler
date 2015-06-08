package org.spl.binder;

import org.spl.common.ASTNode;
import org.spl.binder.exception.IncorrectTypeDeclarationException;
import org.spl.common.Nonterminal;
import org.spl.common.Symbol;
import org.spl.common.Token;
import org.spl.common.type.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class TypeFactory {

    private final static HashMap<Token, String> m_basicTypeMap = new HashMap<Token, String>() {{
        put(Token.TYPE_VOID, "Void");
        put(Token.TYPE_INT, "Int");
        put(Token.TYPE_BOOL, "Bool");
        put(Token.TYPE_CHAR, "Char");
    }};

    public static Type buildForDeclaration(ASTNode node) throws IncorrectTypeDeclarationException {
        Symbol nodeSymbol = node.getSymbol();
        Token nodeToken = node.getToken();

        ArrayList<Type> innerTypes = new ArrayList<Type>();
        for (Enumeration e = node.children(); e.hasMoreElements(); ) {
            innerTypes.add(buildForDeclaration((ASTNode) e.nextElement()));
        }

        Type type;
        if (nodeSymbol == Nonterminal.BasicType && node.getChildCount() == 0) {
            type = new BasicType(m_basicTypeMap.get(nodeToken));
        } else if (nodeSymbol == Nonterminal.PolymorphicType && node.getChildCount() == 0) {
            type = new PolymorphicType(node.getString());
        } else if (nodeSymbol == Nonterminal.ListType && node.getChildCount() == 1) {
            type = new ListType(innerTypes.get(0));
        } else if (nodeSymbol == Nonterminal.TupleType && node.getChildCount() == 2) {
            type = new TupleType(innerTypes.get(0), innerTypes.get(1));
        } else {
            throw new IncorrectTypeDeclarationException(node);
        }

        return type;
    }

    public static Type buildForLiteral(Token nodeToken) {
        Type type;
        if (nodeToken == Token.NUMERIC) {
            type = new BasicType(m_basicTypeMap.get(Token.TYPE_INT));
        } else if (nodeToken == Token.CHARACTER) {
            type = new BasicType(m_basicTypeMap.get(Token.TYPE_CHAR));
        } else if (nodeToken == Token.BOOL_TRUE || nodeToken == Token.BOOL_FALSE) {
            type = new BasicType(m_basicTypeMap.get(Token.TYPE_BOOL));
        } else if (nodeToken == Token.EMPTY_ARRAY) {
            type = new EmptyListType();
        } else {
            throw new RuntimeException();
        }

        return type;
    }
}
