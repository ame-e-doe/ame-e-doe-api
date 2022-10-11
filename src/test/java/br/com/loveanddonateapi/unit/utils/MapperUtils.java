package br.com.loveanddonateapi.unit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Generated;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class MapperUtils {

    @Generated
    private static final Logger log = LogManager.getLogger( MapperUtils.class );

    protected String objectToJson( Object object ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString( object );
        } catch ( JsonProcessingException e ) {
            log.error( e.getMessage(), e );
            return null;
        }

    }

    protected < M > M jsonToObject( String pathJson, Class< M > tClass){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue( new File( pathJson ), tClass );
        } catch( IOException e ) {
            log.error( e.getMessage(), e );
            return null;
        }

    }

}