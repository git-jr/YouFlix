package com.paradoxo.youflix.util;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Cronos {

    public String tempo_depois(DateTime dataDoVideo) {
        String at = "atrás";
        String retorno;

        int minuto = 1;
        int hora = 60;
        int dia = 1440;
        int semana = 10080;
        int mes = 43800;
        int ano = 525600;

        Duration duration = new Duration(dataDoVideo, DateTime.now()); // Calcula a diferença em minutos entre a data passada e a data de hoje
        int min_at = (int) duration.getStandardMinutes();


        // Para evitar situações do tipo "1 horaS" atrás

        if (min_at < minuto * 2) {
            retorno = String.valueOf(min_at / minuto) + " minuto " + at;
            return retorno;

        } else if (min_at < hora * 2 && min_at > hora) {
            retorno = String.valueOf(min_at / hora) + " hora " + at;
            return retorno;

        } else if (min_at < dia * 2 && min_at > dia) {
            retorno = String.valueOf(min_at / dia) + " dia " + at;
            return retorno;

        } else if (min_at < semana * 2 && min_at > semana) {
            retorno = String.valueOf(min_at / semana) + " semana " + at;
            return retorno;

        } else if (min_at < mes * 2 && min_at > mes) {
            retorno = String.valueOf(min_at / mes) + " mês " + at;
            return retorno;

        } else if (min_at < ano * 2 && min_at > ano) {
            retorno = String.valueOf(min_at / ano) + " ano " + at;
            return retorno;

        } else { // Se não vamos checar na forma "plural da coisa"

            if (min_at > minuto && min_at < hora) {
                retorno = String.valueOf(min_at / minuto) + " minutos " + at;
                return retorno;

            } else if (min_at > hora && min_at < dia) {
                retorno = String.valueOf(min_at / hora) + " horas " + at;
                return retorno;

            } else if (min_at > dia && min_at < semana) {
                retorno = String.valueOf(min_at / dia) + " dias " + at;
                return retorno;

            } else if (min_at > semana && min_at < mes) {
                retorno = String.valueOf(min_at / semana) + " semanas " + at;
                return retorno;

            } else if (min_at > mes && min_at < ano) {
                retorno = String.valueOf(min_at / mes) + " meses " + at;
                return retorno;

            } else {
                retorno = String.valueOf(min_at / ano) + " anos " + at;
                return retorno;
            }
        }
    }

    public String converterTimeStamp(String timeStamp) {
        // No Firebase a data está sendo gravada em algo parecido com isso: 1552860052915
        // Fazendo o procedimento abaixo ela fica assim: 17/03/2019  19:00

        // Não esquecer de adicionar o "L" ao fim
        long dataTimestamp = Long.parseLong(timeStamp);
        DateTime dateTime = new DateTime(dataTimestamp);

        return dateTime.toString("dd/MM/yyyy HH:mm");


    }
}
