package org.spl.common.structure;

import org.spl.codegenerator.CodeGeneratorMaps;
import org.spl.common.ASTNode;
import org.spl.common.Token;
import org.spl.common.type.BasicType;
import org.spl.common.type.Type;

import java.util.ArrayList;

public class Expression extends StructureObject {

    protected ASTNode m_node;
    protected Token m_token;
    protected Token m_parentToken;
    protected ArrayList<Expression> m_expressions;
    protected Integer m_forcedAdjustment;

    public Expression() {
        super();
        m_node = null;
        m_token = null;
        m_expressions = new ArrayList<Expression>();
        m_forcedAdjustment = null;
    }

    public Expression(ASTNode node) {
        super();
        m_node = node;
        m_token = null;
        m_expressions = new ArrayList<Expression>();
        m_forcedAdjustment = null;
    }

    public Token getToken() {
        return m_token;
    }

    public Token getParentToken() {
        return m_parentToken;
    }

    public void setToken(Token token) {
        m_token = token;
    }

    public void setParentToken(Token parentToken) {
        m_parentToken = parentToken;
    }

    public void addExpression(Expression expression) {
        m_expressions.add(expression);
    }

    public boolean isReference() {
        if (m_token == Token.GETTER_FIRST ||
                m_token == Token.GETTER_SECOND ||
                m_token == Token.GETTER_HEAD ||
                m_token == Token.GETTER_TAIL) {
            return true;
        }

        return false;
    }

    @Override
    public Type getType() {
        if (m_node.getType() != null && !m_node.getType().containsPolymorphicTypes()) {
            return m_node.getType();
        } else {
            return m_node.getTempType();
        }
    }

    public Integer getSize() {
        if (m_forcedAdjustment != null) {
            return m_forcedAdjustment;
        }

        return m_node.getType().getBodySize();
    }

    public void generateHeader(Context context) {
        Type type = getType();

        context.addInstruction(new String[]{LOAD_CONSTANT, "1"});

        if (type.isBasicType()) {
            if (type.unify(new BasicType("Int"))) {
                context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
            } else if (type.unify(new BasicType("Bool"))) {
                context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
            } else if (type.unify(new BasicType("Char"))) {
                context.addInstruction(new String[]{LOAD_CONSTANT, "2"});
            }
        } else if (type.isTupleType()) {
            context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
        } else if (type.isListType()) {
            context.addInstruction(new String[]{LOAD_CONSTANT, "4"});
        }
    }

    @Override
    public void generate(Context context) {
        if (CodeGeneratorMaps.binaryOperatorTokenMap.containsKey(m_token) && m_expressions.size() == 2) {
            Expression leftExpression = m_expressions.get(0);
            Expression rightExpression = m_expressions.get(1);

            leftExpression.generateValue(context);
            rightExpression.generateValue(context);

            context.addInstruction(new String[]{CodeGeneratorMaps.binaryOperatorTokenMap.get(m_token)});
        } else if (CodeGeneratorMaps.unaryOperatorTokenMap.containsKey(m_token) && m_expressions.size() == 1) {
            if (m_token == Token.OPERATOR_SUBTRACT) {
                context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
            }

            Expression expression = m_expressions.get(0);

            expression.generateValue(context);

            context.addInstruction(new String[]{CodeGeneratorMaps.unaryOperatorTokenMap.get(m_token)});
        } else if (m_token == Token.OPERATOR_CONCATENATE && m_expressions.size() == 2) {
            Expression leftExpression = m_expressions.get(0);
            Expression rightExpression = m_expressions.get(1);

            Integer leftExpressionSize = leftExpression.getSize() + 2;

            rightExpression.generateValue(context);

            leftExpression.generateHeader(context);
            leftExpression.generateValue(context);

            if (leftExpression.isReference()) {
                context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
                context.addInstruction(new String[]{ADJUST, "-1"});

                if (leftExpression.getSize() == 2) {
                    context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "add_reference"});
                    context.addInstruction(new String[]{ADJUST, "-1"});
                }

                leftExpression.generateValue(context);
            }

            if (leftExpressionSize == 3) {
                // Adds empty space in the block
                context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
            }

