package br.jus.framework.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe de utilidades para manipulação de datas.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public final class DateUtil {

    /** Data com fomato para exibir hora, minuto e segundo. */
    public static final String FORMAT_HOURS_MINUTES_SECONDS = "HH:mm:ss";

    /** Data com fomato para exibir hora e minuto. */
    public static final String FORMAT_HOURS_MINUTES = "HH:mm";

    /** Data com fomato para exibir dia e mês. */
    public static final String FORMAT_DAY_MONTH = "dd/MM";

    /** Data com fomato para exibir dia, mês e ano. */
    public static final String FORMAT_DAY_MONTH_YEAR = "dd/MM/yyyy";

    /** Data com fomato para exibir mês e ano. */
    public static final String FORMAT_MONTH_YEAR = "MM/yyyy";

    /** Data com fomato para exibir dia, mês, ano, hora, minuto e segundo. */
    public static final String FORMAT_DAY_MONTH_YEAR_HOURS_MINUTES_SECONDS = "dd/MM/yyyy HH:mm:ss";

    /** Data com fomato para exibir dia, mês, ano, hora e minuto. */
    public static final String FORMAT_DAY_MONTH_YEAR_HOURS_MINUTES = "dd/MM/yyyy HH:mm";

    /**
     * Construtor privado.
     */
    private DateUtil() {
    }

    /**
     * Formata uma data em um determinado formato.
     * 
     * @param date
     *            A data a ser formatada.
     * @param format
     *            O formato da data.
     * @return A data formatada do tipo String.
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Retorna a data atual do sistema.
     * 
     * @return A data atual.
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * Método que retorna o ano atual.
     * 
     * @return O ano atual.
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Método que retorna o mês atual.
     * 
     * @return O mês atual.
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * Método que retorna o dia atual.
     * 
     * @return O dia atual.
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Retorna o número de dias entre duas datas, Caso a data maior data
     * seja menor que a data menor, retorna a diferença em valores negativos.
     * 
     * @param firstDate
     *            A primeira data.
     * @param secondDate
     *            A segunda data.
     * @return O número de dias da diferença.
     */
    public static int getDaysDiff(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return 0;
        }
        return new Integer(new Long((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000)).intValue());
    }

    /**
     * Compara duas datas, retornando a diferença em meses entre elas (data
     * maior - data menor). Considera somente os valores de ano e mês para
     * o cálculo (exemplo: diferença entre 31/12/2005 e 01/01/2006
     * é um mês). Caso a data maior data seja menor que a data
     * menor, retorna a diferença em valores negativos.
     * 
     * @param firstDate
     *            Data a ser subtraída da dataMaior
     * @param secondDate
     *            Data maior
     * @return null caso algum parâmetro seja nulo ou Integer com a
     *         diferença das datas
     */
    public static Integer getMonthDiff(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(secondDate);
        int anoMaior = cal.get(Calendar.YEAR);
        int mesMaior = cal.get(Calendar.MONTH) + 1;

        cal = Calendar.getInstance();
        cal.setTime(firstDate);
        int anoMenor = cal.get(Calendar.YEAR);
        int mesMenor = cal.get(Calendar.MONTH) + 1;

        int mesesDiff = 0;

        if (anoMaior != anoMenor) {
            int anosEntreMaiorEMenor = anoMaior - anoMenor;
            // subtrai um (ou soma, caso ano menor > ano maior) por não
            // considerar o maior ano (nem o menor).
            // Neles, é feita a contagem de meses por não ser o ano inteiro.

            if (anoMaior > anoMenor) {
                anosEntreMaiorEMenor = anosEntreMaiorEMenor - 1;
            } else {
                anosEntreMaiorEMenor = anosEntreMaiorEMenor + 1;
            }

            mesesDiff = anosEntreMaiorEMenor * 12;

            if (anoMaior > anoMenor) {
                mesesDiff = mesesDiff + 12 - mesMenor;
            } else {
                mesesDiff = mesesDiff - (12 - mesMaior);
            }

            if (anoMaior > anoMenor) {
                mesesDiff = mesesDiff + mesMaior;
            } else {
                mesesDiff = mesesDiff - mesMenor;
            }

        } else if (mesMaior != mesMenor) {
            mesesDiff = mesMaior - mesMenor;
        }

        return new Integer(mesesDiff);
    }

    /**
     * Compara duas datas, retornando a diferença em anos entre elas
     * (data maior - data menor). Considera somente o valor do ano para o
     * cálculo (exemplo: diferença entre 31/12/2005 e 01/01/2006 é um
     * ano). Caso a datamaior seja menor que a datamenor, retorna a
     * diferença em valores negativos.
     * 
     * @param firstDate
     *            Data a ser subtraída da dataMaior
     * @param secondDate
     *            Data maior
     * @return null caso algum parâmetro seja nulo ou Integer com a
     *         diferença das datas
     */
    public static Integer getYearDiff(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return null;
        }
        Calendar calMaior = Calendar.getInstance();
        calMaior.setTime(secondDate);
        int anoMaior = calMaior.get(Calendar.YEAR);

        Calendar calMenor = Calendar.getInstance();
        calMenor.setTime(firstDate);
        int anoMenor = calMenor.get(Calendar.YEAR);

        return new Integer(anoMaior - anoMenor);
    }

    /**
     * Dada uma data de nascimento, calcula a idade.
     * 
     * @param date
     *            A data de nascimento.
     * @return Integer A idade.
     */
    public static Integer getYearOld(Date date) {
        int idade = -1;
        if (date != null) {
            GregorianCalendar calendar = new GregorianCalendar();
            int anoAtual = calendar.get(GregorianCalendar.YEAR);
            GregorianCalendar calendarParametro = new GregorianCalendar();
            calendarParametro.setTime(date);
            int anoParametro = calendarParametro.get(GregorianCalendar.YEAR);
            calendarParametro.set(GregorianCalendar.YEAR, anoAtual);
            // se mesma data no ano atual for antes de hoje
            if (calendar.after(calendarParametro)) {
                // fez aniversário esse ano
                idade = anoAtual - anoParametro;
            } else {
                // ainda não fez aniversário esse ano
                idade = anoAtual - anoParametro - 1;
            }
        }
        return new Integer(idade);
    }

    /**
     * Verifica se a data passada como parâmetro é futuro.
     * 
     * @param data
     *            Objeto Date a ser verificado
     * @return boolean true se data for futuro
     */
    public static boolean isFuture(Date data) {
        return (new Date().before(data));
    }

    /**
     * Verifica se a data passada como parâmetro é passado.
     * 
     * @param data
     *            Objeto date a ser verificado
     * @return true se data for passado
     */
    public static boolean isPast(Date data) {
        return new Date().after(data);
    }

}