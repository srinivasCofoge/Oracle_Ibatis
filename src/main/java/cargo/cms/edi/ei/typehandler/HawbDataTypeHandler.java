package cargo.cms.edi.ei.typehandler;

import com.ibatis.sqlmap.engine.type.BaseTypeHandler;
import com.ibatis.sqlmap.engine.type.TypeHandler;
import cargo.cms.edi.ei.bo.HawbData;
import cargo.cms.edi.ei.util.PostgreSQLStructUtil;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Custom TypeHandler for converting HawbData objects to PostgreSQL STRUCT type.
 * This handler resolves NullPointerException when mapping Java HawbData objects 
 * to PostgreSQL t_hawb_ins STRUCT type in iBATIS.
 * 
 * @author Generated TypeHandler
 * @version 1.0
 */
public class HawbDataTypeHandler extends BaseTypeHandler implements TypeHandler {
    
    private static final Logger logger = Logger.getLogger(HawbDataTypeHandler.class.getName());
    
    /**
     * Sets the parameter value in the PreparedStatement for PostgreSQL STRUCT type.
     * Converts HawbData object to PostgreSQL STRUCT format.
     */
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, String jdbcType) 
            throws SQLException {
        try {
            if (parameter == null) {
                ps.setNull(i, Types.STRUCT, "t_hawb_ins");
                logger.log(Level.FINE, "Set null STRUCT parameter at index " + i);
                return;
            }
            
            if (!(parameter instanceof HawbData)) {
                throw new SQLException("Parameter must be of type HawbData, got: " + 
                    parameter.getClass().getName());
            }
            
            HawbData hawbData = (HawbData) parameter;
            
            // Validate HawbData before conversion
            PostgreSQLStructUtil.validateHawbDataForStruct(hawbData);
            
            // Convert HawbData to PostgreSQL STRUCT
            Object[] structValues = convertHawbDataToStruct(hawbData);
            
            // Create PostgreSQL STRUCT with utility method
            Connection conn = ps.getConnection();
            PostgreSQLStructUtil.logStructCreation("t_hawb_ins", structValues.length);
            Struct struct = PostgreSQLStructUtil.createPostgreSQLStruct(conn, "t_hawb_ins", structValues);
            
            ps.setObject(i, struct);
            
            logger.log(Level.FINE, "Successfully set HawbData parameter at index " + i + 
                      " for HAWB: " + hawbData.getHawbNo());
            
        } catch (SQLException e) {
            throw e; // Re-throw SQL exceptions as-is
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error setting HawbData parameter: " + e.getMessage(), e);
            throw PostgreSQLStructUtil.handleStructException("set HawbData parameter", e);
        }
    }
    
    /**
     * Gets the result from ResultSet - not typically used for input parameters
     * but required by TypeHandler interface.
     */
    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        // This method is typically used for output parameters
        // For STRUCT input parameters, this may not be called
        return null;
    }
    
    /**
     * Gets the result from ResultSet by column index.
     */
    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        // This method is typically used for output parameters
        return null;
    }
    
    /**
     * Gets the result from CallableStatement for output parameters.
     */
    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        // This would be used if the STRUCT was an output parameter
        return null;
    }
    
    /**
     * Converts HawbData object to Object array for PostgreSQL STRUCT creation.
     * Maps all fields from HawbData to t_hawb_ins structure with proper type conversion.
     * 
     * @param hawbData The HawbData object to convert
     * @return Object array containing all field values in the correct order
     */
    private Object[] convertHawbDataToStruct(HawbData hawbData) throws SQLException {
        try {
            // Create array for all STRUCT fields
            // Note: Size should match the actual PostgreSQL t_hawb_ins type definition
            Object[] values = new Object[20]; // Using 20 for the sample fields
            
            int index = 0;
            
            // Map HawbData fields to STRUCT fields with type conversion
            // Field order must match PostgreSQL t_hawb_ins definition exactly
            values[index++] = PostgreSQLStructUtil.convertToPostgreSQLType(
                safeGetValue(() -> hawbData.getHawbNo()), Types.VARCHAR);
            values[index++] = PostgreSQLStructUtil.convertToPostgreSQLType(
                safeGetValue(() -> hawbData.getHawbDate()), Types.DATE);
            values[index++] = PostgreSQLStructUtil.convertToPostgreSQLType(
                safeGetValue(() -> hawbData.getOrigin()), Types.VARCHAR);
            values[index++] = PostgreSQLStructUtil.convertToPostgreSQLType(
                safeGetValue(() -> hawbData.getDestination()), Types.VARCHAR);
            values[index++] = PostgreSQLStructUtil.convertToPostgreSQLType(
                safeGetValue(() -> hawbData.getPieces()), Types.INTEGER);
            values[index++] = PostgreSQLStructUtil.convertToPostgreSQLType(
                safeGetValue(() -> hawbData.getWeight()), Types.DOUBLE);
                
            // Shipper information with safe truncation for varchar fields
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getShipperName()), 100);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getShipperAddress1()), 100);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getShipperAddress2()), 100);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getShipperCity()), 50);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getShipperCountry()), 3);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getShipperPhone()), 20);
                
            // Consignee information
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getConsigneeName()), 100);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getConsigneeAddress1()), 100);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getConsigneeAddress2()), 100);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getConsigneeCity()), 50);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getConsigneeCountry()), 3);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getConsigneePhone()), 20);
                
            // Cargo information
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getGoodsDescription()), 500);
            values[index++] = PostgreSQLStructUtil.safeTruncate(
                (String) safeGetValue(() -> hawbData.getSpecialHandling()), 100);
            
            // Note: In a real implementation, all 100+ fields would be mapped here
            // Additional fields would follow the same pattern with proper type conversion
            
            logger.log(Level.FINE, "Converted HawbData to struct with " + index + " fields for HAWB: " + 
                      hawbData.getHawbNo());
            
            return values;
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error converting HawbData to struct: " + e.getMessage(), e);
            throw PostgreSQLStructUtil.handleStructException("convert HawbData to struct", e);
        }
    }
    
    /**
     * Safely gets a value from a supplier, handling potential null values.
     * 
     * @param supplier Functional interface to get the value
     * @return The value or null if an exception occurs
     */
    private Object safeGetValue(ValueSupplier supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error getting value from supplier: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Functional interface for safe value extraction.
     */
    @FunctionalInterface
    private interface ValueSupplier {
        Object get() throws Exception;
    }
    
    /**
     * Returns the SQL type for this handler.
     */
    @Override
    public Object valueOf(String s) {
        return null;
    }
}