            // Stores the value on the heap
            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});

            context.addInstruction(new String[]{LOAD_CONSTANT, "1"});
            context.addInstruction(new String[]{LOAD_CONSTANT, "5"});
            context.addInstruction(new String[]{LOAD_FROM_STACK, "-2"});
            context.addInstruction(new String[]{LOAD_FROM_STACK, "-5"});

            // Stores the value on the heap
            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});

            context.addInstruction(new String[]{STORE_ON_STACK, "-3"});
            context.addInstruction(new String[]{ADJUST, "-1"});
        } else if (m_token == Token.TUPLE_TYPE) {
            Expression leftExpression = m_expressions.get(0);
            Expression rightExpression = m_expressions.get(1);

            Integer leftExpressionSize = leftExpression.getSize() + 2;
            Integer rightExpressionSize = rightExpression.getSize() + 2;

            leftExpression.generateHeader(context);
            leftExpression.generateValue(context);

            if (leftExpressionSize == 3) {
                // Adds empty space in the block
                context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
            }

            // Stores the value on the heap
            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});

            rightExpression.generateHeader(context);
            rightExpression.generateValue(context);

            if (rightExpressionSize == 3) {
                // Adds empty space in the block
                context.addInstruction(new String[]{LOAD_CONSTANT, "0"});
            }

            // Stores the value on the heap
            context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "initialise_variable"});
        } else if (m_token == Token.GETTER_FIRST) {
            Expression expression = m_expressions.get(0);
//            Type expressionType = expression.getType();
//            Integer secondSize = ((TupleType) expressionType).getInnerTypeRight().getBodySize();
//            Integer adjust = -secondSize;

            expression.generateValue(context);

            context.addInstruction(new String[]{ADJUST, "-1"});
        } else if (m_token == Token.GETTER_SECOND) {
            Expression expression = m_expressions.get(0);
//            Type expressionType = expression.getType();
//            Integer expressionSize = expressionType.getBodySize();
//            Integer secondSize = ((TupleType) expressionType).getInnerTypeRight().getBodySize();

//            Integer store = -(expressionSize - 1);
//            Integer adjust = secondSize - 1;

            expression.generateValue(context);

            context.addInstruction(new String[]{ADJUST, "-2"});
            context.addInstruction(new String[]{LOAD_FROM_STACK, "2"});

//            context.addInstruction(new String[]{STORE_MULTIPLE_ON_STACK, store.toString(), secondSize.toString()});
//            context.addInstruction(new String[]{ADJUST, adjust.toString()});
        } else if (m_token == Token.GETTER_HEAD) {
            Expression expression = m_expressions.get(0);

            expression.generateValue(context);

            context.addInstruction(new String[]{ADJUST, "-1"});
            context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});
        } else if (m_token == Token.GETTER_TAIL) {
            Expression expression = m_expressions.get(0);

            context.addInstruction(new String[]{LOAD_CONSTANT, "1"}); // List size
            context.addInstruction(new String[]{LOAD_CONSTANT, "6"}); // Type for temporary lists

            expression.generateValue(context);

            context.addInstruction(new String[]{LOAD_FROM_STACK, "-1"});
            context.addInstruction(new String[]{LOAD_FROM_HEAP, "3"});
            context.addInstruction(new String[]{STORE_ON_STACK, "-2"});
            context.addInstruction(new String[]{STORE_MULTIPLE_ON_HEAP, "4"});

            context.addInstruction(new String[]{LOAD_CONSTANT, "3"});
            context.addInstruction(new String[]{SUBTRACT});
        } else if (m_token == null) {
            for (Expression expression : m_expressions) {
                expression.generateValue(context);
            }
        } else {
            System.out.println(m_expressions);
            throw new RuntimeException();
        }
    }

    public void generateValue(Context context) {
        Type type = getType();

        generate(context);

        if (isReference()) {
            if (type.isBasicType()) {
                context.addInstruction(new String[]{LOAD_FROM_HEAP, "2"});
            } else if (type.isTupleType()) {
                context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});
            } else if (type.isListType()) {
                context.addInstruction(new String[]{LOAD_MULTIPLE_FROM_HEAP, "-3", "2"});
            } else if (type.isPolymorphicType()) {
                context.addInstruction(new String[]{BRANCH_TO_SUBROUTINE, "get_value"});
            }
        }
    }

    @Override
    public String toString() {
        return "EXPRESSION";
    }
}
