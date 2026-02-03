package cargo.cms.edi.ei.util;

import cargo.cms.edi.ei.bo.HawbData;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

/**
 * Utility class for handling PostgreSQL STRUCT operations related to HawbData.
 * Provides helper methods for data conversion and validation.
 * 
 * @author Generated Utility
 * @version 1.0
 */
public class PostgreSQLStructUtil {
    
    private static final Logger logger = Logger.getLogger(PostgreSQLStructUtil.class.getName());
    
    // Date format for PostgreSQL compatibility
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Validates that all required fields in HawbData are present for STRUCT conversion.
     * 
     * @param hawbData The HawbData object to validate
     * @throws SQLException if validation fails
     */
    public static void validateHawbDataForStruct(HawbData hawbData) throws SQLException {
        if (hawbData == null) {
            throw new SQLException("HawbData object cannot be null");
        }
        
        // Validate required fields
        StringBuilder errors = new StringBuilder();
        
        if (isNullOrEmpty(hawbData.getHawbNo())) {
            errors.append("HAWB Number is required; ");
        }
        
        if (hawbData.getHawbDate() == null) {
            errors.append("HAWB Date is required; ");
        }
        
        if (isNullOrEmpty(hawbData.getOrigin())) {
            errors.append("Origin is required; ");
        }
        
        if (isNullOrEmpty(hawbData.getDestination())) {
            errors.append("Destination is required; ");
        }
        
        if (hawbData.getPieces() == null || hawbData.getPieces() <= 0) {
            errors.append("Pieces must be greater than 0; ");
        }
        
        if (hawbData.getWeight() == null || hawbData.getWeight() <= 0) {
            errors.append("Weight must be greater than 0; ");
        }
        
        if (errors.length() > 0) {
            throw new SQLException("HawbData validation failed: " + errors.toString());
        }
        
        logger.log(Level.FINE, "HawbData validation passed for HAWB: " + hawbData.getHawbNo());
    }
    
    /**
     * Converts a Java value to PostgreSQL-compatible format.
     * 
     * @param value The value to convert
     * @param expectedType The expected SQL type
     * @return The converted value
     */
    public static Object convertToPostgreSQLType(Object value, int expectedType) {
        if (value == null) {
            return null;
        }
        
        try {
            switch (expectedType) {
                case Types.VARCHAR:
                case Types.CHAR:
                    return value.toString();
                    
                case Types.INTEGER:
                    if (value instanceof Integer) {
                        return value;
                    } else if (value instanceof Number) {
                        return ((Number) value).intValue();
                    } else {
                        return Integer.parseInt(value.toString());
                    }
                    
                case Types.DECIMAL:
                case Types.NUMERIC:
                    if (value instanceof BigDecimal) {
                        return value;
                    } else if (value instanceof Number) {
                        return new BigDecimal(value.toString());
                    } else {
                        return new BigDecimal(value.toString());
                    }
                    
                case Types.DOUBLE:
                    if (value instanceof Double) {
                        return value;
                    } else if (value instanceof Number) {
                        return ((Number) value).doubleValue();
                    } else {
                        return Double.parseDouble(value.toString());
                    }
                    
                case Types.DATE:
                case Types.TIMESTAMP:
                    if (value instanceof java.util.Date) {
                        return new java.sql.Date(((java.util.Date) value).getTime());
                    } else if (value instanceof String) {
                        // Try to parse string date
                        return java.sql.Date.valueOf(value.toString());
                    } else {
                        return value;
                    }
                    
                default:
                    return value;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error converting value to PostgreSQL type: " + e.getMessage());
            return value; // Return original value if conversion fails
        }
    }
    
    /**
     * Creates a PostgreSQL STRUCT with proper error handling.
     * 
     * @param connection The database connection
     * @param typeName The STRUCT type name
     * @param attributes The STRUCT attributes
     * @return The created STRUCT
     * @throws SQLException if STRUCT creation fails
     */
    public static Struct createPostgreSQLStruct(Connection connection, String typeName, Object[] attributes) 
            throws SQLException {
        try {
            return connection.createStruct(typeName, attributes);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create PostgreSQL STRUCT: " + e.getMessage(), e);
            throw new SQLException("Failed to create PostgreSQL STRUCT '" + typeName + "': " + e.getMessage(), e);
        }
    }
    
    /**
     * Safely truncates a string to the specified maximum length.
     * 
     * @param value The string value
     * @param maxLength The maximum allowed length
     * @return The truncated string or null if input is null
     */
    public static String safeTruncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        
        if (value.length() <= maxLength) {
            return value;
        }
        
        String truncated = value.substring(0, maxLength);
        logger.log(Level.WARNING, "Truncated string from " + value.length() + " to " + maxLength + " characters");
        return truncated;
    }
    
    /**
     * Formats a date for PostgreSQL compatibility.
     * 
     * @param date The date to format
     * @return Formatted date string or null if input is null
     */
    public static String formatDateForPostgreSQL(java.util.Date date) {
        if (date == null) {
            return null;
        }
        
        return DATE_FORMAT.format(date);
    }
    
    /**
     * Checks if a string is null or empty.
     * 
     * @param value The string to check
     * @return true if null or empty, false otherwise
     */
    private static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Logs STRUCT creation details for debugging.
     * 
     * @param typeName The STRUCT type name
     * @param attributeCount The number of attributes
     */
    public static void logStructCreation(String typeName, int attributeCount) {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Creating PostgreSQL STRUCT: type=" + typeName + 
                      ", attributes=" + attributeCount);
        }
    }
    
    /**
     * Handles exceptions during STRUCT operations and provides meaningful error messages.
     * 
     * @param operation The operation being performed
     * @param cause The original exception
     * @return A SQLException with enhanced error information
     */
    public static SQLException handleStructException(String operation, Exception cause) {
        String message = "Failed to " + operation + ": " + cause.getMessage();
        logger.log(Level.SEVERE, message, cause);
        return new SQLException(message, cause);
    }
}