package br.com.loveanddonateapi.utils;

public class EmailUtils {

    private EmailUtils() {

    }

    public static String formatterEmail( String mail ) {
        StringBuilder sb = new StringBuilder();
        sb.append( mail.substring( 0, 2 ) );
        String[] split = mail.split( "@" );
        String firstMailPart = split[ 0 ];
        sb.append( "*********" );
        sb.append( firstMailPart.substring( firstMailPart.length() - 1 ) );
        sb.append( "@" );
        sb.append( split[ 1 ] );
        return sb.toString();
    }

}