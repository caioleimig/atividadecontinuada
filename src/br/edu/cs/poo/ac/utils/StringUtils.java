package br.edu.cs.poo.ac.utils;

public class StringUtils {
    public static boolean estaVazia(String str) {
        if (str == null) {
            return true;
        }
        return str.trim().isEmpty();
    }
    public static boolean tamanhoExcedido(String str, int tamanho) {
        if (tamanho < 0) {
            return false;
        }
        int comprimento = (str == null) ? 0 : str.length();
        
        return comprimento > tamanho;
    }
    public static boolean emailValido(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }

    public static boolean telefoneValido(String tel) {
        if (tel == null) {
            return false;
        }
        String regex = "^\\(\\d{2}\\)\\d{8,9}$";
        return tel.matches(regex);
    }
}