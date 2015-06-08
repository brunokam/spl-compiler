package org.spl.common.structure;

import java.util.ArrayList;

public class Context {

    private Integer m_statementCounter;
    private Integer m_variableCounter;
    private ArrayList<String[]> m_instructions;
    private ArrayList<VariableDeclaration> m_functionArguments;
    private ArrayList<VariableDeclaration> m_globalVariables;
    private ArrayList<VariableDeclaration> m_localVariables;

    public Context() {
        m_statementCounter = 0;
        m_variableCounter = 0;
        m_instructions = new ArrayList<String[]>();
        m_functionArguments = new ArrayList<VariableDeclaration>();
        m_globalVariables = new ArrayList<VariableDeclaration>();
        m_localVariables = new ArrayList<VariableDeclaration>();
    }

    public Context(Context context) {
        m_statementCounter = context.m_statementCounter;
    }

    public Integer getAddressPosition(VariableDeclaration variableDeclaration) {
        Integer position = 0;

        if (containsArgument(variableDeclaration)) {
            for (VariableDeclaration var : m_functionArguments) {
                if (var.equals(variableDeclaration)) {
                    break;
                }
                position += var.getType().getAddressSize();
            }

            return -position - 2;
        } else if (containsLocal(variableDeclaration)) {
            for (VariableDeclaration var : m_localVariables) {
                if (var.equals(variableDeclaration)) {
                    break;
                }
                position += var.getType().getAddressSize();
            }

            return position + 1;
        } else {
            for (VariableDeclaration var : m_globalVariables) {
                if (var.equals(variableDeclaration)) {
                    break;
                }
                position += var.getType().getAddressSize();
            }

            return position + 1;
        }
    }

    public ArrayList<String[]> getInstructions() {
        return m_instructions;
    }

    public Integer getStatementCounter() {
        return m_statementCounter++;
    }

    public Integer getArgumentsEnvironmentLength() {
        Integer length = 0;

        for (VariableDeclaration variableDeclaration : m_functionArguments) {
            length += variableDeclaration.getType().getBodySize();
        }

        return length;
    }

    public ArrayList<VariableDeclaration> getArguments() {
        return m_functionArguments;
    }

    public ArrayList<VariableDeclaration> getLocalVariables() {
        return m_localVariables;
    }

    public ArrayList<VariableDeclaration> getGlobalVariables() {
        return m_globalVariables;
    }

    public Integer getLocalEnvironmentLength() {
        Integer length = 0;

        for (VariableDeclaration variableDeclaration : m_localVariables) {
            length += variableDeclaration.getType().getAddressSize();
        }

        return length;
    }

    public boolean containsArgument(VariableDeclaration variableDeclaration) {
        return m_functionArguments.contains(variableDeclaration);
    }

    public boolean containsLocal(VariableDeclaration variableDeclaration) {
        return m_localVariables.contains(variableDeclaration);
    }

    public boolean containsGlobal(VariableDeclaration variableDeclaration) {
        return m_globalVariables.contains(variableDeclaration);
    }

    public void addArgument(VariableDeclaration variableDeclaration) {
        m_functionArguments.add(variableDeclaration);
    }

    public void addLocal(VariableDeclaration variableDeclaration) {
        m_localVariables.add(variableDeclaration);
    }

    public void addGlobal(VariableDeclaration variableDeclaration) {
        m_globalVariables.add(variableDeclaration);
    }

    public void clearLocal() {
        m_functionArguments.clear();
        m_localVariables.clear();
    }

    public void addInstruction(String[] instruction) {
        m_instructions.add(instruction);
    }
}
