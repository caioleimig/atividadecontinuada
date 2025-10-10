package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ {

    private static final int TAMANHO_CPF = 11;
    private static final int TAMANHO_CNPJ = 14;
 
    public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj) {
        String apenasDigitos = removerCaracteresNaoNumericos(cpfCnpj);
        if (apenasDigitos.length() == TAMANHO_CPF) {
            return validarCPF(apenasDigitos);
        } else if (apenasDigitos.length() == TAMANHO_CNPJ) {
            return validarCNPJ(apenasDigitos);
        } else {
            return new ResultadoValidacaoCPFCNPJ(false, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ);
        }
    }
    private static ResultadoValidacaoCPFCNPJ validarCPF(String cpf) {
        if (todosDigitosIguais(cpf)) {
            return new ResultadoValidacaoCPFCNPJ(true, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO);
        }
        int dv1 = calcularDigito(cpf.substring(0, 9), 10);
        // Calcula o segundo dígito verificador
        int dv2 = calcularDigito(cpf.substring(0, 10), 11);
        if (dv1 == Character.getNumericValue(cpf.charAt(9)) && dv2 == Character.getNumericValue(cpf.charAt(10))) {
            return new ResultadoValidacaoCPFCNPJ(true, false, null);
        } else {
            return new ResultadoValidacaoCPFCNPJ(true, false, ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO);
        }
    }
    private static ResultadoValidacaoCPFCNPJ validarCNPJ(String cnpj) {
        if (todosDigitosIguais(cnpj)) {
            return new ResultadoValidacaoCPFCNPJ(false, true, ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO);
        }
        int[] pesosDV1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosDV2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int dv1 = calcularDigito(cnpj.substring(0, 12), pesosDV1);
        int dv2 = calcularDigito(cnpj.substring(0, 13), pesosDV2);
        if (dv1 == Character.getNumericValue(cnpj.charAt(12)) && dv2 == Character.getNumericValue(cnpj.charAt(13))) {
            return new ResultadoValidacaoCPFCNPJ(false, true, null);
        } else {
            return new ResultadoValidacaoCPFCNPJ(false, true, ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO);
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private static String removerCaracteresNaoNumericos(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replaceAll("[^0-9]", "");
    }

    private static boolean todosDigitosIguais(String texto) {
        char primeiro = texto.charAt(0);
        for (int i = 1; i < texto.length(); i++) {
            if (texto.charAt(i) != primeiro) {
                return false;
            }
        }
        return true;
    }

    private static int calcularDigito(String trecho, int pesoMax) {
        int soma = 0;
        for (int i = 0; i < trecho.length(); i++) {
            soma += Character.getNumericValue(trecho.charAt(i)) * (pesoMax - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
    
    private static int calcularDigito(String trecho, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < trecho.length(); i++) {
            soma += Character.getNumericValue(trecho.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}