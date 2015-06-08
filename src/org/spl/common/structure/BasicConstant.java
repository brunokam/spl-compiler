package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.Token;
import org.spl.common.type.Type;

public class BasicConstant extends Expression {

    private ASTNode m_valueNode;

    public BasicConstant(ASTNode valueNode) {
        super();
        m_valueNode = valueNode;
    }

    public Type getType() {
        return m_valueNode.getType();
    }

    @Override
    public Integer getSize() {
        return 1;
    }

    @Override
    public void generateCode(Context context) {
        Token nodeToken = m_valueNode.getToken();

        if (nodeToken == Token.NUMERIC) {
            context.addInstruction(new String[]{LOAD_CONSTANT, m_valueNode.getString()});
        } else if (nodeToken == Token.BOOL_TRUE) {
            context.addInstruction(new String[]{LOAD_CONSTANT, "-1"});
        } else if (nodeToken == Token.BOOL_FALSE) {
            context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
        } else if (nodeToken == Token.CHARACTER) {
            String parameter = m_valueNode.getString();
            context.addInstruction(new String[]{LOAD_CONSTANT, Integer.toString((int) parameter.charAt(1))});
        }
    }

    @Override
    public String toString() {
        return "CONSTANT " + m_valueNode;
    }
}
